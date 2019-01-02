package com.oristartech.rule.core.ws.client.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.components.http.HttpService;
import com.oristartech.marketing.core.ApplicationContextHelper;
import com.oristartech.marketing.core.http.HttpClientUtil;
import com.oristartech.marketing.core.http.HttpResult;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.core.base.dao.ISystemServiceUrlDao;
import com.oristartech.rule.core.base.dao.ISystemWebServiceDao;
import com.oristartech.rule.core.base.model.SystemServiceUrl;
import com.oristartech.rule.core.base.model.SystemWebService;
import com.oristartech.rule.core.config.service.ISystemConfigService;
import com.oristartech.rule.core.ws.client.service.IRuleSystemWebServiceInvoker;
import com.oristartech.rule.vos.base.enums.ServiceType;
import com.oristartech.rule.vos.ws.vo.CMSWSResultVO;
import com.oristartech.rule.vos.ws.vo.RuleWSResultVO;

@Component
public class RuleSystemWebServiceInvokerImpl implements IRuleSystemWebServiceInvoker {

	private static final String SERVICE_METHOD = "method";
	private static final String ENTRY_METHOD = "searchMethod";
	private static final String CONDITIONS = "conditions";
	
	private static final String CMS_DATA_PARAM_NAME = "data";
	private static final String CMS_WEB_SERVICE_KEY = "CMS_WEB_SERVICE_KEY";
	private static final String CMS_KEY_PARAM_NAME = "license_number";
	private static final String SYSTEM_CODE_PARAM = "systemCode";
	
	@Autowired
	private ISystemWebServiceDao ruleSystemWebServiceDao;
	@Autowired
	private ISystemConfigService ruleSystemConfigService;
	@Autowired
	private ApplicationContextHelper ruleApplicationContextHelper;
	@Autowired
	private ISystemServiceUrlDao ruleSystemServiceUrlDao;
	@Autowired
	HttpService httpService;
	
	private static final Logger log = LoggerFactory.getLogger(RuleSystemWebServiceInvokerImpl.class);
	
	/**
	 * @param ruleSystemServiceUrlDao the ruleSystemServiceUrlDao to set
	 */
	public void setRuleSystemServiceUrlDao(ISystemServiceUrlDao ruleSystemServiceUrlDao) {
		this.ruleSystemServiceUrlDao = ruleSystemServiceUrlDao;
	}

	public void setRuleSystemConfigService(ISystemConfigService ruleSystemConfigService) {
    	this.ruleSystemConfigService = ruleSystemConfigService;
    }

	public ISystemWebServiceDao getRuleSystemWebServiceDao() {
    	return ruleSystemWebServiceDao;
    }

	public void setRuleApplicationContextHelper(ApplicationContextHelper ruleApplicationContextHelper) {
    	this.ruleApplicationContextHelper = ruleApplicationContextHelper;
    }

	public void setRuleSystemWebServiceDao(ISystemWebServiceDao ruleSystemWebServiceDao) {
    	this.ruleSystemWebServiceDao = ruleSystemWebServiceDao;
    }

	public String invodeService(String serviceName, Map<String, Object> params) {
		if(!BlankUtil.isBlank(serviceName)) {
			SystemWebService ws = ruleSystemWebServiceDao.getByName(serviceName);
			if(ws == null || BlankUtil.isBlank(ws.getServiceEntry()) || ws.getServiceType() == null) {
				return RuleWSResultVO.createFailVoStr("serviceName=" + serviceName + " 不存在, 或没设置service 方法, 或没设置service type");
			}
			
			try {
				switch(ws.getServiceType()) {
				case ACTION : 
				case RESTFUL : return requestHttp(ws, params);
				case BEAN : Object result = requestBeanService(ws, params); 
				            if(result != null && result instanceof String) {
				            	return (String)result;
				            } else if (result != null ) {
				            	return result.toString();
				            }
				            return RuleWSResultVO.createFailVoStr("没结果");
				case CRM_SOAP : return requestCRMSoap(ws, params);	
				case CMS_RESTFUL : return requestCMSHttp(ws, params);
				            
				}
			} catch (Exception e) {
				log.error("调用service出错", e);
				return RuleWSResultVO.createFailVoStr(e.getMessage());
			}
		}
		return RuleWSResultVO.createFailVoStr("必须提供serviceName ");
	}
	
