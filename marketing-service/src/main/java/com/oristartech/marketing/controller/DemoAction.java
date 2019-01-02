/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oristartech.marketing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.core.config.service.ISystemConfigService;
import com.oristartech.rule.core.init.service.IRuleSystemInitService;
import com.oristartech.rule.core.match.service.IRuleService;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;


@RestController
@RequestMapping("/test")
public class DemoAction {

   /* @Reference
    private IDemoService demoService;*/

	@Autowired
	private ISystemConfigService systemConfigService;

	@Autowired
	IRuleSystemInitService ruleSystemInitService;
    @Autowired
    IRuleService ruleService;
    
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String setDemoServiceGet(@RequestParam("str") String str) {
        //System.out.println(demoService.sayHello("hello"));
        //this.dubboService = demoService;
        return systemConfigService.getValueByName(str);
    }
    
	@RequestMapping(value = "/init",method = RequestMethod.GET)
    public void init() {
        ruleSystemInitService.init();
    }
	
	@RequestMapping(value = "/testrule",method = RequestMethod.GET)
    public void testrule() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"id\":-1,\"name\":\"影票打折1109\",\"ruleSectionVO\":{\"id\":887,\"ruleConditions\":[{\"id\":-1,\"conditionElements\":[{\"id\":-1,\"isAnd\":true,\"seqNum\":0,\"opId\":2,\"opCode\":\"==\",\"opUniqueName\":\"normalEqual\",\"opCnName\":\"等于\",\"opNum\":1,\"isCustomOpCode\":false,\"opIdentityTag\":1,\"operand\":\"BUY\",\"modelField\":{\"id\":33,\"name\":\"tradeType\",\"cnName\":\"交易类型\",\"type\":\"java.lang.String\",\"modelCategoryId\":7,\"modelCategoryName\":\"SaleInfo\",\"isExtern\":false,\"isDelete\":false,\"isLoadAuto\":false,\"formCtrl\":\"multiSelect\",\"fieldOperators\":[],\"modelFieldDataSources\":[{\"id\":27,\"label\":\"消费\",\"value\":\"BUY\",\"seqNum\":0,\"modelFieldId\":33},{\"id\":28,\"label\":\"充值\",\"value\":\"MEMBER_ADD_AMOUNT\",\"seqNum\":1,\"modelFieldId\":33},{\"id\":29,\"label\":\"退货\",\"value\":\"REJECT\",\"seqNum\":2,\"modelFieldId\":33},{\"id\":74,\"label\":\"会员注册\",\"value\":\"MEMBER_ACTIVE_CARD\",\"seqNum\":3,\"modelFieldId\":33}]},\"modelFieldId\":33,\"modelFieldName\":\"tradeType\",\"ruleConditionId\":-1,\"operandLabel\":\"[\\\"消费\\\"]\"}],\"ruleSectionId\":887,\"seqNum\":0,\"modelCategoryId\":7,\"modelCategoryName\":\"SaleInfo\"},{\"id\":-2,\"conditionElements\":[{\"id\":-2,\"isAnd\":true,\"seqNum\":0,\"opId\":8,\"opCode\":\"in\",\"opUniqueName\":\"normalIn\",\"opCnName\":\"包含\",\"opNum\":-1,\"isCustomOpCode\":false,\"opIdentityTag\":1,\"operand\":\"32045111\",\"modelField\":{\"id\":80,\"name\":\"businessCode\",\"cnName\":\"交易商户\",\"type\":\"java.lang.String\",\"modelCategoryId\":7,\"modelCategoryName\":\"SaleInfo\",\"isExtern\":true,\"isDelete\":false,\"isLoadAuto\":false,\"externPageMethod\":\"chooseBusiness\",\"formCtrl\":\"input\",\"businessSystemId\":3,\"businessSystemName\":\"CRM_WEB\",\"searchServiceName\":\"BusinessSearch\",\"modelServiceName\":\"BusinessGets\",\"labelFieldName\":\"businessName\",\"fieldOperators\":[],\"modelFieldDataSources\":[]},\"modelFieldId\":80,\"modelFieldName\":\"businessCode\",\"ruleConditionId\":-2,\"operandLabel\":\"金坛星轶IMAX国际影城\"}],\"ruleSectionId\":887,\"seqNum\":1,\"modelCategoryId\":7,\"modelCategoryName\":\"SaleInfo\",\"fieldGroupId\":14}],\"ruleActions\":[],\"ruleGroupId\":-1,\"isSerial\":true},\"priority\":1000,\"bizOrderCode\":\"MAT201811090001\",\"createDate\":\"2018-12-18 12:34:27\",\"status\":\"NEW\",\"rules\":[{\"id\":434,\"ruleGroupId\":-1,\"ruleGroupName\":\"影票打折1109\",\"ruleGroupRemark\":\"影票打折1109\",\"name\":\"影票打折1109\",\"seqNum\":0,\"priority\":1000,\"status\":\"NEW\",\"ruleType\":\"SaleActivity\",\"executeMode\":\"AUTO_MATCH\",\"modifyDate\":\"2018-12-18 12:34:27\",\"version\":1,\"ruleSections\":[{\"id\":-1,\"seqNum\":0,\"ruleConditions\":[],\"ruleActions\":[{\"id\":-1,\"ruleSectionId\":-1,\"seqNum\":0,\"actionFnId\":19,\"actionFnUniqueName\":\"FilmTicketPriceModify\",\"clzName\":\"com.oristartech.rule.functions.sales.FilmTicketPriceModify\",\"clsMethod\":\"execute\",\"cnName\":\"修改单票售价\",\"fnCategoryId\":2,\"fnCategoryName\":\"Pricing\",\"fnEffect\":\"modifyFilmTicketPrice\",\"funcGroupId\":15,\"fnIsExeMultiTime\":true,\"fnResultType\":\"MULTI_RESULT_OBJ\",\"parameters\":[{\"fnParameterId\":68,\"fnParameterName\":\"modifyWay\",\"fnParameterCnName\":\"调价方式\",\"ruleActionId\":-1,\"value\":\"addToLowestPrice\",\"seqNum\":0},{\"fnParameterId\":38,\"fnParameterName\":\"modifyValue\",\"fnParameterCnName\":\"调整额\",\"ruleActionId\":-1,\"value\":\"0\",\"seqNum\":1},{\"fnParameterId\":100,\"fnParameterName\":\"addAmountAfterDiscount\",\"fnParameterCnName\":\"折扣后加减N元\",\"ruleActionId\":-1,\"value\":\"\",\"seqNum\":2},{\"fnParameterId\":94,\"fnParameterName\":\"integralAmount\",\"fnParameterCnName\":\"积分定价（分）\",\"ruleActionId\":-1,\"value\":\"\",\"seqNum\":3},{\"fnParameterId\":96,\"fnParameterName\":\"integralMoney\",\"fnParameterCnName\":\"加金额（元）\",\"ruleActionId\":-1,\"value\":\"\",\"seqNum\":4},{\"fnParameterId\":78,\"fnParameterName\":\"decimalRoundMode\",\"fnParameterCnName\":\"折扣后取整方式\",\"ruleActionId\":-1,\"value\":\"ROUND_UNNECESSARY\",\"seqNum\":5},{\"fnParameterId\":70,\"fnParameterName\":\"lessProcessMethod\",\"fnParameterCnName\":\"低于最低价时\",\"ruleActionId\":-1,\"value\":\"cinemaPay\",\"seqNum\":6},{\"fnParameterId\":71,\"fnParameterName\":\"cinemaPayAmount\",\"fnParameterCnName\":\"最多补贴N元\",\"ruleActionId\":-1,\"value\":\"20\",\"seqNum\":7},{\"fnParameterId\":72,\"fnParameterName\":\"modifyAmountMethod\",\"fnParameterCnName\":\"调价商品数量\",\"ruleActionId\":-1,\"value\":\"all\",\"seqNum\":8},{\"fnParameterId\":69,\"fnParameterName\":\"modifyAmount\",\"fnParameterCnName\":\"数量\",\"ruleActionId\":-1,\"value\":\"\",\"seqNum\":9}],\"allParameterMapObjWithConfig\":{\"addAmountAfterDiscount\":\"\",\"modifyWay\":\"addToLowestPrice\",\"modifyAmount\":\"\",\"modifyAmountMethod\":\"all\",\"decimalRoundMode\":\"ROUND_UNNECESSARY\",\"modifyValue\":\"0\",\"cinemaPayAmount\":\"20\",\"integralMoney\":\"\",\"lessProcessMethod\":\"cinemaPay\",\"integralAmount\":\"\"},\"allParameterMap\":{\"addAmountAfterDiscount\":\"\",\"modifyWay\":\"addToLowestPrice\",\"modifyAmount\":\"\",\"modifyAmountMethod\":\"all\",\"decimalRoundMode\":\"ROUND_UNNECESSARY\",\"modifyValue\":\"0\",\"cinemaPayAmount\":\"20\",\"integralMoney\":\"\",\"lessProcessMethod\":\"cinemaPay\",\"integralAmount\":\"\"},\"allParameterMapWithConfig\":{\"addAmountAfterDiscount\":\"\",\"modifyWay\":\"addToLowestPrice\",\"modifyAmount\":\"\",\"modifyAmountMethod\":\"all\",\"decimalRoundMode\":\"ROUND_UNNECESSARY\",\"modifyValue\":\"0\",\"cinemaPayAmount\":\"20\",\"integralMoney\":\"\",\"lessProcessMethod\":\"cinemaPay\",\"integralAmount\":\"\"},\"parameterMapObj\":{\"addAmountAfterDiscount\":\"\",\"modifyWay\":\"addToLowestPrice\",\"modifyAmount\":\"\",\"modifyAmountMethod\":\"all\",\"decimalRoundMode\":\"ROUND_UNNECESSARY\",\"modifyValue\":\"0\",\"cinemaPayAmount\":\"20\",\"integralMoney\":\"\",\"lessProcessMethod\":\"cinemaPay\",\"integralAmount\":\"\"}}],\"ruleId\":434}],\"validDateStart\":\"2018-11-01 00:00:00\",\"validDateEnd\":\"2019-01-31 00:00:00\",\"bizOrderCode\":\"MAT201811090001\",\"bizPropertyMap\":{},\"merge\":false,\"validTimeStartStr\":\"2018-11-01\",\"validTimeEndStr\":\"2019-01-31\"}],\"ruleType\":\"SaleActivity\",\"validDateStart\":\"2018-11-01\",\"validDateEnd\":\"2019-01-31\",\"executeMode\":\"AUTO_MATCH\",\"templateId\":1,\"remark\":\"影票打折1109\",\"validDateStartStr\":\"2018-11-01\",\"validDateEndStr\":\"2019-01-31\"}");
		
		RuleGroupVO groupVO = JsonUtil.jsonToObject(sb.toString(), RuleGroupVO.class);
		List<Object> facts = JsonUtil.jsonArrayToList("[{\"_type\":\"CommonInfo\",\"_parent\":\"Object\",\"validDate\":\"2018-12-18 12:30:32\",\"validWeek\":\"2018-12-18 12:30:32\",\"validTime\":\"2018-12-18 12:30:32\",\"holiday\":false,\"appointInvalidDate\":\"2018-12-18 12:30:32\",\"currentDate\":\"2018-12-18 12:30:32\",\"_typeList\":\"CommonInfo\"},{\"_type\":\"SaleInfo\",\"tradeType\":\"BUY\",\"businessCode\":\"32045111\"},{\"_type\":\"FilmTicketInfo\",\"_parent\":\"SaleItemInfo\",\"price\":\"100\",\"saleItemType\":\"0\",\"lowestPrice\":\"20\",\"planStartTime\":\"2018-12-18 12:30\"}]", Object.class);
		
		ruleService.testRuleGroup(groupVO, facts);
    }
	
	@RequestMapping(value = "/matchrule",method = RequestMethod.POST)
    public String matchrule(@RequestParam(value = "typeName", required = false) String typeName,@RequestParam(value = "facts") String facts) {
		
		return ruleService.matchRule("1",typeName, facts);
    }

	@RequestMapping(value = "/matchrule1",method = RequestMethod.GET)
    public String matchrule1(@RequestParam("facts") String facts) {
		
		return ruleService.matchRule("1",null, facts);
    }
}