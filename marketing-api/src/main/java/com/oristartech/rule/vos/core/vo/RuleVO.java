package com.oristartech.rule.vos.core.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateUtil;
import com.oristartech.rule.common.util.HashCodeUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;
import com.oristartech.rule.vos.core.enums.RuleExecuteMode;
import com.oristartech.rule.vos.core.enums.RuleStatus;

public class RuleVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6817710071608359975L;

	private Integer id;
	
	/**
	 * 组id
	 */
	private Integer ruleGroupId;
	
	/**
	 * 组名称
	 */
	private String ruleGroupName;
	
	/**
	 * 组备注
	 */
	private String ruleGroupRemark;
	
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 顺序号
	 */
	private Integer seqNum;
	/**
	 * 优先级,值越大,越优先.
	 */
	private Integer priority;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 规则状态
	 */
	private RuleStatus status;
	/**
	 * 规则类型
	 */
	private String ruleType;
	
	/**
	 * 执行模式
	 */
	private RuleExecuteMode executeMode;
	
	//创建日期
	private Date createDate;
	
	//修改日期
	private Date modifyDate;

	/**
	 * 版本号
	 */
	private Integer version;
	
	/**
	 * 规则块
	 */
	private List<RuleSectionVO> ruleSections = new ArrayList<RuleSectionVO>();
	
	/**
	 * 公共条件section
	 */
	private RuleSectionVO commonSection ;
	
	/**
	 * 有效开始时间, 若条件里的有效时间是分段的, 则要用最开始时间
	 */
	private Date validDateStart;
	
	/**
	 * 结束时间, 若条件里的有效时间是分段的, 则要用最后时间
	 */
	private Date validDateEnd;
	
	/**
	 * 关联的业务编码. 例如活动编号
	 */
	private String bizOrderCode;
	
	/**
	 * 额外业务属性. 额外的业务系统需要保存的规则属性. json字符串对象
	 * bizProperties没有getter, 因为外部使用bizPropertyMap访问
	 */
	@JsonIgnore
	private String bizProperties;
	
	/**
	 * 方便外部访问 额外业务属性. 即可以通过对象方式访问. 内部依然用bizProperties设置到DO里面
	 */
	private Map<String, Object> bizPropertyMap = new HashMap<String, Object>();
	
	/**
	 * 里面的条件是否已经merge过, 内部使用，主要用于在缓存取出时，可以不在merge
	 */
	@JsonIgnore
	private boolean isMerge = false;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		RuleVO other = (RuleVO)obj;
		return new EqualsBuilder().append(this.id, other.id).isEquals(); 
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(HashCodeUtil.initialNonZeroOddNumber, HashCodeUtil.multiplierNonZeroOddNumber)
		                        .append(id).toHashCode();
	}
	
	/**
	 * @return the isMerge
	 */
	public boolean isMerge() {
		return isMerge;
	}


	/**
	 * @param isMerge the isMerge to set
	 */
	public void setMerge(boolean isMerge) {
		this.isMerge = isMerge;
	}
	
	/**
	/**
	 * 方便外部访问 额外业务属性方法
	 * @param name
	 * @return
	 */
	@JsonIgnore
	public Object getBizProperty(String name) {
		if(getBizPropertyMap() != null && !BlankUtil.isBlank(name)) {
			return getBizPropertyMap().get(name);
		}
		return null;
	}
	
	
	
	public boolean existConditionCategory(String categoryName) {
		if(!BlankUtil.isBlank(categoryName) ) {
			if(commonSection != null) {
				if(!BlankUtil.isBlank(commonSection.findConditions(categoryName))) {
					return true;
				}
			}
			if(!BlankUtil.isBlank(this.ruleSections)) {
				for(RuleSectionVO sectionVO : ruleSections) {
					if(!BlankUtil.isBlank(sectionVO.findConditions(categoryName))) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 条件中是否存在指定的条件 
	 * @param categoryName
	 * @param fieldName
	 * @return
	 */
	public boolean existConditionElement(String categoryName, String fieldName) {
		if(!BlankUtil.isBlank(categoryName) && !BlankUtil.isBlank(fieldName)) {
			if(commonSection != null) {
				if(!BlankUtil.isBlank(commonSection.findConditionElements(categoryName, fieldName))) {
					return true;
				}
			}
			if(!BlankUtil.isBlank(this.ruleSections)) {
				for(RuleSectionVO sectionVO : ruleSections) {
					if(!BlankUtil.isBlank(sectionVO.findConditionElements(categoryName, fieldName))) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 返回规则中所有条件
	 */
	@JsonIgnore
	public List<RuleConditionVO> findAllRuleConditionVOs() {
		List<RuleConditionVO> result = new ArrayList<RuleConditionVO>();
		if(!BlankUtil.isBlank(this.ruleSections)) {
			for(RuleSectionVO sectionVO : ruleSections){
				result.addAll(sectionVO.getRuleConditions());
			}
		}
		return result;
	}
	
	/**
	 * 获取指定分类的条件
	 * @param categoryName
	 * @return
	 */
	public List<RuleConditionVO> findConditions(String categoryName) {
		List<RuleConditionVO> result = new ArrayList<RuleConditionVO>();
		if(!BlankUtil.isBlank(ruleSections)) {
			for(RuleSectionVO sectionVO : ruleSections) {
				List<RuleConditionVO> cons = sectionVO.findConditions(categoryName);
				if(!BlankUtil.isBlank(cons)) {
					result.addAll(cons);
				}
			}
		}
		return result;
	}
	
	/**
	 * 查找指定section里的条件
	 * @param sectionId
	 * @param categoryName
	 * @return
	 */
	public List<RuleConditionVO> findConditions(Long sectionId, String categoryName) {
		List<RuleConditionVO> result = new ArrayList<RuleConditionVO>();
		if(!BlankUtil.isBlank(ruleSections)) {
			for(RuleSectionVO sectionVO : ruleSections) {
				if(sectionId.equals(sectionVO.getId())) {
					List<RuleConditionVO> cons = sectionVO.findConditions(categoryName);
					if(!BlankUtil.isBlank(cons)) {
						result.addAll(cons);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据id查询条件
	 * @param id
	 * @return
	 */
	public RuleConditionVO findConditionById(Long id) {
		if(!BlankUtil.isBlank(ruleSections)) {
			for(RuleSectionVO sectionVO : ruleSections) {
				List<RuleConditionVO> cons = sectionVO.getRuleConditions();
				if(!BlankUtil.isBlank(cons)) {
					for(RuleConditionVO con :cons) {
						if(con.getId().equals(id)) {
							return con;
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取条件中属性的操作符号, 和操作数,同一属性可以多个条件,所以返回list, list中没一个是string数组,0位置是操作符,1是操作数.
	 * @param categoryName
	 * @param fieldName
	 * @return
	 */
	public List<String[]> findOpAndOperand(String categoryName, String fieldName) {
		List<RuleConditionElementVO> eleVos = findConditionElements(categoryName, fieldName);
		List<String[]> results = null;
		if(!BlankUtil.isBlank(eleVos)) {
			results = new ArrayList<String[]>();
			for(RuleConditionElementVO ele : eleVos) {
				String[] ops = new String[2];
				ops[0] = ele.getOpUniqueName();
				ops[1] = ele.getOperand();
				results.add(ops);
			}
		}
		return results;
	}
	
	/**
	 * 根据分类名和属性名返回条件, 方便前端直接显示某几个条件.
	 * @param categoryName
	 * @param fieldName
	 * @return
	 */
	public List<RuleConditionElementVO> findConditionElements (String categoryName, String fieldName) {
		List<RuleConditionElementVO> result = new ArrayList<RuleConditionElementVO>(); 
		if(!BlankUtil.isBlank(ruleSections)) {
			for(RuleSectionVO sectionVO : ruleSections) {
				List<RuleConditionElementVO> els = sectionVO.findConditionElements(categoryName, fieldName);
				if(!BlankUtil.isBlank(els)) {
					result.addAll(els);
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取第一个出现的指定条件
	 * @param categoryName
	 * @param fieldName
	 * @return
	 */
	public RuleConditionElementVO findFirstConditionElement(String categoryName, String fieldName) {
		if(!BlankUtil.isBlank(ruleSections)) {
			for(RuleSectionVO sectionVO : ruleSections) {
				List<RuleConditionElementVO> els = sectionVO.findConditionElements(categoryName, fieldName);
				if(!BlankUtil.isBlank(els)) {
					return els.get(0);
				}
			}
		}
		return null;
	}
	/**
	 * 为了方便生成规则文件, 把同一括号父条件放到所有子条件中, 把同一括号中相同条件整合到同一个类中.
	 */
	public void mergeConditons(List<ModelCategoryVO> mergeCategoryVOs) {
		if(!BlankUtil.isBlank(ruleSections)) {
			for(RuleSectionVO sectionVO : ruleSections) {
				moveCommonCondition(sectionVO);
				sectionVO.mergeConditons(mergeCategoryVOs);
			}
			//需要设为null, 若不空,rule.ftl会继续生成commonSection的条件
			commonSection = null;
		}
		
	}
	
	/**
	 * 把公共条件移动到规则条件里面
	 */
	private void moveCommonCondition(RuleSectionVO sectionVO) {
		if(commonSection != null && !BlankUtil.isBlank(commonSection.getRuleConditions()) ) {
			//clone一个,用来merge,避免merge时,干扰到同一rulegroup下的commonsection.
			List<RuleConditionVO> conditions = (List)SerializationUtils.clone((ArrayList)commonSection.getRuleConditions());
			
			//若原有rule包含或condition(不是conditionElement), 不适合merge, 现在只有与
			if(!BlankUtil.isBlank(sectionVO.getRuleConditions())) {
				sectionVO.getRuleConditions().addAll(0, conditions);
			} else {
				sectionVO.setRuleConditions(conditions);
			}
		}
	}
	
	/**
	 * 获取第一个出现的指定条件值
	 * @param categoryName
	 * @param fieldName
	 * @return
	 */
	public String findFirstConditionElementValue(String categoryName, String fieldName) {
		if(!BlankUtil.isBlank(ruleSections) && !BlankUtil.isBlank(categoryName) && !BlankUtil.isBlank(fieldName)) {
			for(RuleSectionVO sectionVO : ruleSections) {
				List<RuleConditionElementVO> els = sectionVO.findConditionElements(categoryName, fieldName);
				if(!BlankUtil.isBlank(els)) {
					return els.get(0).getOperand();
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取第一个指定section出现的指定条件值
	 * @param categoryName
	 * @param fieldName
	 * @return
	 */
	public String findFirstConditionElementValue(Long sectionId, String categoryName, String fieldName) {
		if(!BlankUtil.isBlank(ruleSections) && sectionId != null && !BlankUtil.isBlank(categoryName) && !BlankUtil.isBlank(fieldName)) {
			for(RuleSectionVO sectionVO : ruleSections) {
				if(sectionId.equals(sectionVO.getId())) {
					List<RuleConditionElementVO> els = sectionVO.findConditionElements(categoryName, fieldName);
					if(!BlankUtil.isBlank(els)) {
						return els.get(0).getOperand();
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 榨取规则项中的条件,格式同fact格式, 而且不包含比较符信息
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> extractSectionConditions(Long sectionId) {
		if(!BlankUtil.isBlank(ruleSections) && sectionId != null) {
			for(RuleSectionVO section : ruleSections) {
				if(sectionId.equals(section.getId())) {
					return section.extractConditions();
				}
			}
		}
	    return null;
    }
	
	/**
	 * 获取规则说有执行方法
	 * @return
	 */
	@JsonIgnore
	public List<RuleActionVO> findAllActions() {
		List<RuleActionVO> result = null;
		if(!BlankUtil.isBlank(ruleSections)) {
			result = new ArrayList<RuleActionVO>();
			for(RuleSectionVO section : ruleSections) {
				if(!BlankUtil.isBlank(section.getRuleActions())) {
					result.addAll(section.getRuleActions());
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取规则中出现的第一个指定动作. 有些动作只能添加一个到规则里面, 这时方便获取
	 * @param fnName 方法名称
	 * @return
	 */
	public RuleActionVO findFirstAction(String fnName) {
		if(!BlankUtil.isBlank(ruleSections) && !BlankUtil.isBlank(fnName)) {
			for(RuleSectionVO section : ruleSections) {
				RuleActionVO vo = section.findFirstAction(fnName);
				if(vo != null) {
					return vo;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取规则中出现的第一个指定动作里面的参数.
	 * 有些动作只能添加一个到规则里面, 这时方便获取
	 * 
	 * @param fnName 对应的方法名称
	 * @return
	 */
	public Map<String, String> findFirstActionParam(String fnName) {
		RuleActionVO action = findFirstAction(fnName);
		if(action != null) {
			return action.getParameterMap();
		}
		return null;
	}
	
	/**
	 * 获取指定action
	 * @param actionId
	 * @return
	 */
	public RuleActionVO findAction(Long actionId) {
		if(!BlankUtil.isBlank(ruleSections) && !BlankUtil.isBlank(actionId)) {
			for(RuleSectionVO section : ruleSections) {
				RuleActionVO vo = section.findAction(actionId);
				if(vo != null) {
					return vo;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取指定action的所有参数, 包含lazy是true的参数
	 * @param actionId
	 * @return
	 */
	public Map<String, String> findAllActionParam(Long actionId) {
		RuleActionVO vo = findAction(actionId);
		if(vo != null) {
			return vo.getAllParameterMap();
		}
		return null;
	}
	
	/**
	 * 获取指定action的参数, 但不包含lazy是true的参数, 及文件参数, 因为这些参数比较大, 也不包含配置参数
	 * 但包含config参数
	 * @param actionId
	 * @return
	 */
	public Map<String, String> findActionParam(Long actionId) {
		RuleActionVO vo = findAction(actionId);
		if(vo != null) {
			return vo.getParameterMap();
		}
		return null;
	}
	
	/**
	 * 获取规则中出现的第一个指定动作里面的指定参数值.
	 * 有些动作只能添加一个到规则里面, 这时方便获取
	 * 
	 * @param fnName 对应的方法名称
	 * @return
	 */
	public String findFirstActionParamValue(String fnName, String paramName) {
		if(!BlankUtil.isBlank(paramName)) {
			Map<String, String> paramMap = findFirstActionParam(fnName);
			if(paramMap != null) {
				return paramMap.get(paramName);
			}
		}
		return null;
	}
	
	/**
	 * 删除规则中指定条件
	 * @param ruleConditions
	 * @param category
	 * @param field
	 */
	public void removeConditionEle(String category, String field) {
		if(!BlankUtil.isBlank(getRuleSections())) {
			for(RuleSectionVO section : getRuleSections()) {
				section.removeConditionEle(category, field);
			}
		}
    }
	
	public String getRuleGroupRemark() {
    	return ruleGroupRemark;
    }

	public void setRuleGroupRemark(String ruleGroupRemark) {
    	this.ruleGroupRemark = ruleGroupRemark;
    }


	public RuleSectionVO getCommonSection() {
    	return commonSection;
    }

	public void setCommonSection(RuleSectionVO commonSection) {
    	this.commonSection = commonSection;
    }

	public String getRuleGroupName() {
    	return ruleGroupName;
    }

	public void setRuleGroupName(String ruleGroupName) {
    	this.ruleGroupName = ruleGroupName;
    }

	public Integer getId() {
    	return id;
    }

	public void setId(Integer id) {
    	this.id = id;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public Integer getPriority() {
    	return priority;
    }

	public void setPriority(Integer priority) {
    	this.priority = priority;
    }

	public String getRemark() {
    	return remark;
    }

	public void setRemark(String remark) {
    	this.remark = remark;
    }

	public RuleStatus getStatus() {
    	return status;
    }

	public void setStatus(RuleStatus status) {
    	this.status = status;
    }

	public String getRuleType() {
    	return ruleType;
    }

	public void setRuleType(String ruleType) {
    	this.ruleType = ruleType;
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

	public Integer getRuleGroupId() {
    	return ruleGroupId;
    }

	public void setRuleGroupId(Integer ruleGroupId) {
    	this.ruleGroupId = ruleGroupId;
    }

	public List<RuleSectionVO> getRuleSections() {
    	return ruleSections;
    }

	public void setRuleSections(List<RuleSectionVO> ruleSections) {
    	this.ruleSections = ruleSections;
    }

	public Integer getVersion() {
    	return version;
    }

	public void setVersion(Integer version) {
    	this.version = version;
    }

	public RuleExecuteMode getExecuteMode() {
    	return executeMode;
    }

	public void setExecuteMode(RuleExecuteMode executeMode) {
    	this.executeMode = executeMode;
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

	/**
	 * 结束时间字符串, 若条件里的有效时间是分段的, 则要用最后时间
	 */
	public String getValidTimeStartStr() {
    	return DateUtil.convertDateToStr(this.validDateStart, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
    }
	
	/**
	 * 结束时间, 若条件里的有效时间是分段的, 则要用最后时间
	 */
	public String getValidTimeEndStr() {
		return DateUtil.convertDateToStr(this.validDateEnd, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
    }
	
	/**
	 * drool文件结束时间, 有效期后一天
	 */
	@JsonIgnore
	public String getDroolsValidTimeEndStr() {
		if(this.validDateEnd != null) {
			Calendar ca = Calendar.getInstance();
			ca.setTime(this.validDateEnd);
			ca.add(Calendar.DAY_OF_MONTH, 1);
			return DateUtil.convertDateToStr(ca.getTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
		}
		return null;
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

	public Map<String, Object> getBizPropertyMap() {
		if(!BlankUtil.isBlank(this.bizProperties )) {
			return JsonUtil.jsonToObject(this.bizProperties, Map.class);
		}
    	return bizPropertyMap;
    }

	public void setBizPropertyMap(Map<String, Object> bizPropertyMap) {
    	this.bizPropertyMap = bizPropertyMap;
    	this.bizProperties = JsonUtil.objToJson(this.bizPropertyMap);
    }
}
