package com.oristartech.rule.functions.sales;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.RuleConstans;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 卖品品牌优惠价调整
 * @author chenjunfei
 *
 */
public class MerchandiseBrandModifier extends MerchandiseCategoryModifier {
	
	
	//选择分类方式
	private static final String MER_BRAND_METHOD = "merBrandMethod";
	//指定分类
	private static final String APPOINT_BRAND = "appointBrand";
	//全部分类
	private static final String ALL_MER_BRAND = "allMerBrand";
	
	//分类
	private static final String MER_BRAND = "brandId";
	
	public RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext, ResultRuleVO ruleVO, RuleActionVO actionVO) {
		Map<String, String> params = actionVO.getParameterMap();
		RuleActionResult result =  prepareRuleActionResult(actionVO);
		RuleResult ruleResult = context.getRuleResult(ruleVO.getId());
		List<Object> merFacts = context.cloneFactsByType(BizFactConstants.MER_INFO);
		
		log.info("<run> ruleid=" + ruleVO.getId() + " actionid=" + actionVO.getId() + " params=" + JsonUtil.objToJson(params)
			+ " merFacts=" + JsonUtil.objToJson(merFacts));
		
		if(BlankUtil.isBlank(merFacts)) {
			log.info("<run> merFacts=null");
			return null;
		}
		
		
		String modifyWay = getModifyWay(params);
		BigDecimal modifyValue = getModifyValue(params);
		Set<String> merBrands = getModifyMerBrands(params);
		BigDecimal modifyAmount = getModifyAmount(params, acContext);
		
		//优先改价格高的
		super.sortSaleFact(merFacts);
		
		for(Object fact : merFacts) {
			validateMerFact(fact);
			String merBrand = BeanUtils.getPropertyStr(fact, BizFactConstants.MER_BRAND_ID);
			if(BlankUtil.isBlank(merBrand) || (merBrands != null && !merBrands.contains(merBrand))) {
				continue;
			}
			
			Map<String, Object> resultFact = modifyMerItem(acContext, modifyWay, modifyValue, modifyAmount, params, fact, ruleResult);
			if(resultFact != null) {
				result.getFnResults().add(resultFact);
				BigDecimal alreadyModifyAmount = (BigDecimal)resultFact.get(BizFactConstants.AMOUNT);
				modifyAmount = modifyAmount.subtract(alreadyModifyAmount);
				if(modifyAmount.compareTo(BigDecimal.ZERO) <= 0) {
					//已经调整完
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取调整品牌, 若返回null,表示调整所有
	 * @param params
	 * @return
	 */
	private Set<String> getModifyMerBrands(Map<String, String> params) {
		String merBrandMethod = params.get(MER_BRAND_METHOD);
		
		if(ALL_MER_BRAND.equals(merBrandMethod)) {
			return null ;
		} else if(APPOINT_BRAND.equals(merBrandMethod)) {
			//category 是逗号分隔的字符串
			String merBrands = params.get(MER_BRAND);
			if(BlankUtil.isBlank(merBrands)) {
				throw new RuleExecuteRuntimeException("需要设置卖品品牌");
			}
			String[] fields = merBrands.split(RuleConstans.RULE_PARAMETER_SPLITER);
			Set<String> result = new HashSet<String>();
			for(String field : fields) {
				result.add(field);
			}
			return result;
		}
		//都不是则设置分类方式
		throw new RuleExecuteRuntimeException("需要设置选择品牌方式");
	}
	
}
