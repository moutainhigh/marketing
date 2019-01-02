package com.oristartech.rule.core.base.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

import com.oristartech.rule.vos.base.enums.ActionFunctionType;
import com.oristartech.rule.vos.base.enums.FunctionResultType;

/**
 * 业务方法
 * @author chenjunfei
 * @version 1.0
 * @updated 05-十二月-2013 14:13:16
 */
@Entity
@Table(name="RULE_BASE_ACTION_FUNCTION")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class ActionFunction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;

	/**
	 * 函数名称
	 */
	@Column(name="CLS_METHOD", length = 50, nullable = false)
	private String clsMethod;

	/**
	 * 中文名称
	 */
	@Column(length = 50, name="CN_NAME")
	private String cnName;

	/**
	 * 函数类名, 包含包名称
	 */
	@Column(length = 150, nullable = false, name="CLZ_NAME")
	private String clzName;
	/**
	 * 备注
	 */
	@Column(name="REMARK")
	private String remark;
	/**
	 * 是否删除, true删除，false未删除
	 */
	@Column(name="IS_DELETE")
	private boolean isDelete;
	/**
	 * 方法内部类型
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name="TYPE")
	private ActionFunctionType type;
	
	/**
	 * 业务方法分类
	 */
	@ManyToOne
	@ForeignKey(name = "FK_ACTION_FUNCTION_AND_CATEGORY")
	@JoinColumn(name = "FN_CATEGORY_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private ActionFunctionCategory fnCategory;
	
	/**
	 * 唯一名称
	 */
	@Column(name = "UNIQUE_NAME", length= 150, unique=true)
	private String uniqueName;
	
	/**
	 * 方法效果. 例如添加商品, 修改商品, 客户端可以根据本属性直接调用相关方法(必须是符合java命名的英文名)
	 */
	@Column(name = "FN_EFFECT", length= 50)
	private String fnEffect;
	
	@OneToMany(mappedBy="actionFunction", fetch=FetchType.LAZY)
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
	private List<ActionFunctionParameter> actionFunctionParameters;
	
	/**
	 * 若是由引擎执行,在一次匹配过程中的同一条规则里是否做多次.
	 */
	@Column(name = "IS_EXE_MULTI_TIME")
	private Boolean isExeMultiTime;
	
	/**
	 * 方法产生的结果类型。若方法需要返回map结果，需要设置本值
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "RESULT_TYPE")
	private FunctionResultType resultType;
	
	/**
	 * @return the resultType
	 */
	public FunctionResultType getResultType() {
		return resultType;
	}

	/**
	 * @param resultType the resultType to set
	 */
	public void setResultType(FunctionResultType resultType) {
		this.resultType = resultType;
	}

	public Boolean getIsExeMultiTime() {
    	return isExeMultiTime;
    }

	public void setIsExeMultiTime(Boolean isExeMultiTime) {
    	this.isExeMultiTime = isExeMultiTime;
    }

	public Integer getId() {
    	return id;
    }

	public void setId(Integer id) {
    	this.id = id;
    }

	public String getClsMethod() {
    	return clsMethod;
    }

	public void setClsMethod(String clsMethod) {
    	this.clsMethod = clsMethod;
    }

	public String getClzName() {
    	return clzName;
    }

	public void setClzName(String clzName) {
    	this.clzName = clzName;
    }

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isDelete() {
    	return isDelete;
    }

	public void setDelete(boolean isDelete) {
    	this.isDelete = isDelete;
    }
	
	public ActionFunctionType getType() {
    	return type;
    }

	public void setType(ActionFunctionType type) {
    	this.type = type;
    }

	public ActionFunctionCategory getFnCategory() {
    	return fnCategory;
    }

	public void setFnCategory(ActionFunctionCategory fnCategory) {
    	this.fnCategory = fnCategory;
    }

	public String getUniqueName() {
    	return uniqueName;
    }

	public void setUniqueName(String uniqueName) {
    	this.uniqueName = uniqueName;
    }

	public String getFnEffect() {
    	return fnEffect;
    }

	public void setFnEffect(String fnEffect) {
    	this.fnEffect = fnEffect;
    }

	public List<ActionFunctionParameter> getActionFunctionParameters() {
		return actionFunctionParameters;
	}

	public void setActionFunctionParameters(List<ActionFunctionParameter> actionFunctionParameters) {
		this.actionFunctionParameters = actionFunctionParameters;
	}
}