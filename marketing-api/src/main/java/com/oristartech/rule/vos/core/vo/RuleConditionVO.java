package com.oristartech.rule.vos.core.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.HashCodeUtil;
import com.oristartech.rule.constants.FactConstants;

public class RuleConditionVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8334288940223990566L;

	private Long id;

	/**
	 * 包含条件元素
	 */
	private List<RuleConditionElementVO> conditionElements;
	/**
	 * 所属规则块
	 */
	private Long ruleSectionId;
	/**
	 * 顺序号
	 */
	private Integer seqNum;
	
	/**
	 * 业务分类id
	 */
	private Integer modelCategoryId;
	
	/**
	 * 分类名称
	 */
	private String modelCategoryName;
	
	/**
	 * 父分类名称
	 */
	private String modelCategoryParentName;
	
	/**
	 * 对应的fieldGroupId， 定义时动态产生的条件用到。 可空
	 */
	private Integer fieldGroupId;
	
	/**
	 * 条件分类前导修饰符号, 例如exists, not等
	 */
	private String modifier;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		RuleConditionVO other = (RuleConditionVO)obj;
		return new EqualsBuilder().append(this.id, other.id).isEquals(); 
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(HashCodeUtil.initialNonZeroOddNumber, HashCodeUtil.multiplierNonZeroOddNumber)
		                        .append(id).toHashCode();
	}
	
	public void addConditionEle(RuleConditionElementVO vo) {
		if(vo == null) {
			return;
		}
		if(this.conditionElements == null) {
			this.conditionElements = new ArrayList<RuleConditionElementVO>();
		}
		this.conditionElements.add(vo);
	}
	/**
	 * 是否存在指定属性条件元素
	 * @param fieldName
	 * @return
	 */
	public boolean existConditionElement(String fieldName) {
		if(!BlankUtil.isBlank(fieldName) && !BlankUtil.isBlank(conditionElements)) {
			for(RuleConditionElementVO conEle : conditionElements) {
				if(fieldName.equals(conEle.getModelFieldName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取第一个子条件
	 * @param fieldName
	 * @return
	 */
	public  RuleConditionElementVO findElementVO(String fieldName) {
		if(!BlankUtil.isBlank(fieldName) && !BlankUtil.isBlank(conditionElements)) {
			for(RuleConditionElementVO conEleVO : conditionElements) {
				if(conEleVO.getModelFieldName().equals(fieldName)) {
					return conEleVO;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取本条件类下所有同名属性的条件
	 * @param fieldName
	 * @return
	 */
	public  List<RuleConditionElementVO> findElementVOs(String fieldName) {
		List<RuleConditionElementVO> result = new ArrayList<RuleConditionElementVO>();
		if(!BlankUtil.isBlank(fieldName) && !BlankUtil.isBlank(conditionElements)) {
			for(RuleConditionElementVO conEleVO : conditionElements) {
				if(conEleVO.getModelFieldName().equals(fieldName)) {
					result.add(conEleVO);
				}
			}
		}
		return result;
	}
	/**
	 * 榨取规则项中的条件,格式同fact格式, 而且不包含比较符信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> extractConditions() {
		if(!BlankUtil.isBlank(conditionElements)) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put(FactConstants.CATEGORY_TYPE_KEY, this.modelCategoryName);
			for(RuleConditionElementVO conEleVO : conditionElements) {
				result.put(conEleVO.getModelFieldName(), conEleVO.getOperand());
			}
			return result;
		}
	    return null;
    }
	
	public String getModelCategoryParentName() {
    	return modelCategoryParentName;
    }

	public void setModelCategoryParentName(String modelCategoryParentName) {
    	this.modelCategoryParentName = modelCategoryParentName;
    }

	public String getModifier() {
    	return modifier;
    }

	public void setModifier(String modifier) {
    	this.modifier = modifier;
    }

	public Long getId() {
    	return id;
    }

	public void setId(Long id) {
    	this.id = id;
    }

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public Long getRuleSectionId() {
    	return ruleSectionId;
    }

	public void setRuleSectionId(Long ruleSectionId) {
    	this.ruleSectionId = ruleSectionId;
    }

	public String getModelCategoryName() {
		return modelCategoryName;
	}

	public void setModelCategoryName(String modelCategoryName) {
		this.modelCategoryName = modelCategoryName;
	}

	public Integer getModelCategoryId() {
		return modelCategoryId;
	}

	public void setModelCategoryId(Integer modelCategoryId) {
		this.modelCategoryId = modelCategoryId;
	}

	public List<RuleConditionElementVO> getConditionElements() {
    	return conditionElements;
    }

	public void setConditionElements(List<RuleConditionElementVO> conditionElements) {
    	this.conditionElements = conditionElements;
    }

	public Integer getFieldGroupId() {
		return fieldGroupId;
	}

	public void setFieldGroupId(Integer fieldGroupId) {
		this.fieldGroupId = fieldGroupId;
	}
}
