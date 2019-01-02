package com.oristartech.rule.vos.template.vo;

import java.io.Serializable;

import com.oristartech.rule.vos.base.vo.OperatorVO;

public class FieldAndOperatorRelationVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1198907722461419430L;

	private Integer id;
	
	/**
	 * 业务属性
	 */
	private Integer modelFieldId;
	
	/**
	 * 运算符
	 */
	private OperatorVO operator;
	
	/**
	 * 属性分组, 不同的组下的field可以有不同的操作符号
	 */
	private Integer fieldGroupId;
	
	/**
	 * 顺序号
	 */
	private Integer seqNum;
	
	/**
	 * 操作符号和属性关联后,  表单中属性输入控件类型， 如input，select，multiselect等 , 若有值会覆盖ModelField中的 formCtrl
	 */
	private String formCtrl;
	
	/**
	 *  操作符号和属性关联后, 属性值Format, 有些参数需要指定显示格式,例如日期, 可空, 若有值会覆盖ModelField中的 valueFormat
	 */
	private String valueFormat;
	
	/**
	 * 操作符号和属性关联后,属性值的验证规则. 若有值会覆盖ModelField中的 validateRule
	 * 结构同validator组件中的, rules方法中的add模式中的参数. 即为json对象.
	 */
	private String validateRule;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getModelFieldId() {
		return modelFieldId;
	}

	public void setModelFieldId(Integer modelFieldId) {
		this.modelFieldId = modelFieldId;
	}

	public OperatorVO getOperator() {
		return operator;
	}

	public void setOperator(OperatorVO operator) {
		this.operator = operator;
	}

	public Integer getFieldGroupId() {
		return fieldGroupId;
	}

	public void setFieldGroupId(Integer fieldGroupId) {
		this.fieldGroupId = fieldGroupId;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getFormCtrl() {
    	return formCtrl;
    }

	public void setFormCtrl(String formCtrl) {
    	this.formCtrl = formCtrl;
    }

	public String getValueFormat() {
    	return valueFormat;
    }

	public void setValueFormat(String valueFormat) {
    	this.valueFormat = valueFormat;
    }

	public String getValidateRule() {
    	return validateRule;
    }

	public void setValidateRule(String validateRule) {
    	this.validateRule = validateRule;
    }
}
