package com.oristartech.marketing.enums;

public enum ActivityState {
	NORMAL, 	// 0 
	CREATE,		// 1新建
	LOCK, 		// 2锁定
	EXECUTE,	// 3执行中
	STOP,		// 4暂停
	CANCEL,		// 5作废
	COMPLETE;	// 6完成(会员营销活动用)
	
	public static ActivityState getEnumById(Integer id){
		for(int i = 0; i < ActivityState.values().length;i++){
			if(id == ActivityState.values()[i].ordinal())
				return ActivityState.values()[i];
		}
		return null;
	}
	
}
