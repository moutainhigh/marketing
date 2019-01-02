package com.oristartech.marketing.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oristartech.api.exception.BizExceptionEnum;
import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.components.constant.RestResponse;
import com.oristartech.marketing.enums.ApprovalResult;
import com.oristartech.marketing.service.IMarketingService;
import com.oristartech.marketing.service.IRuleElementManagerService;
import com.oristartech.marketing.vo.MarketingActivityVO;
import com.oristartech.marketing.vo.MarketingSearchVO;
import com.oristartech.marketing.vo.MarketingViewVO;
import com.oristartech.rule.basic.model.CommonInfo;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;
import com.oristartech.rule.vos.template.vo.ElementConditionVO;
import com.oristartech.rule.vos.template.vo.FieldGroupVO;
import com.oristartech.rule.vos.template.vo.GroupElementTypeVO;
import com.oristartech.rule.vos.template.vo.RuleElementTemplateVO;

@RestController
@RequestMapping("/marketing")
public class MarketingController {
	
	private Logger logger = LoggerFactory.getLogger(MarketingController.class);

	@Autowired
	IMarketingService marketingService;
	@Autowired
	IRuleElementManagerService ruleElementManagerService;
	
	
	@RequestMapping(value = "/listMarketingActivity",method = RequestMethod.POST)
    public RestResponse<String> ListMarketingActivity(@RequestBody MarketingSearchVO searchVo,@RequestParam(value = "accountId",required=false) Long accountId) {
		try {
			if(BlankUtil.isBlank(searchVo.getTenantId()))
				return RestResponse.createResult(BizExceptionEnum.MKT_MISSING_TENANT_ID);
			Page results = marketingService.pageListMarketingActivity(searchVo, searchVo.getPageNo(), searchVo.getPageSize(), accountId);
			return RestResponse.createSuccessResult(JsonUtil.objToJson(results));
		} catch (ServiceException e) {
			return RestResponse.createResult(e);
		}
		
    }
	
	/**
	 * 查看活动
	 * @param activityId
	 */
	@RequestMapping(value = "/viewActivity",method = RequestMethod.POST)
	public RestResponse<MarketingViewVO> viewActivity(@RequestParam(value="activityId") Long activityId,@RequestParam(value="tenantId") Long tenantId) {
		MarketingActivityVO activity = marketingService.getMarketingById(tenantId,activityId,true);
		RuleElementTemplateVO templateVO = ruleElementManagerService.getTemplateById(activity.getRuleTemplateId());
		MarketingViewVO vo = new MarketingViewVO();
		vo.setMarketingActivityVO(activity);
		vo.setRuleElementTemplateVO(templateVO);
		return RestResponse.createSuccessResult(vo);
	}
	
	@RequestMapping(value = "/preTestRule",method = RequestMethod.POST)
	public RestResponse<MarketingViewVO> preTestRule(@RequestParam(value="activityId") Long activityId,@RequestParam(value="tenantId") Long tenantId) {
		MarketingActivityVO activity = marketingService.getMarketingById(tenantId,activityId,true);
		RuleElementTemplateVO templateVO = ruleElementManagerService.getTemplateById(activity.getRuleTemplateId());
		MarketingViewVO vo = new MarketingViewVO();
		vo.setMarketingActivityVO(activity);
		vo.setRuleElementTemplateVO(templateVO);
		
		List<FieldGroupVO>  allFieldGroup = ruleElementManagerService.getAllFieldGroupInTest(); 
		List<ModelCategoryVO> allCategory = ruleElementManagerService.getAllCategory();
		if(!BlankUtil.isBlank(allCategory)) {
			vo.setAllCategory(StringEscapeUtils.escapeJavaScript(JsonUtil.objToJson(allCategory)));
		}
		if(!BlankUtil.isBlank(allFieldGroup)) {
			vo.setAllFieldGroup(StringEscapeUtils.escapeJavaScript(JsonUtil.objToJson(allFieldGroup)));
		}
		return RestResponse.createSuccessResult(vo);
	}
	
