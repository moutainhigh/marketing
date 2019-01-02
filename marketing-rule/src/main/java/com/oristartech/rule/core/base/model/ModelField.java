package com.oristartech.rule.core.base.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hibernate.annotations.Index;

import com.oristartech.rule.common.util.BlankUtil;

/**
 * 业务属性
 * @author chenjunfei
 * @version 1.0
 * @updated 07-十二月-2013 17:45:22
 */
@Entity
@Table(name="RULE_BASE_MODEL_FIELD")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
public class ModelField {

	private static final String QUERY_NAME_LEFT_BORDER = "[";
	private static final String QUERY_NAME_RIGHT_BORDER = "]";
	private static final String QUERY_NAME_CAT_FIELD_SPLITER = ":";
	private static final String QUERY_NAME_SPLITER = ",";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	/**
	 * 若是顶级复合属性，可以不用英文名称
	 */
	@Index(name="INDEX_MODEL_FIELD_NAME")
	@Column(length = 50, nullable = false, name="NAME")
	private String name;
	/**
	 * 中文名称
	 */
	@Column(length = 50, name="CN_NAME")
	private String cnName;
	/**
	 * 属性java类型，包含包类名。如java.util.Date
	 */
	@Column(length = 100, name="TYPE")
	private String type;
	/**
	 * 业务分类
	 */
	@ManyToOne
	@ForeignKey(name = "FK_MODEL_FIELD_CATEGORY")
	@JoinColumn(name = "MODEL_CATEGORY_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private ModelCategory modelCategory;

	/**
	 * 默认值
	 */
	@Column(name = "DEFAULT_VALUE")
	private String defaultValue;

	/**
	 * 是否外部数据源
	 */
	@Column(name = "IS_EXTERN")
	private Boolean isExtern;

	/**
	 * 是否删除
	 */
	@Column(name = "IS_DELETE", nullable = false)
	private Boolean isDelete;
	/**
	 * true则调用业务系统主动加载属性数据
	 */
	@Column(name = "IS_LOAD_AUTO")
	private Boolean isLoadAuto;

	/**
	 * 是否在动态条件中
	 */
	@Column(name = "IS_IN_DYNAMIC_CONDITION")
	private Boolean isInDynamicCondition;
	
	/**
	 * 外部数据源界面显示方法
	 */
	@Column(name="EXTERN_PAGE_METHOD", length=50)
	private String externPageMethod;
	
	/**
	 * 表单输入控件类型， 如input，select，multiselect等
	 */
	@Column(name="FORM_CTRL", length=30)
	private String formCtrl;
	
	/**
	 * 测算页面的ctrl, 若空,会使用formCtrl指定的值
	 */
	@Column(name="FORM_CTRL_IN_TEST", length=30)
	private String formCtrlInTest;
	
	/**
	 * 值Format, 有些参数需要指定显示格式,例如日期, 可空
	 */
	@Column(name="VALUE_FORMAT", length=30)
	private String valueFormat;
	
	/**
	 * 测算值Format, 若空,用会 valueFormat
	 */
	@Column(name="VALUE_FORMAT_IN_TEST", length=30)
	private String valueFormatInTest;
	
	/**
	 * 归属业务系统, 因为某些分类是不归属也任何业务系统, 但其某些属性又是要查业务系统的.所以要关联.
	 */
	@ManyToOne
	@ForeignKey(name = "FK_MODEL_FIELD_BUSINESS_SYSTEM")
	@JoinColumn(name = "BUSINESS_SYSTEM_ID")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private BusinessSystem businessSystem;
	/**
	 * 外部模型数据查询ServiceName
	 */
	@Column(name="SEARCH_SERVICE_NAME", length=50)
	private String searchServiceName;
	
	/**
	 * 外部模型对象获取serviceName
	 */
	@Column(name="MODEL_SERVICE_NAME", length=50)
	private String modelServiceName;
	
	/**
	 * 加载动态属性或自动加载的service方法
	 */
	@Column(name="LOAD_SERVICE_NAME", length=50)
	private String loadServiceName;
	
	
	/**
	 * 主动查询本属性时, 可能需要同时提供相关查询条件属性值到其他业务系统进行查询. 
	 * 这里的name, 可以多组,每组用[]括起来, 并用","隔开, 每组的形式是[categoryName:fieldName], 注意现在可以是在其他cateogry, 但所有的fieldName必须限同一个category的field
	 */
	@Column(name="QUERY_KEY_FIELD_NAMES", length=100)
	private String queryKeyFieldNames;
	
	/**
	 * 显示label的field name, 有些属性例如影院编码, 不会直接显示, 
	 * 而是显示影院名称. 这里的field name也是业务属性名称
	 */
	@Column(name="LABEL_FIELD_NAME", length=50)
	private String labelFieldName;
	
	/**
	 * 保存的关联属性名称,有些属性例如商品名称, 显示的是名称, 但保存的是店内码
	 */
	@Column(name="KEY_FIELD_NAME", length=50)
	private String keyFieldName;
	
	/**
	 * 为生成sql查询的名称
	 */
	@Column(name="SQL_NAME", length=50)
	private String sqlName;
	
	/**
	 * 备注(对属性的解释)
	 */
	@Column(name="REMARK")
	private String remark;
	
	/**
	 * 结构同validator组件中的, rules方法中的add模式中的参数. 即为json对象.
	 */
	@Column(name="VALIDATE_RULE")
	private String validateRule;
	
	@OneToMany(mappedBy="modelField", fetch=FetchType.LAZY)
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com-oristartech-rule-basic-model-region")
	private List<ModelFieldDataSource> modelFieldDataSources;
	
	/**
	 * 在测试页面可输入的值数量. 方式同Operator的opNum, 若是空, 则是1.
	 */
	@Column(name="VALUE_NUM_IN_TEST")
	private Integer valueNumInTest;
	
	/**
	 * 是否会在测算页面出现
	 */
	@Column(name="IS_DISPLAY_IN_TEST")
	private Boolean isDisplayInTest;
	
	/**
	 * 是否不导出到业务VO中给业务系统使用. 是为了避免导出太多没比必要的属性.例如很多名称类属性. 业务系统不需要设置这些名称,只需要设置key.
	 * 若是null,或false则导出.
	 */
	@Column(name="IS_NOT_EXPORT_TO_VO")
	private Boolean isNotExportToVO;
	
	/**
	 * 某个field的别名，主要为了便于在界面上添加属性。因为某些属性客户端传递过来时只有一个属性，但是界面可能添加了同一属性的几个概念的设置。
	 * 例如放映时间，可以映射为放映有效期，放映时段等等。
	 * 在生成规则条件时，只会用真正的field比较。
	 */
	@Column(name="ALIAS_FOR_FIELD")
	private String aliasForField;
	
	/**
	 * 组装
	 * @param categoryName
	 * @return
	 */
	public static String composeQueryCategoryPrefix(String categoryName) {
		if(!BlankUtil.isBlank(categoryName)) {
			return QUERY_NAME_LEFT_BORDER + categoryName + QUERY_NAME_CAT_FIELD_SPLITER;
		}
		return null;
	}
	
	/**
	 * 从queryKeyFieldNames中获取到所有的fieldname
	 * @return
	 */
	public static List<String> splitQueryFieldNames(String queryKeyFieldNames) {
		if(!BlankUtil.isBlank(queryKeyFieldNames)) {
			List<String> fieldNames = new ArrayList<String>();
			String[] pairs = queryKeyFieldNames.replaceAll("\\" + QUERY_NAME_LEFT_BORDER, "")
			                                        .replaceAll("\\" + QUERY_NAME_RIGHT_BORDER, "")
			                                        .split(QUERY_NAME_SPLITER);
			if(!BlankUtil.isBlank(pairs)) {
				for(String pair : pairs) {
					String[] catAndField = pair.split(QUERY_NAME_CAT_FIELD_SPLITER) ;
					if(catAndField.length > 1) {
						fieldNames.add(catAndField[1]);
					}
				}
			}
			return fieldNames;
		}
		return null;
	}
	
	public String getAliasForField() {
    	return aliasForField;
    }

	public void setAliasForField(String aliasForField) {
    	this.aliasForField = aliasForField;
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

	public String getLoadServiceName() {
    	return loadServiceName;
    }

	public void setLoadServiceName(String loadServiceName) {
    	this.loadServiceName = loadServiceName;
    }

	public Boolean getIsInDynamicCondition() {
    	return isInDynamicCondition;
    }

	public void setIsInDynamicCondition(Boolean isInDynamicCondition) {
    	this.isInDynamicCondition = isInDynamicCondition;
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

	public ModelCategory getModelCategory() {
		return modelCategory;
	}

	public void setModelCategory(ModelCategory modelCategory) {
		this.modelCategory = modelCategory;
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

	public BusinessSystem getBusinessSystem() {
    	return businessSystem;
    }

	public void setBusinessSystem(BusinessSystem businessSystem) {
    	this.businessSystem = businessSystem;
    }

	public String getSearchServiceName() {
    	return searchServiceName;
    }

	public void setSearchServiceName(String searchServiceName) {
    	this.searchServiceName = searchServiceName;
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

	public String getModelServiceName() {
    	return modelServiceName;
    }

	public void setModelServiceName(String modelServiceName) {
    	this.modelServiceName = modelServiceName;
    }

	public String getKeyFieldName() {
    	return keyFieldName;
    }

	public void setKeyFieldName(String keyFieldName) {
    	this.keyFieldName = keyFieldName;
    }

	public List<ModelFieldDataSource> getModelFieldDataSources() {
		return modelFieldDataSources;
	}

	public void setModelFieldDataSources(List<ModelFieldDataSource> modelFieldDataSources) {
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