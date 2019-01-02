package com.oristartech.rule.functions.op.util;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.oristartech.marketing.core.ApplicationContextHelper;
import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.FactUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.RuleConstans;
import com.oristartech.rule.core.cache.IRuleDataCache;
import com.oristartech.rule.core.functions.BizFunctionUtil;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;
import com.oristartech.rule.vos.core.vo.RuleConditionVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

/**
 * 商品统计数量的比较工具类
 * @author Administrator
 *
 */
public class MerStatisItemAmountUtil {
	public static boolean compare(CustomOpParameter opParam, NumberCompareOpEnum  op) {
		Object fieldValue = opParam.getFieldValue();
		
		if(fieldValue == null ) {
			return false;
		}
		
		IRuleDataCache ruleDataCache = ApplicationContextHelper.getContext().getBean(IRuleDataCache.class);
		RuleVO ruleVO = ruleDataCache.getRuleById(opParam.getRuleId());
		if(ruleVO == null ) {
			return false;
		}
		
		RuleConditionVO conVo = ruleVO.findConditionById(opParam.getConditionId());
		if(conVo == null ) {
			return false;
		}
		String statisFieldName = BizFactConstants.MER_BRAND_ID;
		RuleConditionElementVO conEleVo = conVo.findElementVO(statisFieldName);
		
 		if(conEleVo == null) {
 			//不是统计类别就是品牌，现在
 			statisFieldName = BizFactConstants.MER_CLASS_CODE;
 			conEleVo = conVo.findElementVO(statisFieldName);
		}
 		
 		if(conEleVo == null) {
 			return false;
 		}
		String operand = conEleVo.getOperand();
		Integer amount = null;
//				getAllStatisMerAmount(opParam.getContext().getFactsContainer().getFacts(), 
//									operand, 
//									statisFieldName);
		
		opParam.setFieldValue(amount);
		
		return NumberCompareUtil.compare(opParam, op);
	}
	
	/**
	 * 获取指定属性的统计数
	 * @param facts
	 * @param relateValues
	 * @param modelFieldName
	 * @return
	 */
	private static Integer getAllStatisMerAmount(List<Object> facts, String relateValues, String modelFieldName) {
		Integer total = 0;
		List<Object> statisInfos = FactUtil.collectFactsByType(facts, BizFactConstants.MER_STATIS_INFO);
		if(!BlankUtil.isBlank(relateValues) && !BlankUtil.isBlank(statisInfos)) {
			String [] fields = relateValues.split(RuleConstans.RULE_PARAMETER_SPLITER);
			for(Object fact : statisInfos) {
				String modelFieldValue = BeanUtils.getPropertyStr(fact, modelFieldName);
				if(!BlankUtil.isBlank(modelFieldValue) && ArrayUtils.contains(fields, modelFieldValue)) {
					Integer amount = BizFunctionUtil.getInteger(fact, BizFactConstants.MER_SATTIS_SUM_AMOUNT, 0);
					total += amount;
				}
			}
		}
		return total;
	}
}
