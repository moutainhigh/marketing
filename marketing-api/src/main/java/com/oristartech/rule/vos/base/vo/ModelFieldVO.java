package com.oristartech.rule.vos.base.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.oristartech.rule.vos.template.vo.FieldAndOperatorRelationVO;


/**
 * 业务属性
 * @author chenjunfei
 * @version 1.0
 * @updated 07-十二月-2013 17:45:22
 */
public class ModelFieldVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -324327399432073155L;
	private Integer id;
	/**
	 * 英文名称
	 */
	private String name;
	/**
	 * 中文名称
	 */
	private String cnName;
	/**
	 * 属性java类型，包含包类名。如java.util.Date
	 */
	private String type;
	/**
	 * 业务分类id
	 */
	private Integer modelCategoryId;
	
	/**
	 * 业务分类name
	 */
	private String modelCategoryName;
	
	/**
	 * 父业务分类name
	 */
	private String modelCategoryParentName;

	/**
	 * 一次匹配是否多个fact, 用于测试页面是否可以添加多个category
	 */
	private Boolean isMultiFactCategory;

	/**
	 * 默认值
	 */
	private String defaultValue;

	/**
	 * 是否外部数据源
	 */
	private Boolean isExtern;

	/**
	 * 是否删除
	 */
	private Boolean isDelete;
	/**
	 * true则调用业务系统主动加载属性数据
	 */
	private Boolean isLoadAuto;

	/**
	 * 是否在动态条件中
	 */
	private Boolean isInDynamicCondition;
	
	/**
	 * 加载动态属性或自动加载的service方法
	 */
	private String loadServiceName;
	
	private String remark;
	/**
	 * 外部数据源界面显示方法
	 */
	private String externPageMethod;
	
	/**
	 * 表单输入控件类型， 如input，select，multiselect等
	 */
	private String formCtrl;
	
	/**
	 * 值Format, 有些参数需要指定显示格式,例如日期, 可空
	 */
	private String valueFormat;
	
	/**
	 * 归属业务系统, 因为某些分类是不归属也任何业务系统, 但其某些属性又是要查业务系统的.所以要关联.
	 */
	private Integer businessSystemId;
	
	/**
	 * 业务系统名称
	 */
	private String businessSystemName;
	
	/**
	 * 外部模型数据查询ServiceName
	 */
	private String searchServiceName;
	
	/**
	 * 外部模型对象获取serviceName
	 */
	private String modelServiceName;
	
	/**
	 * 主动查询本属性时, 可能需要同时提供相关查询条件属性值到其他业务系统进行查询. 
	 * 这里的name, 可以多组,每组用[]括起来, 并用","隔开, 每组的形式是[categoryName:fieldName], 注意现在可以是在其他cateogry, 但所有的name必须限同一个category的field
	 */
	private String queryKeyFieldNames;
	
	/**
	 * 显示label的field name, 有些属性例如影院编码, 不会直接显示, 
	 * 而是显示影院名称. 这里的field name也是业务属性名称
	 */
	private String labelFieldName;
	
	/**
	 * 结构同validator组件中的, rules方法中的add模式中的参数. 即为json对象.
	 */
	private String validateRule;
	
	/**
	 * 为生成sql查询的名称
	 */
	private String sqlName;
	

	/**
	 * 在测试页面可输入的值数量. 方式同Operator的opNum, 若是空, 则是1.
	 */
	private Integer valueNumInTest;
	
	/**
	 * 测算页面的ctrl, 若空,会使用formCtrl指定的值
	 */
	private String formCtrlInTest;
	
	/**
	 * 测算值Format, 若空,用会 valueFormat
	 */
	private String valueFormatInTest;
	
	/**
	 * 是否会在测算页面出现
	 */
	private Boolean isDisplayInTest;
	
	/**
	 * 是否不导出到业务VO中给业务系统使用. 是为了避免导出太多没比必要的属性.例如很多名称类属性. 业务系统不需要设置这些名称,只需要设置key.
	 */
	private Boolean isNotExportToVO;
	
	/**
	 * 某个field的别名，主要为了便于在界面上添加属性。因为某些属性客户端传递过来时只有一个属性，但是界面可能添加了同一属性的几个概念的设置。
	 * 例如放映时间，可以映射为放映有效期，放映时段等等。
	 * 在生成规则条件时，只会用真正的field比较。
	 */
	private String aliasForField;
	
	/**
	 * 包含允许的操作符号
	 */
	private List<FieldAndOperatorRelationVO> fieldOperators = new ArrayList<FieldAndOperatorRelationVO>();
	
	/**
	 * 可选数据源
	 */
	private List<ModelFieldDataSourceVO> modelFieldDataSources = new ArrayList<ModelFieldDataSourceVO> ();
	
	public String getAliasForField() {
    	return aliasForField;
    }

	public void setAliasForField(String aliasForField) {
    	this.aliasForField = aliasForField;
    }

	public String getModelCategoryParentName() {
    	return modelCategoryParentName;
    }

	public void setModelCategoryParentName(String modelCategoryParentName) {
    	this.modelCategoryParentName = modelCategoryParentName;
    }

	public Boolean getIsNotExportToVO() {
    	return isNotExportToVO;
    }

	public void setIsNotExportToVO(Boolean isNotExportToVO) {
    	this.isNotExportToVO = isNotExportToVO;
    }

	public Boolean getIsDisplayInTest() {
    	return isDisplayInTest;
    }

	public void setIsDisplayInTest(Boolean isDisplayInTest) {
    	this.isDisplayInTest = isDisplayInTest;
    }

	public String getFormCtrlInTest() {
    	return formCtrlInTest;
    }

	public void setFormCtrlInTest(String formCtrlInTest) {
    	this.formCtrlInTest = formCtrlInTest;
    }

	public String getValueFormatInTest() {
    	return valueFormatInTest;
    }

	public void setValueFormatInTest(String valueFormatInTest) {
    	this.valueFormatInTest = valueFormatInTest;
    }

	public Boolean getIsInDynamicCondition() {
    	return isInDynamicCondition;
    }

	public void setIsInDynamicCondition(Boolean isInDynamicCondition) {
    	this.isInDynamicCondition = isInDynamicCondition;
    }

	public String getLoadServiceName() {
    	return loadServiceName;
    }

	public void setLoadServiceName(String loadServiceName) {
    	this.loadServiceName = loadServiceName;
    }

	public Boolean getIsMultiFactCategory() {
    	return isMultiFactCategory;
    }

	public void setIsMultiFactCategory(Boolean isMultiFactCategory) {
    	this.isMultiFactCategory = isMultiFactCategory;
    }

	public Integer getValueNumInTest() {
    	return valueNumInTest;
    }

	public void setValueNumInTest(Integer valueNumInTest) {
    	this.valueNumInTest = valueNumInTest;
    }

	public String getSqlName() {
    	return sqlName;
    }

	public void setSqlName(String sqlName) {
    	this.sqlName = sqlName;
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

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getIsExtern() {
    	return isExtern;
    }

	public void setIsExtern(Boolean isExtern) {
    	this.isExtern = isExtern;
    }

	public Boolean getIsDelete() {
    	return isDelete;
    }

	public void setIsDelete(Boolean isDelete) {
    	this.isDelete = isDelete;
    }

	public Boolean getIsLoadAuto() {
		return isLoadAuto;
	}

	public void setIsLoadAuto(Boolean isLoadAuto) {
		this.isLoadAuto = isLoadAuto;
	}

	public String getRemark() {
    	return remark;
    }

	public void setRemark(String remark) {
    	this.remark = remark;
    }

	public String getExternPageMethod() {
    	return externPageMethod;
    }

	public void setExternPageMethod(String externPageMethod) {
    	this.externPageMethod = externPageMethod;
    }

	public String getSearchServiceName() {
    	return searchServiceName;
    }

	public void setSearchServiceName(String searchServiceName) {
    	this.searchServiceName = searchServiceName;
    }

	public String getModelServiceName() {
    	return modelServiceName;
    }

	public void setModelServiceName(String modelServiceName) {
    	this.modelServiceName = modelServiceName;
    }

	public String getQueryKeyFieldNames() {
    	return queryKeyFieldNames;
    }

	public void setQueryKeyFieldNames(String queryKeyFieldNames) {
    	this.queryKeyFieldNames = queryKeyFieldNames;
    }

	public String getLabelFieldName() {
    	return labelFieldName;
    }

	public void setLabelFieldName(String labelFieldName) {
    	this.labelFieldName = labelFieldName;
    }

	public Integer getModelCategoryId() {
    	return modelCategoryId;
    }

	public void setModelCategoryId(Integer modelCategoryId) {
    	this.modelCategoryId = modelCategoryId;
    }

	public String getModelCategoryName() {
    	return modelCategoryName;
    }

	public void setModelCategoryName(String modelCategoryName) {
    	this.modelCategoryName = modelCategoryName;
    }

	public Integer getBusinessSystemId() {
    	return businessSystemId;
    }

	public void setBusinessSystemId(Integer businessSystemId) {
    	this.businessSystemId = businessSystemId;
    }

	public String getBusinessSystemName() {
    	return businessSystemName;
    }

	public void setBusinessSystemName(String businessSystemName) {
    	this.businessSystemName = businessSystemName;
    }

	public List<FieldAndOperatorRelationVO> getFieldOperators() {
		return fieldOperators;
	}

	public void setFieldOperators(List<FieldAndOperatorRelationVO> fieldOperators) {
		this.fieldOperators = fieldOperators;
	}

	public List<ModelFieldDataSourceVO> getModelFieldDataSources() {
		return modelFieldDataSources;
	}

	public void setModelFieldDataSources(List<ModelFieldDataSourceVO> modelFieldDataSources) {
		this.modelFieldDataSources = modelFieldDataSources;
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