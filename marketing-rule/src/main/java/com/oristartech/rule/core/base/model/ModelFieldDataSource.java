package com.oristartech.rule.core.base.model;

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

/**
 * 业务属性的备选值列表
 * @author chenjunfei
 * @version 1.0
 * @updated 07-十二月-2013 17:26:39
 */
@Entity
@Table(name = "RULE_BASE_MODEL_FIELD_DATA_SOURCE")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,  region="com-oristartech-rule-basic-model-region")
public class ModelFieldDataSource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 显示值
	 */
	@Column(name = "LABEL", length = 100, nullable = false)
	private String label;
	/**
	 * 值
	 */
	@Column(name = "VALUE", length = 100, nullable = false)
	private String value;
	/**
	 * 顺序号
	 */
	@Column(name = "SEQ_NUM")
	private Integer seqNum;

	/**
	 * 关联的属性
	 */
	@ManyToOne
	@ForeignKey(name = "FK_MODEL_FIELD_DATA_SOURCE_FIELD")
	@JoinColumn(name = "MODEL_FIELD_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private ModelField modelField;

	/**
	 * 联动的属性(即本值选中,则相关属性显示, 否则隐藏)， 最好少用本属性控制。若控制多个联动，用|号隔开多个id
	 */
	@Column(name = "LINKAGE_MODEL_FIELD_ID")
	private String linkageId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSeqNum() {
    	return seqNum;
    }

	public void setSeqNum(Integer seqNum) {
    	this.seqNum = seqNum;
    }

	public ModelField getModelField() {
    	return modelField;
    }

	public void setModelField(ModelField modelField) {
    	this.modelField = modelField;
    }

	public String getLinkageId() {
		return linkageId;
	}

	public void setLinkageId(String linkageId) {
		this.linkageId = linkageId;
	}
}