package com.oristartech.rule.vos.core.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.HashCodeUtil;
import com.oristartech.rule.vos.base.vo.ModelFieldVO;

public class RuleConditionElementVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6534564277772933596L;

	public static String OPERAND_SPLIT = ",";

	private Long id;

	/**
	 * 逻辑符号，OR或AND， true为AND，false为OR 和前面条件的关系
	 */
	private Boolean isAnd;

	/**
	 * 顺序号
	 */
	private Integer seqNum;

	/**
	 * 左括号数
	 */
	private Integer leftBracketNum;
	/**
	 * 右括号数
	 */
	private Integer rightBracketNum;

	/**
	 * 运算符id
	 */
	private Integer opId;

	/**
	 * 运算符
	 */
	private String opCode;

	/**
	 * Condition的判断修饰符号
	 * 
	 */
	private String opConditionModifier;

	/**
	 * 运算符唯一名称
	 */
	private String opUniqueName;

	/**
	 * 运算符号中文名称
	 */
	private String opCnName;

	/**
	 * 操作符需要的操作数量，负数是表示不定, 它的绝对值表示一组数需要输入多少个.
	 */
	private Integer opNum;

	/**
	 * 默认操作数
	 */
	private String opDefaultOperand;

	/**
	 * 若是true, 不会根据opnum 生成操作数, 用于模板中
	 */
	private Boolean isPlainOp;

	/**
	 * 是否是自定义操作符
	 */
	private Boolean isCustomOpCode;

	/**
	 * 操作符同等性标记, 相同类型相同tag的操作符号认为是相同的.
	 */
	private Integer opIdentityTag;

	/**
	 * 操作符的操作数是否不能为null, 若是true,同时判空
	 */
	private Boolean isNotNullOp;

	/**
	 * 操作数。多个操作数，用&ldquo;,&rdquo;号隔开，若某个操作数是复合对象用json表示
	 */
	private String operand;

	/**
	 * 对应的modelField
	 */
	private ModelFieldVO modelField;

	// 方法访问, 把id提出来
	/**
	 * 业务属性id
	 */
	private Integer modelFieldId;

	// 方法访问, 把name提出来
	/**
	 * 业务属性名称
	 */
	private String modelFieldName;

	/**
	 * 归属条件
	 */
	private Long ruleConditionId;

	/**
	 * 操作数显示label, 如"周一", 值是1, 显示"周一"
	 */
	private String operandLabel;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		RuleConditionElementVO other = (RuleConditionElementVO)obj;
		return new EqualsBuilder().append(this.id, other.id).isEquals(); 
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(HashCodeUtil.initialNonZeroOddNumber, HashCodeUtil.multiplierNonZeroOddNumber)
		                        .append(id).toHashCode();
	}

	public static RuleConditionElementVO createConditionEle(String field, String value, String opName) {
		if (!BlankUtil.isBlank(field) && !BlankUtil.isBlank(value) && !BlankUtil.isBlank(opName)) {
			RuleConditionElementVO vo = new RuleConditionElementVO();
			vo.setModelFieldName(field);
			vo.setOperand(value);
			vo.setOpUniqueName(opName);
			return vo;
		}
		return null;
	}

	public String getOpConditionModifier() {
		return opConditionModifier;
	}

	public void setOpConditionModifier(String opConditionModifier) {
		this.opConditionModifier = opConditionModifier;
	}

	public Boolean getIsNotNullOp() {
		return isNotNullOp;
	}

	public void setIsNotNullOp(Boolean isNotNullOp) {
		this.isNotNullOp = isNotNullOp;
	}

	public String getOpDefaultOperand() {
		return opDefaultOperand;
	}

	public void setOpDefaultOperand(String opDefaultOperand) {
		this.opDefaultOperand = opDefaultOperand;
	}

	public Boolean getIsPlainOp() {
		return isPlainOp;
	}

	public void setIsPlainOp(Boolean isPlainOp) {
		this.isPlainOp = isPlainOp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsAnd() {
		return isAnd;
	}

	public void setIsAnd(Boolean isAnd) {
		this.isAnd = isAnd;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public boolean hasLeftBracket() {
		return this.leftBracketNum != null && this.leftBracketNum > 0;
	}

	public Integer getLeftBracketNum() {
		return leftBracketNum;
	}

	public void setLeftBracketNum(Integer leftBracketNum) {
		this.leftBracketNum = leftBracketNum;
	}

	public boolean hasRightBracket() {
		return this.rightBracketNum != null && this.rightBracketNum > 0;
	}

	public Integer getRightBracketNum() {
		return rightBracketNum;
	}

	public void setRightBracketNum(Integer rightBracketNum) {
		this.rightBracketNum = rightBracketNum;
	}

	public Integer getOpId() {
		return opId;
	}

	public void setOpId(Integer opId) {
		this.opId = opId;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public Boolean getIsCustomOpCode() {
		return isCustomOpCode;
	}

	public void setIsCustomOpCode(Boolean isCustomOpCode) {
		this.isCustomOpCode = isCustomOpCode;
	}

	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}

	public Integer getModelFieldId() {
		return modelFieldId;
	}

	public void setModelFieldId(Integer modelFieldId) {
		this.modelFieldId = modelFieldId;
	}

	public String getModelFieldName() {
		return modelFieldName;
	}

	public void setModelFieldName(String modelFieldName) {
		this.modelFieldName = modelFieldName;
	}

	public Long getRuleConditionId() {
		return ruleConditionId;
	}

	public void setRuleConditionId(Long ruleConditionId) {
		this.ruleConditionId = ruleConditionId;
	}

	public String getOperandLabel() {
		return operandLabel;
	}

	public void setOperandLabel(String operandLabel) {
		this.operandLabel = operandLabel;
	}

	public String getOpUniqueName() {
		return opUniqueName;
	}

	public void setOpUniqueName(String opUniqueName) {
		this.opUniqueName = opUniqueName;
	}

	public Integer getOpNum() {
		return opNum;
	}

	public void setOpNum(Integer opNum) {
		this.opNum = opNum;
	}

	public String getOpCnName() {
		return opCnName;
	}

	public void setOpCnName(String opCnName) {
		this.opCnName = opCnName;
	}

	public ModelFieldVO getModelField() {
		return modelField;
	}

	public void setModelField(ModelFieldVO modelField) {
		this.modelField = modelField;
	}

	public Integer getOpIdentityTag() {
		return opIdentityTag;
	}

	public void setOpIdentityTag(Integer opIdentityTag) {
		this.opIdentityTag = opIdentityTag;
	}
}
