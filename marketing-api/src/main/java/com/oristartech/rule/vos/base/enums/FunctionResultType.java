package com.oristartech.rule.vos.base.enums;

public enum FunctionResultType {
	SINGLE_RESULT_VALUE, //0 ,结果形式是单一值
	SINGLE_RESULT_OBJ,  //1 , 结果是单一对象
	SINGLE_RESULT_ACTION_OBJ , //2, 结果是单一对象， 但会把action id信息返回
	MULTI_RESULT_OBJ, //3,  结果包含多个对象。
	MULTI_RESULT_ACTION_OBJ ;//4,  结果包含多个对象。每个对象包含action id信息
	
	public static FunctionResultType getByName(String name) {
		FunctionResultType[] types = values();
		for(FunctionResultType type : types) {
			if(type.name().equals(name)) {
				return type;
			}
		}
		return null;
	}
}
