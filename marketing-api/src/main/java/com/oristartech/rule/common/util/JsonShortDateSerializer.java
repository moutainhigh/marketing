package com.oristartech.rule.common.util;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonShortDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		
		String formattedDate = DateUtil.convertDateToStr(date, DateUtil.DEFAULT_SHORT_DATE_FORMAT);

		gen.writeString(formattedDate);
	}
}
