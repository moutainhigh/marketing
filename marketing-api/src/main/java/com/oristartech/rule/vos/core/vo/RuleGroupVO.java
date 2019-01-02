package com.oristartech.rule.vos.core.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateUtil;
import com.oristartech.rule.common.util.HashCodeUtil;
import com.oristartech.rule.common.util.JsonDateDeserializer;
import com.oristartech.rule.common.util.JsonShortDateSerializer;
import com.oristartech.rule.vos.core.enums.RuleExecuteMode;
import com.oristartech.rule.vos.core.enums.RuleStatus;
import com.oristartech.rule.vos.template.vo.FieldGroupVO;
import com.oristartech.rule.vos.template.vo.FunctionGroupVO;

/**
 * 规则组vo
 */
public class RuleGroupVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9204981760864115435L;

	/**
	 * 租户Id(规则匹配时必填)
	 */
	private Integer tenantId;
	
	/**
	 * 规则组id
	 */
	private Integer id;

	/**
	 * 组名称(通常是活动名称)
	 */
	private String name;
	
	
	/**
	 * 组中共同的规则块
	 */
	private RuleSectionVO ruleSectionVO;

	/**
	 * 优先级
	 */
	private Integer priority;
	/**
	 * 关联的业务编码. 例如活动编号
	 */
	private String bizOrderCode;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date modifyDate;
	
	/**
	 * 组中规则状态
	 */
	private RuleStatus status;
	
	/**
	 * 组中规则
	 */
	private List<RuleVO> rules;
	
	/**
	 * 组中规则类型
	 */
	private String ruleType;
	
	/**
	 * 有效开始时间, 若条件里的有效时间是分段的, 则要用最开始时间
	 */
	@JsonSerialize(using=JsonShortDateSerializer.class)
	@JsonDeserialize(using=JsonDateDeserializer.class)
	private Date validDateStart;
	
	/**
	 * 结束时间, 若条件里的有效时间是分段的, 则要用最后时间
	 */
	@JsonSerialize(using=JsonShortDateSerializer.class)
	@JsonDeserialize(using=JsonDateDeserializer.class)
	private Date validDateEnd;
	
	/**
	 * 执行模式
	 */
	private RuleExecuteMode executeMode;
	
	/**
	 * 关联的模板id, 若不是通过模板建立, 可空
	 */
	private Integer templateId;
	
	/**
	 * 规则组中所有条件用到的属性分组数据. 
	 * 本属性主要用在规则编辑界面,方便编辑. 
	 *  由某些方法初始化.
	 */
	private List<FieldGroupVO> fieldGroups;
	
	/**
	 * 属性分组Map
	 */
	private Map<Integer, FieldGroupVO> fieldGroupMap;
	
	/**
	 * 规则组中所有方法用到的属性分组数据. 
	 * 本属性主要用在规则编辑界面,方便编辑. 
	 * 由某些方法初始化.
	 */
	private List<FunctionGroupVO> funcGroups;
	
	/**
	 * 方法分组Map
	 */
	private Map<Integer, FunctionGroupVO> funcGroupMap;
	
	/**
	 * 备注
	 */
	private String remark;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		RuleGroupVO other = (RuleGroupVO)obj;
		return new EqualsBuilder().append(this.id, other.id).isEquals(); 
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(HashCodeUtil.initialNonZeroOddNumber, HashCodeUtil.multiplierNonZeroOddNumber)
		                        .append(id).toHashCode();
	}
	
	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getRemark() {
    	return remark;
    }

	public void setRemark(String remark) {
    	this.remark = remark;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	/**
	 * 更加ruleid ,查找rule信息. 
	 * @param ruleId
	 * @return
	 */
	public RuleVO findRuleVO(Integer ruleId) {
		if(ruleId != null && !BlankUtil.isBlank(this.getRules())) {
			for(RuleVO ruleVO : this.getRules()) {
				if(ruleId.equals(ruleVO.getId())) {
					return ruleVO;
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据分类名和属性名返回公共条件
	 * @param categoryName
	 * @param fieldName
	 * @return
	 */
	public List<RuleConditionElementVO> findCommonConditionEles (String categoryName, String fieldName) {
		List<RuleConditionElementVO> result = new ArrayList<RuleConditionElementVO>(); 
		if(ruleSectionVO != null) {
			return ruleSectionVO.findConditionElements(categoryName, fieldName);
		}
		return null;
	}
	
	/**
	 * 查找指定的条件名称. 从公共条件和每条规则条件中查找
	 * @param category
	 * @param field
	 * @return
	 */
	public List<RuleConditionElementVO> findAllConditionElements(String category, String field) {
		List<RuleConditionElementVO> result = new ArrayList<RuleConditionElementVO> ();
		List<RuleConditionElementVO> commonEls = findCommonConditionEles(category, field);
		if(!BlankUtil.isBlank(commonEls)) {
			result.addAll(commonEls);
		}
		
		if(!BlankUtil.isBlank(rules)) {
			for(RuleVO rule : rules) {
				List<RuleConditionElementVO> els = rule.findConditionElements(category, field);
				if(!BlankUtil.isBlank(els)) {
					result.addAll(els);
				}
			}
		}
		return result;
	}
	/**
	 * 根据分类名和属性名返回第一个公共条件, 因为公共条件通常同样的属性只有一个 
	 * 所以只返回一个RuleConditionElementVO
	 * @param categoryName
	 * @param fieldName
	 * @return
	 */
	public RuleConditionElementVO findFirstCommonConditionEle (String categoryName, String fieldName) {
		if(ruleSectionVO != null) {
			 List<RuleConditionElementVO> eles = ruleSectionVO.findConditionElements(categoryName, fieldName);
			 if(!BlankUtil.isBlank(eles)) {
				return eles.get(0);
			 }
		}
		return null;
	}
	
	/**
	 * 根据分类名和属性名返回第一个公共条件的值, 因为不动态的公共条件通常同样的属性只有一个 
	 * 所以只返回一个RuleConditionElementVO
	 * @param categoryName
	 * @param fieldName
	 * @return
	 */
	public String findFirstCommonConditionEleValue (String categoryName, String fieldName) {
		if(ruleSectionVO != null) {
			 List<RuleConditionElementVO> eles = ruleSectionVO.findConditionElements(categoryName, fieldName);
			 if(!BlankUtil.isBlank(eles)) {
				 RuleConditionElementVO eleVO = eles.get(0);
				 return eleVO.getOperand();
			 }
		}
		return null;
	}
	
	/**
	 * 获取common条件中第一个值，而且比较符号必须等于指定值。
	 * @param categoryName
	 * @param fieldName
	 * @param operatorName
	 * @return
	 */
	public String findFirstCommonConditionEleValue(String categoryName, String fieldName, String operatorName) {
		RuleConditionElementVO ele = findFirstCommonConditionEle(categoryName, fieldName);
		if(ele.getOpUniqueName().equals(operatorName) ) {
			return ele.getOperand();
		}
		return null;
	}
	/**
	 * 删除指定公共条件
	 * @param category
	 * @param field
	 */
	public void removeCommonConditionEle(String category, String field) {
		if(ruleSectionVO != null ) {
			ruleSectionVO.removeConditionEle(category, field);
		}
	}
	
	/**
	 * 删除规则中指定条件
	 * @param rule
	 * @param category
	 * @param field
	 */
	public void removeRulesConditionEle(String category, String field) {
		if(!BlankUtil.isBlank(rules)) {
			for(RuleVO rule : rules) {
				rule.removeConditionEle(category, field);
			}
		}
	}
	
	/**
	 * 删除指定条件(包含公共条件和每个规则的条件)
	 * @param category
	 * @param field
	 */
	public void removeConditionEleInAll(String category, String field) {
		if(!BlankUtil.isBlank(category) && !BlankUtil.isBlank(field)) {
			removeCommonConditionEle(category, field);
			
			if(!BlankUtil.isBlank(rules)) {
				for(RuleVO ruleVo : rules) {
					ruleVo.removeConditionEle(category, field);
				}
			}
		}
	}
	
	/**
	 * 有些地方需要全新的规则数据, 保存新的对象, 本方法删除id或业务相关联的属性
	 */
	public void removePropForNew() {
		this.setId(null);
		this.setBizOrderCode(null);
		if(this.getRuleSectionVO() != null) {
			this.getRuleSectionVO().setId(null);
		}
		if(!BlankUtil.isBlank(this.getRules())) {
			for(RuleVO rule : this.getRules()) {
				rule.setId(null);
				rule.setRuleGroupId(null);
				rule.setBizOrderCode(null);
				if(!BlankUtil.isBlank(rule.getRuleSections())) {
					for(RuleSectionVO section : rule.getRuleSections()) {
						section.setId(null);
					}
				}
			}
		}
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RuleSectionVO getRuleSectionVO() {
    	return ruleSectionVO;
    }

	public void setRuleSectionVO(RuleSectionVO ruleSectionVO) {
    	this.ruleSectionVO = ruleSectionVO;
    }

	public Integer getPriority() {
    	return priority;
    }

	public void setPriority(Integer priority) {
    	this.priority = priority;
    }

	public String getBizOrderCode() {
    	return bizOrderCode;
    }

	public void setBizOrderCode(String bizOrderCode) {
    	this.bizOrderCode = bizOrderCode;
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public RuleExecuteMode getExecuteMode() {
    	return executeMode;
    }

	public void setExecuteMode(RuleExecuteMode executeMode) {
    	this.executeMode = executeMode;
    }

	public List<RuleVO> getRules() {
    	return rules;
    }

	public void setRules(List<RuleVO> rules) {
    	this.rules = rules;
    }

	public RuleStatus getStatus() {
    	return status;
    }

	public void setStatus(RuleStatus status) {
    	this.status = status;
    }

	public Date getValidDateStart() {
    	return validDateStart;
    }
	
	public void setValidDateStart(Date validDateStart) {
    	this.validDateStart = validDateStart;
    }

	public Date getValidDateEnd() {
    	return validDateEnd;
    }
	
	public void setValidDateEnd(Date validDateEnd) {
    	this.validDateEnd = validDateEnd;
    }

	public Integer getTemplateId() {
    	return templateId;
    }

	public void setTemplateId(Integer templateId) {
    	this.templateId = templateId;
    }

	public List<FieldGroupVO> getFieldGroups() {
    	return fieldGroups;
    }

	public void setFieldGroups(List<FieldGroupVO> fieldGroups) {
    	this.fieldGroups = fieldGroups;
    }

	public List<FunctionGroupVO> getFuncGroups() {
    	return funcGroups;
    }

	public void setFuncGroups(List<FunctionGroupVO> funcGroups) {
    	this.funcGroups = funcGroups;
    }

	public Map<Integer, FieldGroupVO> getFieldGroupMap() {
		if(!BlankUtil.isBlank(this.fieldGroups) && BlankUtil.isBlank(fieldGroupMap)) {
			fieldGroupMap = new HashMap<Integer, FieldGroupVO>();
			for(FieldGroupVO group : fieldGroups) {
				if(fieldGroupMap.get(group.getId()) == null) {
					fieldGroupMap.put(group.getId(), group);
				}
			}
		}
    	return fieldGroupMap;
    }

	public Map<Integer, FunctionGroupVO> getFuncGroupMap() {
		if(!BlankUtil.isBlank(this.funcGroups) && BlankUtil.isBlank(funcGroupMap)) {
			funcGroupMap = new HashMap<Integer, FunctionGroupVO>();
			for(FunctionGroupVO group : funcGroups) {
				if(funcGroupMap.get(group.getId()) == null) {
					funcGroupMap.put(group.getId(), group);
				}
			}
		}
    	return funcGroupMap;
    }

	public String getRuleType() {
    	return ruleType;
    }

	public void setRuleType(String ruleType) {
    	this.ruleType = ruleType;
    }

	public void setFieldGroupMap(Map<Integer, FieldGroupVO> fieldGroupMap) {
    	this.fieldGroupMap = fieldGroupMap;
    }

	public void setFuncGroupMap(Map<Integer, FunctionGroupVO> funcGroupMap) {
    	this.funcGroupMap = funcGroupMap;
    }
}
