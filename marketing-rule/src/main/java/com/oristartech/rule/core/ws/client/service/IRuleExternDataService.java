package com.oristartech.rule.core.ws.client.service;

import java.util.Map;

import com.oristartech.rule.core.base.model.ModelField;
import com.oristartech.rule.vos.ws.vo.RuleWSResultVO;

/**
 * 获取外部数据,例如定义时的外部数据, 或执行时的外部数据.
 * @author chenjunfei
 *
 */
public interface IRuleExternDataService {
	/**
	 * 根据业务属性id查询外部数据
	 * @param id
	 * @param params map 参数
	 * @return json格式结果
	 */
	String searchExtDataByFieldId(Integer id, Map<String, Object> params);
	
	/**
	 * 根据分类名, 属性名,参数查询外部数据
	 * @param categoryName
	 * @param fieldName
	 * @param params map 参数
	 * @return
	 */
	String searchExtDataByName(String categoryName, String fieldName, Map<String, Object> params);
	
	/**
	 * 获取指定key的数据
	 * @param params map 参数
	 * @return
	 */
	String getExtDataByFieldId(Integer id, Map<String, Object> params);
	
	/**
	 * 加载modelfield对应的label
	 * @param operand
	 * @param field
	 * @return
	 */
	String loadExternFieldLabel(String operand, ModelField field, String systemCode);
	/**
	 * 获取指定key的数据
	 * @param id
	 * @param params map 参数
	 * @return
	 */
	String getExtDataByFieldName(String categoryName, String fieldName, Map<String, Object> params);
	
	/**
	 * 根据serviceName 获取数据 返回结果json字符串
	 * @param serviceName
	 * @param params
	 * @return
	 */
	String findExternData(String serviceName, Map<String, Object> params);
	
	/**
	 * 根据serviceName 获取数据, 返回resultVO结果
	 * @param serviceName
	 * @param params
	 * @return
	 */
	RuleWSResultVO findExternDataResultVO(String serviceName, Map<String, Object> params);
	
	/**
	 * 根据serviceName 获取数据, 返回resultVO结果, 两个参数都是String， 方便设置缓存， 在需要缓存的地方，可以调用本方法
	 * @param serviceName
	 * @param params 
	 * @return
	 */
	RuleWSResultVO findExternDataResultVO(String serviceName, String params);
}
