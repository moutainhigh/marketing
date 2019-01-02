package com.oristartech.rule.vos.core.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.HashCodeUtil;
import com.oristartech.rule.vos.base.enums.FunctionResultType;
import com.oristartech.rule.vos.base.vo.RuleActionFunctionParameterVO;


public class RuleActionVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3579544719954399035L;
	private Long id;
	/**
	 * 所属规则块
	 */
	private Long ruleSectionId;

	/**
	 * 顺序号
	 */
	private Integer seqNum;
	

	/**
	 * 与或关系，true是And，false是or
	 */
	private Boolean isAnd;
	
	//-----action fnction 信息
	/**
	 * actionfunction id
	 */
	private Integer actionFnId;
	
	/**
	 * action function 的唯一名称
	 */
	private String actionFnUniqueName;
	
	/**
	 * 函数包类名 
	 */
	private String clzName;
	
	/**
	 * 函数名
	 */
	private String clsMethod;
	
	/**
	 * 函数中文名称
	 */
	private String cnName;
	
	/**
	 * 业务方法分类
	 */
	private Integer fnCategoryId;
	
	private String fnCategoryName;
	
	/**
	 * 方法效果. 例如添加商品, 修改商品, 客户端可以根据本属性直接调用相关方法(必须是符合java命名的英文名)
	 */
	private String fnEffect;

	/**
	 * 对应的functionGroup 的id, 定义时动态产生的动作用到。 可空, 为空表名不是动态添加的动作

	 */
	private Integer funcGroupId;
	
	/**
	 * 若是由引擎执行,在一次匹配过程中的同一条规则里是否应该做多次.
	 */
	private Boolean fnIsExeMultiTime;
	
	/**
	 * 方法产生的结果类型。若方法需要返回map结果，需要设置本值
	 */
	private FunctionResultType fnResultType;
	
	//--end 
	
	/**
	 * 包含参数及值
	 */
	private List<RuleActionParameterVO> parameters ;
	
	/**
	 * 因为function 的 config参数没有在页面存在的, 因而没有保存在action的参数中, 
	 * 需要从function提取出来, 方便使用
	 * 该参数需要主动设置, 简单加载action是不会初始化本参数的.
	 * 通常只有在调用执行类方法动作时用到.其他情况不用初始本参数.
	 */
	@JsonIgnore
	private List<RuleActionFunctionParameterVO> fnParameters;
	
	/**
	 * 把参数转为map 方便访问. 只在java端使用, 不会输出到json, 否则和parameters重复返回太多字符串信息
	 * js端可以自己封装为map访问。
	 */
	@JsonIgnore
	private Map<String, String> parameterMap ;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		RuleActionVO other = (RuleActionVO)obj;
		return new EqualsBuilder().append(this.id, other.id).isEquals(); 
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(HashCodeUtil.initialNonZeroOddNumber, HashCodeUtil.multiplierNonZeroOddNumber)
		                        .append(id).toHashCode();
	}
	
	/**
	 * @return the fnResultType
	 */
	public FunctionResultType getFnResultType() {
		return fnResultType;
	}

	/**
	 * @param fnResultType the fnResultType to set
	 */
	public void setFnResultType(FunctionResultType fnResultType) {
		this.fnResultType = fnResultType;
	}

	public Boolean getFnIsExeMultiTime() {
    	return fnIsExeMultiTime;
    }

	public void setFnIsExeMultiTime(Boolean fnIsExeMultiTime) {
    	this.fnIsExeMultiTime = fnIsExeMultiTime;
    }

	public List<RuleActionFunctionParameterVO> getFnParameters() {
    	return fnParameters;
    }

	public void setFnParameters(List<RuleActionFunctionParameterVO> fnParameters) {
    	this.fnParameters = fnParameters;
    }

	public String getCnName() {
    	return cnName;
    }

	public void setCnName(String cnName) {
    	this.cnName = cnName;
    }
	
	public Integer getActionFnId() {
    	return actionFnId;
    }


	public void setActionFnId(Integer actionFnId) {
    	this.actionFnId = actionFnId;
    }


	public String getActionFnUniqueName() {
    	return actionFnUniqueName;
    }


	public void setActionFnUniqueName(String actionFnUniqueName) {
    	this.actionFnUniqueName = actionFnUniqueName;
    }


	public Long getId() {
    	return id;
    }

	public void setId(Long id) {
    	this.id = id;
    }


	public Long getRuleSectionId() {
    	return ruleSectionId;
    }

	public void setRuleSectionId(Long ruleSectionId) {
    	this.ruleSectionId = ruleSectionId;
    }

	public String getClzName() {
		return clzName;
	}

	public void setClzName(String clzName) {
		this.clzName = clzName;
	}

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public Boolean getIsAnd() {
    	return isAnd;
    }

	public void setIsAnd(Boolean isAnd) {
    	this.isAnd = isAnd;
    }

	public Integer getFuncGroupId() {
    	return funcGroupId;
    }

	public void setFuncGroupId(Integer funcGroupId) {
    	this.funcGroupId = funcGroupId;
    }

	public String getClsMethod() {
    	return clsMethod;
    }

	public void setClsMethod(String clsMethod) {
    	this.clsMethod = clsMethod;
    }

	public Integer getFnCategoryId() {
    	return fnCategoryId;
    }

	public void setFnCategoryId(Integer fnCategoryId) {
    	this.fnCategoryId = fnCategoryId;
    }

	public String getFnCategoryName() {
    	return fnCategoryName;
    }

	public void setFnCategoryName(String fnCategoryName) {
    	this.fnCategoryName = fnCategoryName;
    }

	public String getFnEffect() {
    	return fnEffect;
    }

	public void setFnEffect(String fnEffect) {
    	this.fnEffect = fnEffect;
    }

	public List<RuleActionParameterVO> getParameters() {
    	return parameters;
    }

	public void setParameters(List<RuleActionParameterVO> parameters) {
    	this.parameters = parameters;
    }

	public Map<String, Object> getAllParameterMapObjWithConfig() {
		return (Map) getAllParameterMapWithConfig();
	}
	/**
	 * 获取所有参数, 即同时包含lazy或file, 及config参数
	 * @return
	 */
	public Map<String, String> getAllParameterMapWithConfig() {
		Map<String, String> params = new HashMap<String, String>();
		if(!BlankUtil.isBlank(this.parameters)){
			for(RuleActionParameterVO param : this.parameters) {
				params.put(param.getFnParameterName(), param.getValue());
			}
		}
		if(!BlankUtil.isBlank(this.fnParameters)) {
			for(RuleActionFunctionParameterVO fnParam : this.fnParameters) {
				params.put(fnParam.getName(), fnParam.getDefaultValue());
			}
		}
		return params;
	}
	
	/**
	 * 获取所有参数, 即同时包含lazy或file, 但不包含config参数
	 * @return
	 */
	public Map<String, String> getAllParameterMap() {
		if(!BlankUtil.isBlank(this.parameters)){
			Map<String, String> params = new HashMap<String, String>();
			for(RuleActionParameterVO param : this.parameters) {
				params.put(param.getFnParameterName(), param.getValue());
			}
			return params;
		}
		return null;
	}
	
	/**
	 * 把参数按 Map<String, Object>返回
	 * @return
	 */
	public Map<String, Object> getParameterMapObj() {
		return (Map)this.getParameterMap();
	}
	
	/**
	 * 获取参数, 但不包含lazy或file, 及config参数
	 * @return
	 */
	public Map<String, String> getParameterMap() {
		if(!BlankUtil.isBlank(this.parameterMap)) {
			return this.parameterMap;
		}
		if(!BlankUtil.isBlank(this.parameters)){
			Map<String, String> params = new HashMap<String, String>();
			for(RuleActionParameterVO param : this.parameters) {
				if((param.getIsFile() != null && param.getIsFile() ) || (param.getIsLazy() != null && param.getIsLazy())
					|| (param.getIsConfig() != null && param.getIsConfig())) {
					continue;
				}
				params.put(param.getFnParameterName(), param.getValue());
			}
			parameterMap = params;
		}

    	return parameterMap;
    }
	
	public void setParameterMap(Map<String, String> parameterMap) {
    	this.parameterMap = parameterMap;
    }
}
