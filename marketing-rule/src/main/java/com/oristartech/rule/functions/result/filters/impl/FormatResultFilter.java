package com.oristartech.rule.functions.result.filters.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.core.functions.util.FunctionResultUtil;
import com.oristartech.rule.core.result.filters.IResultFilter;
import com.oristartech.rule.core.result.filters.IResultFilterChain;
import com.oristartech.rule.core.result.utils.RuleResultUtil;
import com.oristartech.rule.drools.executor.context.FactsContainer;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 格式化规则结果信息。应该放在filter最后。
 * 例如清空anction结果中的和规则引擎相关的属性，不会把这些属性返回客户端
 * @author chenjunfei
 *
 */
@Component(value="ruleFormatResultFilter")
public class FormatResultFilter implements IResultFilter {
	
	private static Logger log = LoggerFactory.getLogger(FormatResultFilter.class);
	
	public void doFilter(RuleExecuteContext context, IResultFilterChain chain) {
		if(context == null) {
			return;
		}
		
		List<RuleResult> tempResults = context.getRuleResults();
		if(BlankUtil.isBlank(tempResults) ) {
			return;
		}
		if(!context.isTest()) {
			formatResult(tempResults, context);
		}
	    chain.doFilter(context);
	}
	
	private void formatResult(List<RuleResult> tempResults, RuleExecuteContext context) {
		
		Map<String , Object> contextInfo = getRuleContextInfo(context);
		boolean isMapResult = getIsOutputMaps(contextInfo);
			
		for(RuleResult result : tempResults) {
			List<RuleActionResult> anResults = result.getResults();
			if(BlankUtil.isBlank(anResults)) {
				continue;
			}
			
			for(RuleActionResult anResult : anResults) {
				if(!BlankUtil.isBlank(anResult.getFnResults())) {
					
					for(Object fnRet : anResult.getFnResults()) {
						RuleResultUtil.removeEngineRelateField(fnRet);
					}
					convertActionMapResult(result, anResult, isMapResult);
				}
			}
			setMapResult(result, context, isMapResult);
		}
	}
	
	/*
	 * 添加额外参数入结果集，目前只添加cnName和fnEffect
	 */
	private void addExternalProperties(RuleActionResult anResult){
		String fnEffect = "";
		if(!BlankUtil.isBlank(anResult.getFnEffect())) {
			fnEffect = anResult.getFnEffect();
		}
		String cnName = anResult.getCnName();
		
		for(Object fnRet : anResult.getFnResults()) {
			if(fnRet instanceof Map) {
				((Map) fnRet).put("fnEffect",fnEffect);
				((Map) fnRet).put("cnName",cnName);
			}
		}
	}
	
	/**
	 * 把action的result转为map result
	 * @param result
	 * @param anResult
	 * @param isMapResult
	 */
	private void convertActionMapResult(RuleResult result, RuleActionResult anResult, boolean isMapResult) {
		if(isMapResult) {
			//181011修改，添加额外参数如结果
			addExternalProperties(anResult);
			
			FunctionResultUtil.convertActionToMapResult(result, anResult);
		}
	}
	
	/**
	 * 设置结果方式. 外部接口推荐使用map的结果方式.
	 * @param result
	 * @param context
	 * @param isMapResult
	 */
	private void setMapResult(RuleResult result, RuleExecuteContext context, boolean isMapResult) {
		if(isMapResult) {
			result.setResults(null);
		} else {
			result.setResultMap(null);
			result.setFnCategories(null);
		}
	}
	
	/**
	 * 获取是否输出map结果
	 * @param contextInfo
	 * @return
	 */
	private boolean getIsOutputMaps(Map<String , Object> contextInfo) {
		Boolean isOutputMap = false;
		if(contextInfo != null ) {
			isOutputMap = (Boolean)contextInfo.get(BizFactConstants.RCI_IS_MAP_RESULT);
		}
		isOutputMap = isOutputMap == null ? false : isOutputMap;
		return isOutputMap;
	}
	
	/**
	 * 获取事实中的执行上下文对象
	 * @param context
	 * @return
	 */
	private Map<String , Object> getRuleContextInfo(RuleExecuteContext context) {
		FactsContainer container = context.getFactsContainer();
		List<Object> contextInfos = container.getFactsByType(BizFactConstants.RULE_CONTEXT_INFO);
		if(!BlankUtil.isBlank(contextInfos)) {
			//该对象应该只有一个
			return (Map) contextInfos.get(0);
		}
		return null;
	}
}
