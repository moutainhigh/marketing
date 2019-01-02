package com.oristartech.marketing.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.BaseTestCase;
import com.oristartech.marketing.vo.MarketingSearchVO;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.common.util.Page;
import com.oristartech.setting.service.IPropertyService;

public class PropertyServiceTest extends BaseTestCase {

	@Autowired
	private IPropertyService propertyService;
	
	
	@Test
	public void generateActivityCodeTest() throws ServiceException {
		String string = propertyService.generateActivityCode();
		System.out.println(string);
	}
	
	@Test
	public void generateMemberActivityCodeTest() throws ServiceException {
		String string = propertyService.generateMemberActivityCode();
		System.out.println(string);
	}
}