	public RuleWSResultVO invodeServiceWithResultVO(String serviceName, Map<String, Object> params) {
		if(!BlankUtil.isBlank(serviceName)) {
			SystemWebService ws = ruleSystemWebServiceDao.getByName(serviceName);
			if(ws == null || BlankUtil.isBlank(ws.getServiceEntry()) || ws.getServiceType() == null) {
				return RuleWSResultVO.createFailResultVo("serviceName=" + serviceName + " 不存在, 或没设置service 方法, 或没设置service type");
			}
			
			try {
				switch(ws.getServiceType()) {
				case ACTION : 
				case RESTFUL : return convertStrToVO(requestHttp(ws, params)); 
				case BEAN : Object result = requestBeanService(ws, params);
							if(result != null && result instanceof String) {
				            	return convertStrToVO((String)result);
				            } else if (result != null ) {
				            	return (RuleWSResultVO)result;
				            }
				            return RuleWSResultVO.createFailResultVo("没结果");
				case CRM_SOAP : return convertStrToVO(requestCRMSoap(ws, params));
				
				case CMS_RESTFUL : return convertStrToVO(requestCMSHttp(ws, params)); 
				}
			} catch (Exception e) {
				log.error("调用service出错", e);
				return RuleWSResultVO.createFailResultVo(e.getMessage());
			}
		}
		return RuleWSResultVO.createFailResultVo("必须提供serviceName ");
	}
	
	/**
	 * soap请求
	 * @param ws
	 * @param params
	 * @return
	 */
	private String requestCRMSoap(SystemWebService ws, Map<String, Object> params) {
//		String strParams = null; 
//		if(params != null) {
//			strParams = JsonUtil.objToJson(params);
//		}
//		String baseUrl = getServiceBaseUrl(ws, params);
//		if(!BlankUtil.isBlank(baseUrl)) {
//			return String.valueOf(WSUtil.invokeWS(baseUrl, 
//			        ws.getServiceEntry(), 
//			        ws.getEntryMethod(), 
//			        ws.getServiceVersion(),
//			        ""));
//		} else {
//			log.error("service必须必须关联系统，serviceName=" + ws.getName() );
			return null;
//		}
		
	}
	
	private RuleWSResultVO convertStrToVO(String result) {
		try {
			if(result.trim().startsWith("{") && result.trim().endsWith("}")) {
				return JsonUtil.jsonToObject(result, RuleWSResultVO.class);
			} else {
				return RuleWSResultVO.createFailResultVo(result);
			}
		} catch(Exception e) {
			log.error("转换webservice结果出错", e);
			return RuleWSResultVO.createFailResultVo(e.getMessage());
		}
	}
	
	private String getUrl(SystemWebService ws, String baseUrl) {
		if(ServiceType.ACTION.equals(ws.getServiceType())) {
			if(baseUrl.endsWith("/")) {
				baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
			}
			return baseUrl + "?" + SERVICE_METHOD + "=" + ws.getServiceEntry();
		} else if(ServiceType.RESTFUL.equals(ws.getServiceType()) || ServiceType.CMS_RESTFUL.equals(ws.getServiceType())) {
			if(!baseUrl.endsWith("/")) {
				baseUrl += "/"; 
			}
			return baseUrl + ws.getServiceEntry();
		}
		return baseUrl;
	}
	

	/**
	 * 本系统service bean 调用
	 * @param ws
	 * @param params
	 * @return
	 */
	private Object requestBeanService(SystemWebService ws,  Map<String, Object> params) {
		if(BlankUtil.isBlank(ws.getServiceBeanName())) {
			return RuleWSResultVO.createFailResultVo("please set service bean name when type is bean");
		}
		Object service = ruleApplicationContextHelper.getContext().getBean(ws.getServiceBeanName());
		String methodName = ws.getServiceEntry();
		try {
			if(service == null || methodName == null || (service.getClass().getMethod(methodName, Map.class) == null)) {
				return RuleWSResultVO.createFailVoStr("please set service bean name and method!");
			}
			return MethodUtils.invokeMethod(service, methodName, params);
		} catch (Exception e) {
			log.error("调用 bean 类service出错", e);
			return RuleWSResultVO.createFailResultVo(e.getMessage());
		}
	}
	
