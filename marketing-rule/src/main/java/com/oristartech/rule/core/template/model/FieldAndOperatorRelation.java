package com.oristartech.rule.core.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

import com.oristartech.rule.core.base.model.ModelField;
import com.oristartech.rule.core.base.model.Operator;

/**
 * 属性, 属性组与运算符关系类。设置在定义时，可以允许选择的运算符 , 
 * 不同的属性组下的相同属性可以有不同操作符
 * @author chenjunfei
 * @version 1.0
 * @updated 04-十二月-2013 18:45:10
 */
@Entity
@Table(name="RULE_TPL_FIELD_R_OPERATOR")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class FieldAndOperatorRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	/**
	 * 业务属性
	 */
	@ManyToOne
	@ForeignKey(name="FK_FIELD_AND_OPERATOR_RELATION_MODEL_FIELD")
	@JoinColumn(name="MODEL_FIELD_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private ModelField modelField;
	
	/**
	 * 运算符
	 */
	@ManyToOne
	@ForeignKey(name="FK_FIELD_AND_OPERATOR_RELATION_OPERATOR")
	@JoinColumn(name="OPERATOR_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Operator operator;

	/**
	 * 属性分组, 不同的组下的field可以有不同的操作符号
	 */
	@ManyToOne
	@ForeignKey(name="FK_FIELD_AND_GROUP_AND_OP_RELATION")
	@JoinColumn(name="FIELD_GROUP_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private FieldGroup fieldGroup;
	
	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;
	

	/**
	 * 操作符号和属性关联后,  表单中属性输入控件类型， 如input，select，multiselect等 , 若有值会覆盖ModelField中的 formCtrl
	 */
	@Column(name="FORM_CTRL", length=30)
	private String formCtrl;
	
	/**
	 *  操作符号和属性关联后, 属性值Format, 有些参数需要指定显示格式,例如日期, 可空, 若有值会覆盖ModelField中的 valueFormat
	 */
	@Column(name="VALUE_FORMAT", length=30)
	private String valueFormat;
	
	/**
	 * 操作符号和属性关联后,属性值的验证规则. 若有值会覆盖ModelField中的 validateRule
	 * 结构同validator组件中的, rules方法中的add模式中的参数. 即为json对象.
	 */
	@Column(name="VALIDATE_RULE")
	private String validateRule;
	
	public Integer getId() {
    	return id;
    }

	public void setId(Integer id) {
    	this.id = id;
    }

	public ModelField getModelField() {
    	return modelField;
    }

	public void setModelField(ModelField modelField) {
    	this.modelField = modelField;
    }

	public Operator getOperator() {
    	return operator;
    }

	public void setOperator(Operator operator) {
    	this.operator = operator;
    }

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public FieldGroup getFieldGroup() {
    	return fieldGroup;
    }

	public void setFieldGroup(FieldGroup fieldGroup) {
    	this.fieldGroup = fieldGroup;
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