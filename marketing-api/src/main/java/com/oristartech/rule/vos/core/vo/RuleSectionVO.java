package com.oristartech.rule.vos.core.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.HashCodeUtil;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;

/**
 * 规则块vo
 * @author chenjunfei
 *
 */
public class RuleSectionVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2019213189845161637L;

	private Long id;

	/**
	 * section间顺序号
	 */
	private Integer seqNum;
	
	/**
	 * 条件集合
	 */
	private List<RuleConditionVO> ruleConditions = new ArrayList<RuleConditionVO>();
	/**
	 * 规则动作集合
	 */
	private List<RuleActionVO> ruleActions = new ArrayList<RuleActionVO>();

	/**
	 * 归属规则
	 */
	private Integer ruleId;
	
	/**
	 * 关联的规则组,因为规则组中可以有共同的属性和操作
	 */
	private Integer ruleGroupId;
	
	/**
	 * 和紧跟后面的section是否串行关系, null,或false 是并行, true是串行. 
	 */
	private Boolean isSerial;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		RuleSectionVO other = (RuleSectionVO)obj;
		return new EqualsBuilder().append(this.id, other.id).isEquals(); 
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(HashCodeUtil.initialNonZeroOddNumber, HashCodeUtil.multiplierNonZeroOddNumber)
		                        .append(id).toHashCode();
	}
	
	/**
	 * 根据分类名和属性名返回条件, 方便前端直接显示某几个条件.
	 * @param categoryName
	 * @param fieldName
	 * @return
	 */
	public List<RuleConditionElementVO> findConditionElements (String categoryName, String fieldName) {
		if(BlankUtil.isBlank(categoryName) || BlankUtil.isBlank(fieldName)) {
			return null;
		}
		List<RuleConditionElementVO> result = new ArrayList<RuleConditionElementVO>(); 
		if(!BlankUtil.isBlank(ruleConditions)) {
			for(RuleConditionVO conditionVo : ruleConditions) {
				List<RuleConditionElementVO> conditionEleVos = conditionVo.getConditionElements();
				if(!BlankUtil.isBlank(conditionEleVos) && 
				  ( conditionVo.getModelCategoryName().equals(categoryName) 
				    || categoryName.equals(conditionVo.getModelCategoryParentName()))) {
					List<RuleConditionElementVO> conEleVOs = conditionVo.findElementVOs(fieldName);
					if(!BlankUtil.isBlank(conEleVOs)) {
						result.addAll(conEleVOs);
					}
				}
			}
		}
		return result;
	}

	public List<RuleConditionVO> findConditions(String categoryName) {
		if(BlankUtil.isBlank(categoryName)) {
			return null;
		}
		List<RuleConditionVO> result = new ArrayList<RuleConditionVO>(); 
		if(!BlankUtil.isBlank(ruleConditions)) {
			for(RuleConditionVO conditionVo : ruleConditions) {
				if(conditionVo.getModelCategoryName().equals(categoryName)) {
					result.add(conditionVo);
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
	public List<Map<String, Object>> extractConditions() {
		if(!BlankUtil.isBlank(ruleConditions)) {
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			for(RuleConditionVO conditionVo : ruleConditions) {
				result.add(conditionVo.extractConditions());
			}
			return result;
		}
	    return null;
    }
	
	/**
	 * 获取规则动作
	 * @param fnName 对应的方法名称
	 * @return
	 */
	public RuleActionVO findAction(Long actionId) {
		if(!BlankUtil.isBlank(this.ruleActions) && !BlankUtil.isBlank(actionId)) {
			for(RuleActionVO action : ruleActions) {
				if(actionId.equals(action.getId())) {
					return action;
				}
			}
		}
		return null;
    }
	
	/**
	 * 获取规则中出现的第一个指定动作. 有些动作只能添加一个到规则里面, 这时方便获取
	 * @param fnName 对应的方法名称
	 * @return
	 */
	public RuleActionVO findFirstAction(String fnName) {
		if(!BlankUtil.isBlank(this.ruleActions) && !BlankUtil.isBlank(fnName)) {
			for(RuleActionVO action : ruleActions) {
				if(action.getActionFnUniqueName().equals(fnName)) {
					return action;
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
	
	public void addCondition(RuleConditionVO con) {
		if(con == null) {
			return ;
		}
		if(this.ruleConditions == null) {
			this.ruleConditions = new ArrayList<RuleConditionVO>();
		}
		this.ruleConditions.add(con);
	}
	
	/**
	 * 获取规则中出现的第一个指定动作里面的指定参数值.
	 * 有些动作只能添加一个到规则里面, 这时方便获取
	 * 
	 * @param fnName 对应的方法名称
	 * @return
	 */
	public String findFirstActionParamValue(String fnName, String paramName) {
		if(!BlankUtil.isBlank(this.ruleActions) && !BlankUtil.isBlank(fnName) && !BlankUtil.isBlank(paramName)) {
			Map<String, String> paramMap = findFirstActionParam(fnName);
			if(paramMap != null) {
				return paramMap.get(paramName);
			}
		}
		return null;
	}
	
	/**
	 * 把有单独子条件（因为现在的界面有单独的条件添加，若界面是一个类一个类添加的， 即使只有单个条件，也不需要这样，可以删除这些融合的代码）
	 * 或不是多对象（该分类永远只有一个对象事实传递过来）的condition融合。
	 * 有多个子条件的分组 不merge。
	 */
	public void mergeConditons(List<ModelCategoryVO> mergeCategoryVOs) {
		if(!BlankUtil.isBlank(ruleConditions)) {
			this.emptyFieldGroupId(mergeCategoryVOs);
			this.mergeSameCategoryCon(mergeCategoryVOs);
			this.removeConWithEmptyEle();
			this.mergeParentToChildCategoryCon(mergeCategoryVOs);
			this.removeConWithEmptyEle();
		}
    }
	
	/**
	 * 把的单独子条件，不是多对象的 fieldGroupId设为null，只有具有null fieldGroupId的组才可以merge。
	 * 但若是固定界面，即使有多个子条件，因为fieldGroupId本来为null，所以也可以merge。
	 * 这里借用了fieldGroupId作为标记是否可以merge， 否则可以新建一个属性。
	 * @param mergeCategoryVOs
	 */
	private void emptyFieldGroupId(List<ModelCategoryVO> mergeCategoryVOs) {
		for(RuleConditionVO con : this.ruleConditions) {
			ModelCategoryVO category = getMergeCategory(mergeCategoryVOs, con.getModelCategoryName());
			if(category != null 
			  && !BlankUtil.isBlank(con.getConditionElements()) 
			  && con.getConditionElements().size() == 1 
			  && con.getFieldGroupId() != null) {
				con.setFieldGroupId(null);
			} else if(category != null && (category.getIsMultiFact() == null || category.getIsMultiFact() == false)) {
				con.setFieldGroupId(null);
			}
		}
	}
	
	private void removeConWithEmptyEle() {
		Iterator<RuleConditionVO> conIt = ruleConditions.iterator();
		while(conIt.hasNext()) {
			RuleConditionVO con = conIt.next();
			if(BlankUtil.isBlank(con.getConditionElements()) && BlankUtil.isBlank(con.getModifier())) {
				conIt.remove();
			}
		}
	}
	
	/**
	 * 把父category的条件， 移动到子category条件中， 只能移动并且关系的元素, 因为在drl文件的判断中，会先判断类别名称。
	 */
	private void mergeParentToChildCategoryCon(List<ModelCategoryVO> mergeCategoryVOs) {
		if(!BlankUtil.isBlank(ruleConditions)) {
			Iterator<RuleConditionVO> outConIt = ruleConditions.iterator();
			int curPos = -1;
			Map<Long, RuleConditionVO> removeParentMap = new HashMap<Long, RuleConditionVO>();
			while(outConIt.hasNext()) {
				RuleConditionVO outCon = outConIt.next();
				RuleConditionElementVO firstEle = getFirstConEle(outCon);
				RuleConditionElementVO lastEle = getLastConEle(outCon);
				curPos ++;
				if( firstEle == null 
					|| outCon.getFieldGroupId() != null
				    || (lastEle.getRightBracketNum() != null && lastEle.getRightBracketNum() > 0)) {
					continue;
				}
				
				for(int i= curPos + 1; i < ruleConditions.size();i ++) {
					RuleConditionVO innerCon = ruleConditions.get(i);
					
					RuleConditionElementVO innerFirstEle = getFirstConEle(innerCon);
					RuleConditionElementVO innerLastEle = getFirstConEle(innerCon);
					if(innerFirstEle == null) {
						continue;
					}
					
					//下一个condition的and相同， 且同一层扩号
					if( (innerFirstEle.getIsAnd() == null || innerFirstEle.getIsAnd() == true)
						&& !innerFirstEle.hasLeftBracket() ){
						//在分类相同时， 把内部的condition merge到外面的condition中， 若不同， 内部循环下一个
						if(outCon.getModelCategoryName().equals(innerCon.getModelCategoryParentName())) {
							cloneParentConToChild(outCon, innerCon);
							removeParentMap.put(outCon.getId(), outCon);
						} else if(innerCon.getModelCategoryName().equals(outCon.getModelCategoryParentName())) {
							removeParentMap.put(innerCon.getId(), innerCon);
							cloneParentConToChild(innerCon, outCon);
						}
						if(innerLastEle.hasRightBracket()) {
							break;
						}
					} else {
						break;
					}
				}
			}
			removeParentCatCon(removeParentMap);
		}
	}
	
	/**
	 * 把移动过的父category con移除
	 * @param removeParentMap
	 */
	private void removeParentCatCon(Map<Long, RuleConditionVO> removeParentMap) {
		Iterator<RuleConditionVO> outConIt = ruleConditions.iterator();
		int pos = 0;
		while(outConIt.hasNext()) {
			RuleConditionVO conVo = outConIt.next();
			if(removeParentMap.containsKey(conVo.getId())) {
				RuleConditionElementVO firstEle = getFirstConEle(conVo);
				RuleConditionElementVO lastEle = getLastConEle(conVo);
				if(firstEle.hasLeftBracket() && ((pos + 1) < ruleConditions.size())) {
					RuleConditionVO innerCon = ruleConditions.get(pos + 1);
					RuleConditionElementVO innerFirstEle = getFirstConEle(innerCon);
					innerFirstEle.setLeftBracketNum(firstEle.getLeftBracketNum());
				}
				if(lastEle.hasRightBracket() && ((pos - 1) >= 0)) {
					RuleConditionVO innerCon = ruleConditions.get(pos - 1);
					RuleConditionElementVO innerLastEle = getLastConEle(innerCon);
					innerLastEle.setRightBracketNum(lastEle.getRightBracketNum());
				}
				outConIt.remove();
				conVo.setModifier(null);
			} else {
				pos ++;
			}
		}
	}
	
	private void cloneParentConToChild(RuleConditionVO parent, RuleConditionVO child) {
		if(!BlankUtil.isBlank(parent.getConditionElements())) {
			for(RuleConditionElementVO ele : parent.getConditionElements()) {
				RuleConditionElementVO cloneEle = new RuleConditionElementVO();
				BeanUtils.copyProperties(cloneEle, ele);
				//确保左右括号不copy
				cloneEle.setLeftBracketNum(null);
				cloneEle.setRightBracketNum(null);
				child.getConditionElements().add(cloneEle);
			}
			if(!BlankUtil.isBlank(parent.getModifier()) && BlankUtil.isBlank(child.getModifier()) ) {
				child.setModifier(parent.getModifier());				
			}
		}
	}
	/**
	 * 整合同级符号(都是"与", 或者都是"或"), 并且在同一括号, 中的条件
	 */
	private void mergeSameCategoryCon(List<ModelCategoryVO> mergeCategoryVOs) {
		Iterator<RuleConditionVO> outConIt = ruleConditions.iterator();
		int curPos = -1;
		while(outConIt.hasNext()) {
			RuleConditionVO outCon = outConIt.next();
			RuleConditionElementVO firstEle = getFirstConEle(outCon);
			RuleConditionElementVO lastEle = getLastConEle(outCon);
			curPos ++;
			if( firstEle == null 
			    || outCon.getFieldGroupId() != null
			    || (lastEle.getRightBracketNum() != null && lastEle.getRightBracketNum() > 0)) {
				continue;
			}
			
			Boolean isAnd = firstEle.getIsAnd() == null ? true : firstEle.getIsAnd();
			for(int i= curPos + 1; i < ruleConditions.size();i ++) {
				RuleConditionVO innerCon = ruleConditions.get(i);
				RuleConditionElementVO innerFirstEle = getFirstConEle(innerCon);
				RuleConditionElementVO innerLastEle = getLastConEle(innerCon);
				if(innerCon.getFieldGroupId() != null || innerFirstEle == null) {
					continue;
				}
				if(isMergeNeedBreak(outCon, innerCon, mergeCategoryVOs)) {
					break;
				}
				Boolean innerIsAnd = innerFirstEle.getIsAnd() == null ? true : innerFirstEle.getIsAnd();
	 			//下一个condition的and or 相同， 且同一层扩号
				if( (isAnd == innerIsAnd) 
					&& !innerFirstEle.hasLeftBracket()
					&& !innerLastEle.hasRightBracket()){
					//在分类相同时， 把内部的condition merge到外面的condition中， 若不同， 内部循环下一个
					if(outCon.getModelCategoryName().equals(innerCon.getModelCategoryName())) {
						moveConditionEles(innerCon, outCon);
					}
				} else if((isAnd == innerIsAnd ) 
					&& !innerFirstEle.hasLeftBracket()
					&& innerLastEle.hasRightBracket()
					&& (outCon.getModelCategoryName().equals(innerCon.getModelCategoryName())) ){
					    Integer rightBtNum = innerLastEle.getRightBracketNum();
					    innerLastEle.setRightBracketNum(null);
						moveConditionEles(innerCon, outCon);
						moveRightBracketNum(rightBtNum, curPos, i);
						break;
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * category 中是否包含指定name
	 * @param mergeCategoryVOs
	 * @param categoryName
	 * @return
	 */
	private ModelCategoryVO getMergeCategory(List<ModelCategoryVO> mergeCategoryVOs, String categoryName) {
		if(!BlankUtil.isBlank(mergeCategoryVOs)) {
			for(ModelCategoryVO category : mergeCategoryVOs) {
				if(category.getName().equals(categoryName)) {
					return category;
				} 
			}
		}
		return null;
	}
	
	/**
	 * 判断是否可以融合
	 * @param con
	 * @param mergeCategoryVOs
	 * @return
	 */
	private boolean isMergeNeedBreak(RuleConditionVO outCon, RuleConditionVO innerCon, List<ModelCategoryVO> mergeCategoryVOs) {
		if(outCon.getModelCategoryName().equals(innerCon.getModelCategoryName())) {
			ModelCategoryVO category = getMergeCategory(mergeCategoryVOs, outCon.getModelCategoryName());
			if(category.getIsMultiFact() == null || category.getIsMultiFact() == false) {
				return false;
			}
			for(RuleConditionElementVO innerConEle : innerCon.getConditionElements()) {
				for(RuleConditionElementVO outConEle : outCon.getConditionElements()) {
					if(innerConEle.getModelFieldName().equals(outConEle.getModelFieldName()) 
					 && innerConEle.getOpCode().equals(outConEle.getOpCode())) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private void moveRightBracketNum(Integer bracketNum, int curPos, int rightBtNumPos) {
		rightBtNumPos --;
		while(rightBtNumPos <= curPos) {
			RuleConditionVO innerCon = ruleConditions.get(rightBtNumPos);
			rightBtNumPos --;
			RuleConditionElementVO lastEle = getLastConEle(innerCon);
			if(lastEle == null) {
				continue;
			} else {
				lastEle.setRightBracketNum(bracketNum);
				return;
			}
		}
	}
	
	private void moveConditionEles(RuleConditionVO source, RuleConditionVO dest) {
		dest.getConditionElements().addAll(source.getConditionElements());
		source.getConditionElements().clear();
		if(BlankUtil.isBlank(dest.getModifier()) && !BlankUtil.isBlank(source.getModifier())) {
			dest.setModifier(source.getModifier());
			source.setModifier(null);
		}
	}
	
	private RuleConditionElementVO getFirstConEle(RuleConditionVO con) {
		if(con != null && !BlankUtil.isBlank(con.getConditionElements())) {
			return con.getConditionElements().get(0);
		}
		return null;
	}
	
	private RuleConditionElementVO getLastConEle(RuleConditionVO con) {
		if(con != null && !BlankUtil.isBlank(con.getConditionElements())) {
			return con.getConditionElements().get(con.getConditionElements().size() - 1);
		}
		return null;
	}
	/**
	 * 删除条件分类中指定条件
	 * @param ruleConditions
	 * @param category
	 * @param field
	 */
	public void removeConditionEle(String category, String field) {
		if(!BlankUtil.isBlank(ruleConditions)) {
			Iterator<RuleConditionVO> conditionVoIt = ruleConditions.iterator();
			while(conditionVoIt.hasNext()) {
				RuleConditionVO conditionVo = conditionVoIt.next();
				if(!conditionVo.getModelCategoryName().equals(category)) {
					continue;
				}
				List<RuleConditionElementVO> conditionEleVos = conditionVo.getConditionElements();
				if(conditionEleVos != null) {
					Iterator<RuleConditionElementVO> condEleIt = conditionEleVos.iterator();
					while(condEleIt.hasNext()) {
						RuleConditionElementVO conEleVO = condEleIt.next();
						if(conEleVO.getModelFieldName().equals(field)) {
							condEleIt.remove();
						}
					}
				}
				if(BlankUtil.isBlank(conditionVo.getConditionElements())) {
					conditionVoIt.remove();
				}
			}
		}
	}
	
	public Boolean getIsSerial() {
    	return isSerial;
    }


	public void setIsSerial(Boolean isSerial) {
    	this.isSerial = isSerial;
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

	public List<RuleConditionVO> getRuleConditions() {
		return ruleConditions;
	}

	public void setRuleConditions(List<RuleConditionVO> ruleConditions) {
		this.ruleConditions = ruleConditions;
	}

	public List<RuleActionVO> getRuleActions() {
		return ruleActions;
	}

	public void setRuleActions(List<RuleActionVO> ruleActions) {
		this.ruleActions = ruleActions;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getRuleGroupId() {
		return ruleGroupId;
	}

	public void setRuleGroupId(Integer ruleGroupId) {
		this.ruleGroupId = ruleGroupId;
	}
}
