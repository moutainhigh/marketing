package com.oristartech.rule.core.executor;

import java.util.List;
import java.util.Map;

import com.oristartech.rule.vos.core.vo.RuleConditionVO;

/**
 * 规则匹配前的数据准备接口
 * @author chenjunfei
 *
 */
public interface IRuleExecutorDataService {
	/**
	 * 更新属性时，参数map中，ben属性指定要更新的属性
	 */
	public static final String UPDATE_FIELD_NAME = "updateFieldName" ;
	
//	/**
//	 * 是否要主动加载数据的category
//	 * @param categoryName
//	 * @return
//	 */
//	public boolean isAutoLoadCategory(String categoryName);
	
//	/**
//	 * 分类中是否有动态条件
//	 * @param categoryName
//	 * @return
//	 */
//	public boolean hasDynamicField(String categoryName);
	
//	/**
//	 * 加载category数据
//	 * @param category
//	 * @return
//	 */
//	public Object loadCategoryData(Object category);
	
	/**
	 * 加载category数据
	 * @param factList
	 * @return
	 */
	public void loadAndUpdateFactDatas(List<Object> factList);
	
	/**
	 * 加载和更新需要访问外部系统才可以设置的某些属性，fact中的其他属性会保留。
	 * @param factList
	 */
	public void loadAndUpdateFactFields(List<Object> factList);
	
//	/**
//	 * 获取category中动态加载的数据
//	 * @param categoryName
//	 * @return
//	 */
//	public List<Object> loadDynamicConditionValues(Object category);

//	/**
//	 * 获取所有系统中需要动态加载的条件配置
//	 * @param conditionCategory ModelField 中queryKeyFieldNames 对应的category
//	 * @return
//	 */
//	public Map<String, List<RuleConditionVO>> getAllDynamicConditions(String conditionCategory);
}