	/**
	 * 保存或更新
	 * @param ruleGroup
	 * @param editRules
	 * @param deleteIds
	 */
	@RequestMapping(value = "/saveActivity",method = RequestMethod.POST)
	public RestResponse<String> saveActivity(@RequestBody MarketingActivityVO marketingActivityVO, @RequestParam(value="deleteIds",required=false) String deleteIds ) {
		try {
			if(BlankUtil.isBlank(marketingActivityVO.getTenantId()))
				return RestResponse.createResult(BizExceptionEnum.MKT_MISSING_TENANT_ID);
			
			Long marketingActivityId = marketingService.saveActivity(marketingActivityVO, marketingActivityVO.getRuleGroup(), deleteIds, marketingActivityVO.getCreaterId());
			//判断是否是提交审批并保存，目前业务暂时未有保持并同时提交的操作
			if(marketingActivityVO.getFlag().equals("2")){
				marketingActivityVO.setId(marketingActivityId);
				marketingActivityVO.setApprovalResult(ApprovalResult.NOAUDIT);//提交审批时默认审批状态为未审批
				marketingActivityVO.setActivityCode(marketingService.getActivityCode(marketingActivityId));//业务单据号
				//TODO提交工作流
//				WFResultVO result = createWF(marketingActivityVO);
//				if (result.getStatus().equals("OK")) {
//					return RestResponse.createSuccessResult();
//				} else {
//					renderText(result.getMessage());
//				}
			}else{
				return RestResponse.createSuccessResult();
			}
		} catch (ServiceException e) {
			return RestResponse.createResult(e);
		}
		return RestResponse.createSuccessResult();
	}
	
