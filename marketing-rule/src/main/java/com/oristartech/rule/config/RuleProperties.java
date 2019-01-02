package com.oristartech.rule.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rule")
@PropertySource(value = "classpath:/rule.properties", encoding = "UTF-8")
public class RuleProperties {

	public String ruleFtl;
	public String ruleModelFtl;
	public String droolModelFtl;
	public String clientip;
	public String clientport;
	public String initLoad;
	public String isJmx;
	public String clientTaskNum;
	
	public String getClientip() {
		return clientip;
	}
	public void setClientip(String clientip) {
		this.clientip = clientip;
	}
	public String getClientport() {
		return clientport;
	}
	public void setClientport(String clientport) {
		this.clientport = clientport;
	}
	public String getRuleFtl() {
		return ruleFtl;
	}
	public void setRuleFtl(String ruleFtl) {
		this.ruleFtl = ruleFtl;
	}
	public String getRuleModelFtl() {
		return ruleModelFtl;
	}
	public void setRuleModelFtl(String ruleModelFtl) {
		this.ruleModelFtl = ruleModelFtl;
	}
	public String getDroolModelFtl() {
		return droolModelFtl;
	}
	public void setDroolModelFtl(String droolModelFtl) {
		this.droolModelFtl = droolModelFtl;
	}
	public String getInitLoad() {
		return initLoad;
	}
	public void setInitLoad(String initLoad) {
		this.initLoad = initLoad;
	}
	public String getIsJmx() {
		return isJmx;
	}
	public void setIsJmx(String isJmx) {
		this.isJmx = isJmx;
	}
	public String getClientTaskNum() {
		return clientTaskNum;
	}
	public void setClientTaskNum(String clientTaskNum) {
		this.clientTaskNum = clientTaskNum;
	}
}