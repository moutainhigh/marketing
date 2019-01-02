package com.oristartech.rule.core.ws.client.service;

import java.util.Map;

import com.oristartech.rule.vos.ws.vo.RuleWSResultVO;

/**
 * 系统webservice表中的service方法统一调用类
 * @author chenjunfei
 *
 */
public interface IRuleSystemWebServiceInvoker {
	/**
	 * 调用指定service
	 * @param serviceName
	 * @param params
	 * @return
	 */
	String invodeService(String serviceName, Map<String, Object> params);
	
	/**
	 * 调用指定service, 返回对象结果
	 * @param serviceName
	 * @param params
	 * @return
	 */
	RuleWSResultVO invodeServiceWithResultVO(String serviceName, Map<String, Object> params);
	
}