	/**
	 * 提交审批
	 * @param id
	 */
	@RequestMapping(value = "/submitAudit",method = RequestMethod.POST)
	public RestResponse<String> submitAudit(@RequestParam(value="id") Long id,@RequestParam(value="tenantId") Long tenantId){
		MarketingActivityVO activity = marketingService.getMarketingById(tenantId,id,true);
		//TODO提交工作流
//		WFResultVO result = createWF(activity);
//		
//		if (result.getStatus().equals("OK")) {
//			renderText("ok");
//		} else {
//			renderText(result.getMessage());
//		}
		return RestResponse.createSuccessResult();
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public RestResponse<String> delete(@RequestParam(value="id") Long id,@RequestParam(value="tenantId") Long tenantId){
		try{
			marketingService.delete(id, tenantId);
		}catch (ServiceException e) {
			return RestResponse.createResult(e);
		}
		
		return RestResponse.createSuccessResult();
	}
	
	/**
	 * 启用/停用  营销活动
	 */
	@RequestMapping(value = "/enableOrDisable",method = RequestMethod.POST)
	public RestResponse<String> enableOrDisable(@RequestParam(value="id") Long id,@RequestParam(value="tenantId") Long tenantId,
			@RequestParam(value="accountId") Long accountId, @RequestParam(value="remark",required=false) String remark){
		try{
			marketingService.enableOrDisable(id, tenantId, accountId, remark);
		}catch (ServiceException e) {
			return RestResponse.createResult(e);
		}
		
		return RestResponse.createSuccessResult();
	}
	
	private final static String[] ACTIVITY_TYPES = new String[]{"NormalActivity", "MemberService"};
	
	/**
	 * 展示活动模板
	 */
	@RequestMapping(value = "/listTemplates",method = RequestMethod.POST)
	public RestResponse<Map<String, List<RuleElementTemplateVO>>> listTemplates() {
		Map<String, List<RuleElementTemplateVO>> typeTemplates = new LinkedHashMap<String, List<RuleElementTemplateVO>>();
		try {
			for(String type : ACTIVITY_TYPES) {
				List<RuleElementTemplateVO> normalActivitys =  ruleElementManagerService.getTemplateByType(type);
				if(!BlankUtil.isBlank(normalActivitys)) {
					RuleElementTemplateVO tem = normalActivitys.get(0);
					typeTemplates.put(tem.getCnType(), normalActivitys);
				}
			}
		} catch (ServiceException e) {
			return RestResponse.createResult(e);
		}
		return RestResponse.createSuccessResult(typeTemplates);
		
	}
	
	@RequestMapping(value = "/getTemplate",method = RequestMethod.POST)
	public RestResponse<RuleElementTemplateVO> getTemplate(@RequestParam(value="templateId") Integer templateId) {
		RuleElementTemplateVO templateVO = ruleElementManagerService.getTemplateById(templateId);
		return RestResponse.createSuccessResult(templateVO);
	}
	
	//条件页面
	@RequestMapping(value = "/listConditions",method = RequestMethod.POST)
	public RestResponse<ElementConditionVO> listConditions(@RequestParam(value="templateId") Integer templateId, @RequestParam(value="isCommon") Boolean isCommon,
			@RequestParam(value="excludeGroupIds",required=false) String excludeGroupIds) {
		List<GroupElementTypeVO> fieldGroupTypes = ruleElementManagerService.getGroupEleWithFieldGroups(templateId, isCommon, excludeGroupIds);
		RuleElementTemplateVO templateVO = ruleElementManagerService.getTemplateById(templateId);
		ElementConditionVO vo = new ElementConditionVO();
		vo.setFieldGroupTypes(fieldGroupTypes);
		vo.setTemplateVO(templateVO);
		return RestResponse.createSuccessResult(vo);
	}
	
	//加载指定id的规则条件属性数据
	@RequestMapping(value = "/loadConditionFields",method = RequestMethod.POST)
	public RestResponse<String> loadConditionFields(@RequestParam(value="fieldGroupIds") String ids) {
		return RestResponse.createSuccessResult(ruleElementManagerService.findConditionFieldGroupsJson(ids));
	}
	
	//方法页面
	@RequestMapping(value = "/listFunctions",method = RequestMethod.POST)
	public RestResponse<List<GroupElementTypeVO>> listFunctions(@RequestParam(value="templateId") Integer templateId, @RequestParam(value="excludeGroupIds",required = false) String excludeGroupIds) {
		return RestResponse.createSuccessResult(ruleElementManagerService.getGroupEleWithFunctionGroups(templateId, excludeGroupIds));
	}
	
	//加载方法组数据
	@RequestMapping(value = "/loadFunctionGroups",method = RequestMethod.POST)
	public RestResponse<String> loadFunctionGroups(@RequestParam(value="funcGroupIds") String ids) {
		return RestResponse.createSuccessResult(ruleElementManagerService.findFuncGroupJson(ids));
	}
	
	@RequestMapping(value = "/testRule",method = RequestMethod.POST)
	public RestResponse<String> testRule(@RequestParam(value="ruleGroup") String ruleGroup, @RequestParam(value="facts") String facts,
			@RequestParam(value="currentDate") String currentDate, @RequestParam(value="holiday") Boolean holiday) {
		CommonInfo commonInfo = new CommonInfo();
		commonInfo.setHoliday(holiday);
		if(!BlankUtil.isBlank(currentDate )) {
			Date curDate = DateUtil.convertStrToDate(currentDate, DateUtil.DEFAULT_LONG_DATE_FORMAT);
			commonInfo.setAppointInvalidDate(curDate);
			commonInfo.setValidDate(curDate);
			commonInfo.setValidWeek(curDate);
			commonInfo.setValidTime(curDate);
		}
		List<Object> factObjs = new ArrayList<Object>();
		factObjs.add(commonInfo);
		if(!BlankUtil.isBlank(facts )) {
			factObjs.addAll(JsonUtil.jsonArrayToList(facts, Object.class));
		}
		String result = marketingService.testRule(ruleGroup, factObjs);
		if(result == null) {
			result = "nomatch";
		}
		
		return RestResponse.createSuccessResult(result);
	}
	/**
	 * 验证活动名称
	 * @param name
	 */
	@RequestMapping(value = "/valActivityName",method = RequestMethod.POST)
	public RestResponse<String> valActivityName(@RequestParam(value="id") Long id,@RequestParam(value="name") String name,
			@RequestParam(value="flag") String flag,@RequestParam(value="tenantId") Long tenantId){
		if (marketingService.validateName(name,id,flag,tenantId)) {
			return RestResponse.createSuccessResult("false");
		}else{
			return RestResponse.createSuccessResult("true");
		}
	}
}
