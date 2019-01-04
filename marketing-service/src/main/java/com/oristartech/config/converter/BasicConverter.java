package com.oristartech.config.converter;

import java.math.BigInteger;

import org.dozer.CustomConverter;

public class BasicConverter implements CustomConverter {

	@Override
	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		if(sourceFieldValue instanceof BigInteger){
			if(destinationClass.equals(Long.class)){
	//			BigInteger转Long
				BigInteger bi = new BigInteger(sourceFieldValue.toString());
				return Long.valueOf(bi.longValue());
			}
		}
		return null;
	}

}
