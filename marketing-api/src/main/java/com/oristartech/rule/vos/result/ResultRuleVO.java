package com.oristartech.rule.vos.result;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;

/**
 * 规则执行结果返回简单的规则信息.
 * 只定义需要返回的规则信息
 * @author user
 *
 */
public class ResultRuleVO extends BaseResultRuleVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3448755253971774180L;

	/**
	 * 关联的业务编码. 例如活动编号
	 */
	private String bizOrderCode;
	
	/**
	 * 优先级
	 */
	@JsonIgnore
	private int priority;
	
	/**
	 * 规则类型
	 */
	@JsonIgnore
	private String ruleType;
	
	/**
	 * 额外业务属性. 额外的业务系统需要保存的规则属性. json字符串对象
	 * bizProperties没有getter, 因为外部使用bizPropertyMap访问
	 */
	@JsonIgnore
	private String bizProperties;
	
	/**
	 * 执行模式
	 */
	private String executeMode;
	
	/**
	 * 方便外部访问 额外业务属性. 即可以通过对象方式访问. 内部依然用bizProperties设置到DO里面
	 */
	private Map<String, Object> bizPropertyMap ;
	
	/**
	 * 组活动备注 
	 */
	private String ruleGroupRemark;
	
	/**
	 * 备注 
	 */
	private String remark;
	
	public String getRuleGroupRemark() {
    	return ruleGroupRemark;
    }

	public void setRuleGroupRemark(String ruleGroupRemark) {
    	this.ruleGroupRemark = ruleGroupRemark;
    }

	public String getRemark() {
    	return remark;
    }

	public void setRemark(String remark) {
    	this.remark = remark;
    }

	public String getExecuteMode() {
    	return executeMode;
    }

	public void setExecuteMode(String executeMode) {
    	this.executeMode = executeMode;
    }

	public Map<String, Object> getBizPropertyMap() {
		if(!BlankUtil.isBlank(this.bizProperties )) {
			return JsonUtil.jsonToObject(this.bizProperties, Map.class);
		}
    	return bizPropertyMap;
    }


	public String getBizOrderCode() {
    	return bizOrderCode;
    }

	public void setBizOrderCode(String bizOrderCode) {
    	this.bizOrderCode = bizOrderCode;
    }

	public String getBizProperties() {
		return bizProperties;
	}

	public void setBizProperties(String bizProperties) {
		this.bizProperties = bizProperties;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
