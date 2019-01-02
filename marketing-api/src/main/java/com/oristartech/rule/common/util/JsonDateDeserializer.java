package com.oristartech.rule.common.util;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonDateDeserializer extends JsonDeserializer<Date> {
	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext arg1) throws IOException, JsonProcessingException {
        String dateStr = jsonParser.getText();
        try {
            return DateUtil.convertStrToDate(dateStr, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        } catch (Exception e) {
        	return DateUtil.convertStrToDate(dateStr, DateUtil.DEFAULT_DATE_FORMAT);
        }
	}
}
