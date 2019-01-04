package com.oristartech.marketing.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.api.exception.DaoException;
import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.core.exception.ServiceRuntimeException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.marketing.dao.IMarketingDao;
import com.oristartech.marketing.dao.IMarketingLogDao;
import com.oristartech.marketing.enums.ActivityState;
import com.oristartech.marketing.enums.ApprovalResult;
import com.oristartech.marketing.model.MarketingActivity;
import com.oristartech.marketing.model.MarketingActivityLog;
import com.oristartech.marketing.service.IMarketingActivityBusinessManagenMentService;
import com.oristartech.marketing.service.IMarketingService;
import com.oristartech.marketing.vo.MarketingActivityBusinessManagenMentVO;
import com.oristartech.marketing.vo.MarketingActivityVO;
import com.oristartech.marketing.vo.MarketingSearchVO;
import com.oristartech.rule.basic.model.SaleInfo;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.core.core.service.IRuleFinderService;
import com.oristartech.rule.core.core.service.IRuleManagerService;
import com.oristartech.rule.core.data.sync.model.RuleTaskDefineType;
import com.oristartech.rule.core.data.sync.service.ITaskDataService;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.enums.RuleStatus;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.setting.service.IPropertyService;

@Component
@Transactional
public class MarketingServiceImpl extends RuleBaseServiceImpl implements IMarketingService {

	private static final Logger log = Logger.getLogger(MarketingServiceImpl.class);
	