	private String requestCMSHttp(SystemWebService ws, Map<String, Object> params) {
		HttpResult result = getHttpRequestResult(ws, params, false);
		if(result != null  ) {
			//要把cms的结果转一下。
			if(result.isOk()) {
				String content = result.getContent();
				CMSWSResultVO vo = JsonUtil.jsonToObject(content, CMSWSResultVO.class);
				if(vo != null) {
					return vo.convert2WSResult().toString();
				}
			} else {
				String msg = result.getContent() != null ? result.getContent() :"http请求异常！";
				return RuleWSResultVO.createFailVoStr(msg);
			}
		} 
		
		return RuleWSResultVO.createFailVoStr("serviceName=" + ws.getName() + "没有请求结果");
	}
	
	/**
	 * http请求
	 * @param ws
	 * @param allParams
	 * @return
	 */
	private String requestHttp(SystemWebService ws, Map<String, Object> params) {
		HttpResult result = getHttpRequestResult(ws, params, false);
		if(result != null  ) {
			if(result.isOk()) {
				return result.getContent();
			} else {
				String msg = result.getContent() != null ? result.getContent() :"http请求异常！";
				return RuleWSResultVO.createFailVoStr(msg);
			}
		} else {
			return RuleWSResultVO.createFailVoStr("serviceName=" + ws.getName() + "没有请求结果");
		}
	}
	
	/**
	 * 
	 * @param ws
	 * @param params
	 * @param expandParams 是否把map中的参数展开传改接口端，否则全部转为json放在conditions参数名下
	 * @return
	 */
	private HttpResult getHttpRequestResult(SystemWebService ws, Map<String, Object> params, boolean expandParams) {
		String baseUrl = getServiceBaseUrl(ws, params);
		if(!BlankUtil.isBlank(baseUrl) ) {
			Map<String, String> allParams = new HashMap<String, String>();
			if(params == null) {
				params = new HashMap<String, Object>();
			}
			if(!BlankUtil.isBlank(ws.getEntryMethod())) {
				params.put(ENTRY_METHOD, ws.getEntryMethod());
			}
			
			if(!BlankUtil.isBlank(params)) {
				if(expandParams) {
					for(String key : params.keySet()) {
						allParams.put(key, JsonUtil.objToJson(params.get(key)));
					}
				} else if(!ServiceType.CMS_RESTFUL.equals(ws.getServiceType())){
					allParams.put(CONDITIONS, JsonUtil.objToJson(params));
				}
			}
			if(ServiceType.CMS_RESTFUL.equals(ws.getServiceType())) {
				String key = ruleSystemConfigService.getValueByName(CMS_WEB_SERVICE_KEY);
				allParams.put(CMS_KEY_PARAM_NAME, key);
				if(!BlankUtil.isBlank(params)) {
					allParams.put(CMS_DATA_PARAM_NAME,  JsonUtil.objToJson(params));
				}
			}
			String url = getUrl(ws, baseUrl);
			return HttpClientUtil.sendHttpRequest(allParams, url, true); 
		} else {
			log.error("service必须必须关联系统，serviceName=" + ws.getName() );
			return null;
		}
	}
	
	private String getServiceBaseUrl(SystemWebService ws, Map<String, Object> params) {
		SystemServiceUrl serviceUrl = getSystemUrl(ws, params);
		if(serviceUrl != null) {
			return serviceUrl.getBaseUrl();
		}
		if(ws != null && ws.getBizSystem() != null) {
			return ws.getBizSystem().getBaseUrl();
		}
		return null;
	}
	
	private SystemServiceUrl getSystemUrl(SystemWebService ws, Map<String, Object> params) {
		String systemCode = (String)params.get(SYSTEM_CODE_PARAM);
		if(ws != null && ws.getBizSystem() != null && !BlankUtil.isBlank(systemCode) ) {
			return ruleSystemServiceUrlDao.get(ws.getBizSystem().getName(), systemCode);
		}
		return null;
	}
}
