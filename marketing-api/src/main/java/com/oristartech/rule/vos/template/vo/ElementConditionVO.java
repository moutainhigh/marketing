package com.oristartech.rule.vos.template.vo;

import java.io.Serializable;
import java.util.List;

public class ElementConditionVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7855766499926924641L;

	private List<GroupElementTypeVO> fieldGroupTypes;
	private RuleElementTemplateVO templateVO;
	
	public List<GroupElementTypeVO> getFieldGroupTypes() {
		return fieldGroupTypes;
	}
	public void setFieldGroupTypes(List<GroupElementTypeVO> fieldGroupTypes) {
		this.fieldGroupTypes = fieldGroupTypes;
	}
	public RuleElementTemplateVO getTemplateVO() {
		return templateVO;
	}
	public void setTemplateVO(RuleElementTemplateVO templateVO) {
		this.templateVO = templateVO;
	}
}