	@Autowired
	private IMarketingLogDao marketingLogDao;
	@Autowired
	private IMarketingDao marketingDao;
	@Autowired
	private IRuleManagerService ruleManagerService;
	@Autowired
	private ITaskDataService ruleTaskDataService;
	@Autowired
	private IMarketingActivityBusinessManagenMentService marketingActivityBusinessManagenMentService;
	@Autowired
	IRuleFinderService ruleFinderService;
	@Autowired
	IPropertyService propertyService;
	/**
	 * 1根据accountId通过接口找到账户可查询商户
	 * 2查询
	 * 3根据查询出来的创建人id，审核人id通过接口转换成名字
	 */
	public Page pageListMarketingActivityByAccountAuth(MarketingSearchVO searchVo, int pageNo, int pageSize,
			Long accountId) throws ServiceException {
		log.info("pageListMarketingActivityByAccountAuth start");
		
		try {
			//TODO 根据accountId通过接口找到账户可查询商户
//			searchVo.setBusinessCode(businessCode); 多个用,隔开
			Page page = marketingDao.pageListMarketingActivityByAccountAuth(searchVo, pageNo, pageSize);
			//TODO 创建人id，审核人id通过接口转换成名字
			return page;
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public MarketingActivityVO getMarketingById(Long tenantId,Long id,boolean isLoadGroupJson) {
		MarketingActivity ma = marketingDao.getActivity(tenantId, id);
		MarketingActivityVO mavo = getMapper().map(ma,MarketingActivityVO.class);
//		mavo.setCreaterId(ma.getCreater());
//		TODO 接口获取真名
//		mavo.setCreaterName(ma.getCreater().getTrueName());
		if(isLoadGroupJson)
			mavo.setRuleGroup(ruleManagerService.getRGJsonWithDefineData(mavo.getRuleGroupId(), false));
		return mavo;
	}

	public Long saveActivity(MarketingActivityVO marketingActivityVO, String ruleGroup, String deleteIds
			,Long accountId) throws ServiceException{
		//TODO 根据accountId通过接口找到账户可查询商户
//		String businessCode = accountAreaService.getAccountAreaBusinessCode(user.getId());

		//		String businessCode = null;
//		if(businessCode == null){
//			throw new ServiceException("用户所绑定的组织非商户，不能创建营销活动");
//		}
		marketingActivityVO.setCreaterId(accountId);//创建人
		marketingActivityVO.setCreateTime(DateUtil.getSystemDate());//创建时间
		
		MarketingActivity activity = null;
		MarketingActivity mActivity = new MarketingActivity();
		Long marketingActivityId = marketingActivityVO.getId();
		//冗余交易商户获取数据
		RuleGroupVO groupVOs = JsonUtil.jsonToObject(ruleGroup, RuleGroupVO.class);
		Map<String, String> busCodes = getContainBusinessCode(groupVOs);
		String[] inBusinessCode = null;
		String[] notInBusinessCode = null;
		if(busCodes.get("contains")!=null){
			inBusinessCode = busCodes.get("contains").split(",");
		}
		if(busCodes.get("notContains")!=null){
			notInBusinessCode = busCodes.get("notContains").split(",");
		}
		if(marketingActivityVO.getId() == null) {
//			新建营销活动
			activity = getMapper().map(marketingActivityVO, MarketingActivity.class);
			activity.setCreater(marketingActivityVO.getCreaterId());//创建人
			activity.setActivityCode(propertyService.generateActivityCode());//营销活动编号
			activity.setActivityState(ActivityState.CREATE);//活动状态，默认新建
			activity.setApprovalResult(ApprovalResult.NOCOMMIT);//默认审批状态为未提交
			Integer ruleGroupId = ruleManagerService.saveRuleGroup(ruleGroup, deleteIds ,activity.getActivityCode());
			activity.setActivityType(activity.getRuleTemplateId().toString());//活动模块编号
			activity.setRuleGroupId(ruleGroupId);
			marketingActivityId = (Long) marketingDao.save(activity);
			
			//保存营销活动交易商户数据
			this.saveBusinessCode(inBusinessCode, notInBusinessCode, marketingActivityId);
		} else {
			activity = marketingDao.getActivity(marketingActivityVO.getTenantId(), marketingActivityVO.getId());
			mActivity = getMapper().map(marketingActivityVO, MarketingActivity.class);
			//新建和审批不通过的单不进行作废旧单再创建
			if(activity.getActivityState() != ActivityState.CREATE && activity.getApprovalResult() != ApprovalResult.AUDITFAIL){
				if(marketingActivityVO.getFlag().equals("2")){
					mActivity.setApprovalResult(ApprovalResult.NOAUDIT);//审批状态为未审批
					mActivity.setActivityState(ActivityState.LOCK);//锁定状态
				}else{
					mActivity.setApprovalResult(ApprovalResult.NOCOMMIT);//审批状态为未提交
					mActivity.setActivityState(ActivityState.CREATE);//新建状态
				}
				//暂停和执行状态时需要把原来的活动作废再生成一条记录，并需要重新审批
				
				mActivity.setActivityType(activity.getRuleTemplateId().toString());
				mActivity.setActivityCode(propertyService.generateActivityCode());//营销活动编号
//				mActivity.setActivityDesc(marketingActivityVo.getActivityDesc());
//				mActivity.setActivityName(marketingActivityVo.getActivityName());
//				mActivity.setActivityType(marketingActivityVo.getActivityType());
				mActivity.setCreater(activity.getCreater());
				RuleGroupVO groupVO = JsonUtil.jsonToObject(ruleGroup, RuleGroupVO.class);
				groupVO.removePropForNew();
				mActivity.setRuleGroupId(ruleManagerService.saveRuleGroup(JsonUtil.objToJson(groupVO), deleteIds ,mActivity.getActivityCode()));
				mActivity.setRuleTemplateId(marketingActivityVO.getRuleTemplateId());
//				mActivity.setValidDateStart(marketingActivityVo.getValidDateStart());
//				mActivity.setValidDateEnd(marketingActivityVo.getValidDateEnd());
				mActivity.setApprovalCompleteTime(null);
				mActivity.setApprovalman(null);
				mActivity.setApprovalTaskCode(null);
				mActivity.setActivityState(ActivityState.CREATE);//新建状态
				mActivity.setCreateTime(DateUtil.getSystemDate());//新建时间
				mActivity.setApprovalResult(ApprovalResult.NOCOMMIT);//审批状态为未提交
				mActivity.setRevisonId(activity.getId());//保存要作废的活动ID
				marketingActivityId = marketingDao.save(mActivity);//根据作废的记录参数新建一条记录
				
				//循环保存交易商户数据到关联表
				//先删除原保存的交易商户数据，再新增
				this.marketingActivityBusinessManagenMentService.deleteByActivityId(marketingActivityVO.getId());
				//保存营销活动交易商户数据
				this.saveBusinessCode(inBusinessCode, notInBusinessCode, marketingActivityId);

				
				/************************作废活动单***********************************/
				/**
				//规则组
				activity.setActivityState(ActivityState.CANCEL);
				ruleManagerService.removeRuleGroupLogical(activity.getRuleGroupId());
				marketingActivityDao.update(activity);//先把旧的记录变更为作废
				String paramJson = ruleManagerService.getRuleGroupJsonById(activity.getRuleGroupId(), false);
				ruleTaskDataService.saveTaskData(paramJson, "DISABLE");
				*/
				
				
			}else{
				activity = marketingDao.getActivity(marketingActivityVO.getTenantId(), marketingActivityVO.getId());
				if(activity.getActivityState()==ActivityState.CREATE){//新建状态时修改内容
					activity.setActivityName(marketingActivityVO.getActivityName());
					activity.setActivityDesc(marketingActivityVO.getActivityDesc());
					activity.setValidDateStart(DateUtil.convertStrToDate(marketingActivityVO.getValidDateStart(),"yyyy-MM-dd HH:mm:ss"));
					activity.setValidDateEnd(DateUtil.convertStrToDate(marketingActivityVO.getValidDateEnd(),"yyyy-MM-dd HH:mm:ss"));
					activity.setExecuteMode(marketingActivityVO.getExecuteMode());
					Integer ruleGroupId = ruleManagerService.saveRuleGroup(ruleGroup, deleteIds ,activity.getActivityCode());
					activity.setActivityType(activity.getRuleTemplateId().toString());//活动模块编号
					activity.setRuleGroupId(ruleGroupId);
					//循环保存交易商户数据到关联表
					//先删除原保存的交易商户数据，再新增
					this.marketingActivityBusinessManagenMentService.deleteByActivityId(activity.getId());
					//保存营销活动交易商户数据
					this.saveBusinessCode(inBusinessCode, notInBusinessCode, activity.getId());
				}
				marketingDao.update(activity);
			}
		}
		
		return marketingActivityId;
	}
	
	public String getActivityCode(Long id) throws ServiceException{
		try {
			return marketingDao.getActivityCode(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	/**
	 * 删除营销活动
	 * @param id
	 * @throws ServiceException 
	 */
	public void delete(Long id,Long tenantId) throws ServiceException{
		MarketingActivity marketingActivity = marketingDao.getActivity(tenantId, id);
		if(marketingActivity.getApprovalResult()==ApprovalResult.NOCOMMIT){
			marketingDao.remove(marketingActivity);
			ruleManagerService.removeRuleGroup(marketingActivity.getRuleGroupId());
		}else{
			marketingActivity.setActivityState(ActivityState.CANCEL);//执行中的删除未作废状态
			marketingDao.update(marketingActivity);
			ruleManagerService.updateRuleGroupStatus(marketingActivity.getRuleGroupId(), RuleStatus.DELETE);
			//状态变更通知规则引擎同步任务数据--停用
			ruleTaskDataService.saveTaskData(marketingActivity.getRuleGroupId(), RuleTaskDefineType.DISABLE);
		}
	}
	
	public void enableOrDisable(Long id, Long tenantId, Long accountId, String remark) throws ServiceException {
		MarketingActivity ma = marketingDao.getActivity(tenantId,id);
		
		MarketingActivityLog marketingActivityLog = new MarketingActivityLog();
		marketingActivityLog.setMarketingActivity(ma.getId());
		marketingActivityLog.setOperateTime(new Date());
		marketingActivityLog.setOperatorId(accountId);
//		TODO 通过账号获取名字
//		marketingActivityLog.setOperatorName(opName);
		marketingActivityLog.setRemark(remark);
		
		if(ma.getActivityState() == ActivityState.EXECUTE){//执行中 --> 暂停
			ma.setActivityState(ActivityState.STOP);
			marketingActivityLog.setOpetateType(1); // 操作类型 0:启用 1:停用
			ruleManagerService.updateRuleGroupStatus(ma.getRuleGroupId(), RuleStatus.STOP);
			//状态变更通知规则引擎同步任务数据--停用
			ruleTaskDataService.saveTaskData(ma.getRuleGroupId(), RuleTaskDefineType.DISABLE);
		}else if(ma.getActivityState() == ActivityState.STOP){//暂停 --> 执行中
			marketingActivityLog.setOpetateType(0); // 操作类型 0:启用 1:停用
			ruleManagerService.updateRuleGroupStatus(ma.getRuleGroupId(), RuleStatus.RUNNING);
			ma.setActivityState(ActivityState.EXECUTE);
			//状态变更通知规则引擎同步任务数据--启用
			ruleTaskDataService.saveTaskData(ma.getRuleGroupId(), RuleTaskDefineType.ENABLE);
		}

		marketingDao.update(ma);
		marketingLogDao.save(marketingActivityLog);
	}
	
	public String testRule(String ruleGroup, List<Object> facts) {
		if(BlankUtil.isBlank(ruleGroup) || BlankUtil.isBlank(ruleGroup)) {
			return null;
		}
		return ruleManagerService.testRuleGroup(ruleGroup, facts);
	}
	
	/**
	 * 验证活动名称是否存在
	 */
	public boolean validateName(String name, Long id,String flag,Long tenantId){
		List<MarketingActivity> list = marketingDao.validateName(name, id,tenantId);
		if(list.size()>0){
			//判断是否新增的，新增的直接校验数据库是否存在未作废的单是否存在
			if(id==null){
				return true;
			}else{
				//修订的时候需要校验其他新建情况,"1"放在前面防止flag为null报错
				if("1".equals(flag)){
					for(MarketingActivity newActivity : list){
						if(newActivity.getActivityState()!=ActivityState.CANCEL){
							return true;
						}
					}
				}else{//修改直接不校验
					return false;
				}
			}
		}
		
		return false;
	}
	
	public Page<RuleGroupVO> findEngineExeRuleVOs(RuleSearchCondition searchCondition) {
		Page page = marketingDao.findEngineExeRuleVOs(searchCondition);
		List<RuleGroupVO> vos = new ArrayList<RuleGroupVO>();
		for(Object ruleGroup : page.getResult()){
			RuleGroupVO vo = getMapper().map(ruleGroup, RuleGroupVO.class);
			vos.add(vo);
		}
		try{
			if(vos != null && !BlankUtil.isBlank(vos)) {
				List<RuleGroupVO> groupVos = ruleFinderService.assembleRuleGroup(vos);
				Page<RuleGroupVO> pageVo = new Page<RuleGroupVO>(page.getStartIndex(), page.getTotalCount(), page.getPageSize(), groupVos);
				return pageVo;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceRuntimeException(e.getMessage());
		}
		return null;
	}
	
	private Map<String, String> getContainBusinessCode(RuleGroupVO groupVO) {
		List<RuleConditionElementVO> conEles = groupVO.findAllConditionElements(SaleInfo.class.getSimpleName(), "businessCode");
		Map<String, String> result = new HashMap<String, String>();
		if(!BlankUtil.isBlank(conEles)) {
			List<String> contains = new ArrayList<String>();
			List<String> notContains = new ArrayList<String>();
			for(RuleConditionElementVO conEle : conEles) {
				if("normalIn".equals(conEle.getOpUniqueName())) {
					contains.add(conEle.getOperand());
				} else {
					notContains.add(conEle.getOperand());
				}
			}
			result.put("contains", StringUtils.join(contains, ","));
			result.put("notContains", StringUtils.join(notContains, ","));
		}
		return result;
	}
	
	@Override
	public void audit(Long id,Integer auditStatus,Date auditTime,String auditTaskCode,Long approvalMan) throws ServiceException {
		MarketingActivity marketingActivity = marketingDao.get(id);
		if(marketingActivity != null){
			switch (auditStatus) {
				case 1 : // 审批通过
					marketingActivity.setApprovalResult(ApprovalResult.AUDITSUCC);
					marketingActivity.setActivityState(ActivityState.EXECUTE); // 执行中
					ruleManagerService.updateRuleGroupStatus(marketingActivity.getRuleGroupId(), RuleStatus.RUNNING);//设置规则为执行中
					
					//假如存在作废前的活动单，需要作废原活动单，并规则停用
					if(marketingActivity.getRevisonId()!=null){
						//获取需要作废的单据
						MarketingActivity activity = marketingDao.get(marketingActivity.getRevisonId());
						if(activity.getActivityState()==ActivityState.EXECUTE || activity.getActivityState()==ActivityState.STOP){//针对在执行中的活动进行作废
							activity.setActivityState(ActivityState.CANCEL);
							ruleManagerService.removeRuleGroupLogical(activity.getRuleGroupId());
							marketingDao.update(activity);//先把旧的记录变更为作废
							//停用规则
							ruleTaskDataService.saveTaskData(activity.getRuleGroupId(), RuleTaskDefineType.DISABLE);
						}
					}
					//先停用旧的在启用新的.
					//状态变更通知规则引擎同步任务数据--启用
					ruleTaskDataService.saveTaskData(marketingActivity.getRuleGroupId(), RuleTaskDefineType.ENABLE);
					
					break;
				case 2 : // 驳回审批
					marketingActivity.setApprovalResult(ApprovalResult.AUDITFAIL);//审批不通过
					marketingActivity.setActivityState(ActivityState.CREATE);//新建
					break;
			}
//			TODO 通过账号获取名字
			marketingActivity.setApprovalman(approvalMan);
			marketingActivity.setApprovalCompleteTime(auditTime);
			marketingActivity.setApprovalTaskCode(auditTaskCode);
			marketingDao.update(marketingActivity);
		}
	}
	
	private void saveBusinessCode(String[] inBusinessCode,String[] notInBusinessCode,Long activityId){
		//循环保存交易商户数据到关联表
		//包含商户
		if(!BlankUtil.isBlank(inBusinessCode)){
			for(String inCode : inBusinessCode){
				if(!BlankUtil.isBlank(inCode)){
					MarketingActivityBusinessManagenMentVO activityBusMent = new MarketingActivityBusinessManagenMentVO();
					activityBusMent.setActivityId(activityId);
					activityBusMent.setBusinessCode(inCode);
					activityBusMent.setFlag("0");
					this.marketingActivityBusinessManagenMentService.save(activityBusMent);
				}
			}
		}
		//不包含商户
		if(!BlankUtil.isBlank(notInBusinessCode)){
			for(String notInCode : notInBusinessCode){
				if(!BlankUtil.isBlank(notInCode)){
					MarketingActivityBusinessManagenMentVO activityBusMent = new MarketingActivityBusinessManagenMentVO();
					activityBusMent.setActivityId(activityId);
					activityBusMent.setBusinessCode(notInCode);
					activityBusMent.setFlag("1");
					this.marketingActivityBusinessManagenMentService.save(activityBusMent);
				}
			}
		}
	}

	public Page pageListMarketingActivity(MarketingSearchVO searchVo, int pageNo, int pageSize) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public MarketingActivityVO getMarketingByIdForCopy(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(MarketingActivityVO marketingActivityVO) {
		MarketingActivity marketingActivity = getMapper().map(marketingActivityVO,MarketingActivity.class);
//		marketingActivity.setActivityState(ActivityState.getEnumById(marketingActivityVO.getActivityState()));
//		marketingActivity.setApprovalResult(approvalResult);
//		测试分布式事物，抛异常
//		throw new RuntimeException();
		marketingDao.saveOrUpdate(marketingActivity);
	}

	public MarketingActivityVO getMarketingByNo(String serNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MarketingActivityVO> queryByStatus(ActivityState activityState) {
		// TODO Auto-generated method stub
		return null;
	}

	public void expiredMarketings() {
		// TODO Auto-generated method stub
		
	}

}
