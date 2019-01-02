package com.oristartech.config.converter;

import java.util.List;
import java.util.Map;

import org.dozer.CustomConverter;


public class CollectConverter implements CustomConverter {

	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		if(sourceFieldValue instanceof Map){
			return (Map) sourceFieldValue;
		}else if(sourceFieldValue instanceof List){
			return (List) sourceFieldValue;
		}
		return null;
	}

	
	
}
