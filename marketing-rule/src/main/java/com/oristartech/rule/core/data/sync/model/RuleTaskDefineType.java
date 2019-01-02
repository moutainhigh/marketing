package com.oristartech.rule.core.data.sync.model;

public enum RuleTaskDefineType {
	ENABLE, //启用
	DISABLE; //停用
	
	public static RuleTaskDefineType get(int i){
		if(i == 0)
			return RuleTaskDefineType.ENABLE;
		else
			return RuleTaskDefineType.DISABLE;
	}
}
