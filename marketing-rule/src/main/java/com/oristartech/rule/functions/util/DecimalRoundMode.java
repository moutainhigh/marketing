package com.oristartech.rule.functions.util;

import java.math.BigDecimal;

import com.oristartech.rule.common.util.BlankUtil;

public enum DecimalRoundMode {
	ROUND_HALF_UP, //四舍五入
	ROUND_FLOOR,   //忽略小数取整
	ROUND_CEILING,  //小数进1取整
	ROUND_UNNECESSARY ; //保留小数
	
	public static DecimalRoundMode getByName(String mode, DecimalRoundMode defaultMode) {
		if(BlankUtil.isBlank(mode)) {
			return defaultMode;
		}
		for(DecimalRoundMode m : DecimalRoundMode.values()) {
			if(m.name().equals(mode)) {
				return m;
			}
		}
		return defaultMode;
	}
	
	public int getBigDecimalRoundMode() {
		switch( this ) {
		case ROUND_HALF_UP :  return BigDecimal.ROUND_HALF_UP;
		case ROUND_FLOOR :  return BigDecimal.ROUND_FLOOR;
		case ROUND_CEILING :  return BigDecimal.ROUND_CEILING;
		case ROUND_UNNECESSARY :  return BigDecimal.ROUND_UNNECESSARY;
		}
		return BigDecimal.ROUND_UNNECESSARY; 
	}
	
	public static int convertBigDecimalRoundMode(String mode, DecimalRoundMode defaultMode) {
		DecimalRoundMode roundMode = DecimalRoundMode.getByName(mode, defaultMode);
		
		if(roundMode == null) {
			roundMode = ROUND_UNNECESSARY;
		}
		return roundMode.getBigDecimalRoundMode();
	}
}
