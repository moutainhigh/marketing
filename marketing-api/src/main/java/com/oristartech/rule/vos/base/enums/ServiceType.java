package com.oristartech.rule.vos.base.enums;

/**
 * SystemWebService中type的service type类型
 * @author chenjunfei
 *
 */
public enum ServiceType {
	//http action 方式请求, 通过method参数指定入口 
	ACTION,
	
	//http restful方式, 入口直接拼在url
	RESTFUL,
	
	//soap 方式请求
	SOAP,
	
	//因为有些可能是内嵌在别的系统中, 可以直接是spring bean类型
	BEAN,
	
	//票务的soap方式，有登录动作
	CMS_SOAP,
	
	//crm 的soap
	CRM_SOAP,
	
	//cms restful http 
	CMS_RESTFUL
}
