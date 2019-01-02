package com.oristartech.config.converter;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 *  dozer 自定义类型转换器，能自动对Enum与String类型相互转换
 * @author Vincent
 *
 */
public class EnumDozerConverter implements CustomConverter {

	@SuppressWarnings("unchecked")
	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,	Class<?> sourceClass) {
		if (sourceFieldValue == null) {
			return null;
		}
		Object dest = null;
		if (sourceClass.isEnum()) {
			if (existingDestinationFieldValue == null) {
				dest = String.valueOf(((Enum)sourceFieldValue).ordinal());
			} else {
				dest = existingDestinationFieldValue;
			}
			return dest;
		} else if (sourceClass.equals(String.class)) {
			try {
				Object[] consts = destinationClass.getEnumConstants();
				return (Enum) consts[Integer.valueOf((String)sourceFieldValue)];
			} catch (SecurityException e) {
				throw new MappingException(e);
			}  catch (IllegalArgumentException e) {
				throw new MappingException(e);
			}
		}else if (sourceClass.equals(Integer.class)) {
			try {
				Object[] consts = destinationClass.getEnumConstants();
				return (Enum) consts[Integer.valueOf(String.valueOf(sourceFieldValue))];
			} catch (SecurityException e) {
				throw new MappingException(e);
			}  catch (IllegalArgumentException e) {
				throw new MappingException(e);
			}
		} else {
			throw new MappingException(
					"Converter EnumStringConverter used incorrectly. Arguments passed in were:"
							+ existingDestinationFieldValue + " and " + sourceFieldValue);
		}
	}
}