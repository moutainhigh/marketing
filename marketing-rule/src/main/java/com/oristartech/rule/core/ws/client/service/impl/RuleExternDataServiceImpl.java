package com.oristartech.rule.core.ws.client.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.core.base.dao.IModelFieldDao;
import com.oristartech.rule.core.base.model.ModelField;
import com.oristartech.rule.core.ws.client.service.IRuleExternDataService;
import com.oristartech.rule.core.ws.client.service.IRuleSystemWebServiceInvoker;
import com.oristartech.rule.vos.ws.vo.RuleWSResultVO;

@Component
public class RuleExternDataServiceImpl implements IRuleExternDataService {

	private static final String LABEL_SEPARATOR = ",";
	@Autowired
	private IModelFieldDao ruleModelFieldDao;
	@Autowired
	private IRuleSystemWebServiceInvoker ruleSystemWebServiceInvoker;
	
	public String searchExtDataByFieldId(Integer id, Map<String, Object> params) {
		if(id != null) {
			ModelField field = ruleModelFieldDao.get(id);
			if(!BlankUtil.isBlank(field.getSearchServiceName())) {
				return findFieldData(field,  field.getSearchServiceName(), params);
			}
		} 
		return null;
	}

	public String searchExtDataByName(String categoryName, String fieldName, Map<String, Object> params) {
		if(!BlankUtil.isBlank(categoryName) && !BlankUtil.isBlank(fieldName)) {
			ModelField field = ruleModelFieldDao.getByCatNameAndFieldName(categoryName, fieldName);
			if(!BlankUtil.isBlank(field.getSearchServiceName())) {
				return findFieldData(field,field.getSearchServiceName(), params);
			}
			
		}
		return null;
	}
	
	private String findFieldData(ModelField modelField, String serviceName, Map<String, Object> params) {
		if(modelField != null && modelField.getIsExtern() != null && modelField.getIsExtern()) {
			return findExternData(serviceName, params);
		}
		return null;
	}
	
	public String getExtDataByFieldId(Integer id, Map<String, Object> params) {
		if(id != null) {
			ModelField field = ruleModelFieldDao.get(id);
			if(!BlankUtil.isBlank(field.getModelServiceName())) {
				return findFieldData(field,field.getModelServiceName(),  params);
			}
			
		} 
	    return null;
	}
	
	public String loadExternFieldLabel(String operand, ModelField field, String systemCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Object> paramNeedQuery = new ArrayList<Object>();
		//如果不是数组形式的字符串,就是","号隔开的字符串
		//外部数据只获取第一个值, 其他用等等显示
		if(!operand.trim().startsWith("[")) {
			String []values = operand.split(",");
			paramNeedQuery.add(values[0]);
		} else {
			List<Object> valueList = JsonUtil.jsonArrayToList(operand, Object.class);
			if(valueList.size() > 0) {
				paramNeedQuery.add(valueList.get(0));
			} 
		}

		params.put(field.getName(), paramNeedQuery);
		params.put(BizFactConstants.SYSTEM_CODE, systemCode);
		
		String mapJsonStr = (this.getExtDataByFieldId(field.getId(), params)) ;
		return assembleFieldLabel(field, mapJsonStr);
	}
	
	public String getExtDataByFieldName(String categoryName, String fieldName, Map<String, Object> params) {
		if(!BlankUtil.isBlank(categoryName) && !BlankUtil.isBlank(fieldName)) {
			ModelField field = ruleModelFieldDao.getByCatNameAndFieldName(categoryName, fieldName);
			if(!BlankUtil.isBlank(field.getModelServiceName())) {
				return findFieldData(field,field.getModelServiceName(),  params);
			}
		}
		return null;
	}
	
	public String findExternData(String serviceName, Map<String, Object> params) {
		return ruleSystemWebServiceInvoker.invodeService(serviceName, params);
	}
	
	public RuleWSResultVO findExternDataResultVO(String serviceName, String params) {
		Map<String, Object> paramMap = JsonUtil.jsonToObject(params, Map.class);
	    return findExternDataResultVO(serviceName, paramMap);
	}
	
	public RuleWSResultVO findExternDataResultVO(String serviceName, Map<String, Object> params) {
		return ruleSystemWebServiceInvoker.invodeServiceWithResultVO(serviceName, params);
	}
	
	/**
	 * 组装外部数据条件值对应的显示label
	 * @param field
	 * @param mapJsonStr
	 * @return
	 */
	private String assembleFieldLabel(ModelField field , String mapJsonStr) {
		if(!BlankUtil.isBlank(mapJsonStr)) {
			RuleWSResultVO result = JsonUtil.jsonToObject(mapJsonStr, RuleWSResultVO.class);
			String labelKey = BlankUtil.isBlank(field.getKeyFieldName()) ? field.getLabelFieldName() : field.getName() ;
			List<String> labels = new ArrayList<String>();
			if((result != null && result.isOk() && result.getData() != null) ) {
				if(result.getData() instanceof String  ) {
					String data = (String)result.getData();
					if(data.trim().startsWith("[")) {
						List<Map<String, Object>> datas = (List)JsonUtil.jsonArrayToList(data, Map.class);
						return getLabels(datas, labelKey);
					}
					return (String)result.getData();
				} else {
					//在用某些key值查询数据后, 结果里面的data是数组
					List<Map<String, Object>> datas = (List<Map<String, Object>>) result.getData();
					return getLabels(datas, labelKey);
				}
			}
		}
		return null;
	}
	
	private String getLabels(List<Map<String, Object>> datas, String labelKey) {
		List<String> labels = new ArrayList<String>();
		for(int i=0; i < datas.size(); i++) {
			Map<String, Object> map = datas.get(i);
			labels.add(String.valueOf(map.get(labelKey)));
		}
		return StringUtils.join(labels, LABEL_SEPARATOR);
	}
}
