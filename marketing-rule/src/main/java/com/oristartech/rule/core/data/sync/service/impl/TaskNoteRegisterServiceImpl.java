package com.oristartech.rule.core.data.sync.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.RuleRuntimeBaseException;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.config.RuleProperties;
import com.oristartech.rule.core.config.service.ISystemConfigService;
import com.oristartech.rule.core.data.sync.service.ITaskNoteRegisterService;
import com.oristartech.rule.core.ws.client.service.IRuleExternDataService;
import com.oristartech.rule.vos.ws.vo.RuleWSResultVO;


/**
 * 规则节点注册
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class TaskNoteRegisterServiceImpl implements ITaskNoteRegisterService {

	private static Logger log = LoggerFactory.getLogger(TaskNoteRegisterServiceImpl.class);
	@Autowired
	private RuleProperties ruleSystemConfigProps;
	@Autowired
	private ISystemConfigService ruleSystemConfigService;
	@Autowired
	private IRuleExternDataService ruleExternDataService;
	private static final String SYSTEM_NAME = "IS_SAVE";
	
	
//	public void setRuleSystemConfigService(ISystemConfigService ruleSystemConfigService) {
//    	this.ruleSystemConfigService = ruleSystemConfigService;
//    }
//	
//	public void setRuleExternDataService(IRuleExternDataService ruleExternDataService) {
//		this.ruleExternDataService = ruleExternDataService;
//	}
	
	public void run(){
		try {
			tryRegister();
		} catch (Exception e) {
			log.error("注册节点错误", e );
		}
	}

	private void tryRegister() throws Exception {
		Map<String, Object> params = getParams();
		while (true) {
			try {
				if(this.sendRegisterRequest(params)) {
					break;
				} 
			} catch (Exception e) {
				//其他异常，例如超时，继续尝试
				log.error("------注册节点异常，5分钟后继续尝试-----", e);
			}
			Thread.sleep(5*60*1000);
		}
	}
	
	private boolean sendRegisterRequest(Map<String, Object> params){
		RuleWSResultVO result = ruleExternDataService.findExternDataResultVO("CheckTaskNote", params);
		if(!result.isOk() && result.getCode() != null){
			//业务失败，退出
			log.error("注册节点失败,退出注册，原因：" + result.getMessage());
			return true;
		} else if(!result.isOk()){
			log.error("=========注册节点失败，5分钟后继续尝试=========, msg=" + result.getMessage());
			return false;
		} else if(result.isOk()){
			return true;
		}
		return false;
	}
	
	private Map<String, Object> getParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		
		String ip = ruleSystemConfigProps.getClientip();
		String port = ruleSystemConfigProps.getClientport();
		String isSave = ruleSystemConfigService.getValueByName(SYSTEM_NAME);
		
		log.info("==========开始注册规则运行节点=========，ip=" + ip + ",poart=" + port + ",isSave=" + isSave);
		
		if(BlankUtil.isBlank(ip)) {
			throw new RuleRuntimeBaseException("请提供ip参数");
		}
		if(BlankUtil.isBlank(port)) {
			throw new RuleRuntimeBaseException("请提供port参数");
		}
		if(BlankUtil.isBlank(isSave)) {
			throw new RuleRuntimeBaseException("请提供isSave参数");
		}
		params.put("ip", ip);
		params.put("port", port);
		params.put("isSave", isSave);
		
		return params;
	}
}
