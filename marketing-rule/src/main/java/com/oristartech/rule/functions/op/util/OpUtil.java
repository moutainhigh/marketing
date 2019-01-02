package com.oristartech.rule.functions.op.util;

import com.oristartech.rule.base.op.model.CustomOpParameter;
import com.oristartech.rule.drools.executor.context.RuleMatchContext;

public class OpUtil {
	private static String IGNORE_SPLITER = "-";
	/**
	 * 当前属性是否在忽略比较列表中.
	 * @param opParam
	 * @return
	 */
	public static boolean isCurFieldIgnore(CustomOpParameter opParam) {
		String curCategoryName = opParam.getModelCategoryName();
		String curFieldName = opParam.getModelFieldName();
		
		RuleMatchContext context = opParam.getContext();
//		List<Object> ruleContextInfos = context.getFactsContainer().cloneFactsByType(BizFactConstants.RULE_CONTEXT_INFO);
//		if(BlankUtil.isBlank(ruleContextInfos)) {
//			return false;
//		}
//		Object ruleContextInfo = ruleContextInfos.get(0);
//		
//		if(ruleContextInfo != null) {
//			String ignoreFields = BeanUtils.getPropertyStr(ruleContextInfo, BizFactConstants.RCI_IGNORE_CATEGORY_FIELDS);
//			if(!BlankUtil.isBlank(ignoreFields)) {
//				//ignoreFields格式是categoryName-fieldName,categoryName-fieldName...
//				String[] catAndFields = ignoreFields.split(RuleConstans.RULE_PARAMETER_SPLITER);
//				for(String catAndField : catAndFields) {
//					String[] catAndFieldNames = catAndField.split(IGNORE_SPLITER);
//					if(catAndFieldNames.length < 2) {
//						throw new RuleBaseException("忽略属性设置错误:" + catAndField);
//					}
//					String categoryName = catAndFieldNames[0];
//					String fieldName = catAndFieldNames[1];
//					if(curCategoryName.equals(categoryName) && curFieldName.equals(fieldName)) {
//						return true;
//					}
//				}
//			}
//		}
		return false;
	}
}
