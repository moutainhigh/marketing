package com.oristartech.rule.functions.result.filters.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.core.result.filters.IResultFilter;
import com.oristartech.rule.core.result.filters.IResultFilterChain;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.result.RuleResult;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 删除没用的规则。
 * 例如把规则所有actionResult的为空的规则结果移除。 
 * @author chenjunfei
 *
 */
@Component(value="ruleRemoveUselessResultFilter")
public class RemoveUselessResultFilter implements IResultFilter {
	
	private static Logger log = LoggerFactory.getLogger(RemoveUselessResultFilter.class);
	
	public void doFilter(RuleExecuteContext context, IResultFilterChain chain) {
		if(context == null) {
			return;
		}
		List<RuleResult> tempResults = context.getRuleResults();
		if(BlankUtil.isBlank(tempResults) ) {
			return;
		}
		removeResult(tempResults, context);
	    chain.doFilter(context);
	}
	
	private void removeResult(List<RuleResult> tempResults, RuleExecuteContext context) {
		Iterator<RuleResult> it = tempResults.iterator();
		String mode = getExecuteMode(context);
		while(it.hasNext()) {
			//移除规则结果为空的
			RuleResult result = it.next();
			List<RuleActionResult> anResults = result.getResults();
			if(BlankUtil.isBlank(result.getResults())) {
				log.info(getMsg(result) );
				it.remove();
				continue;
			}
			boolean isActionEmpty = true;
			for(RuleActionResult anResult : anResults) {
				if(!BlankUtil.isBlank(anResult.getFnResults())){
					isActionEmpty = false;
					break;
				}
			}
			if(isActionEmpty) {
				log.info(getMsg(result) );
				it.remove();
				continue;
			}  
			
			removeUnMatchMode(result, it, mode);
			
		}
	}
	
	/**
	 * 若指定了只返回某种执行模式，把其他模式过滤。
	 * 后期可以通过添加规则中添加对比执行模式条件实现, 加快效率
	 * @param results
	 * @param context
	 */
	private void removeUnMatchMode(RuleResult result  , Iterator<RuleResult> it, String mode) {
		if(BlankUtil.isBlank(mode) ) {
			return ;
		}
		if(result.getRuleVO() == null || !mode.equals(result.getRuleVO().getExecuteMode())) {
			it.remove();
		}
	}
	
	private String getExecuteMode(RuleExecuteContext context) {
		for(Object fact  : context.getAllFacts()) {
			String type = String.valueOf(((Map)fact).get(FactConstants.CATEGORY_TYPE_KEY));
			if(BizFactConstants.SALE_INFO.equals(type)) {
				Map<String, Object> saleInfo = (Map)fact;
				Object mode = saleInfo.get("ruleExecuteMode");
				if(mode != null) {
					return mode.toString();
				}
			}
		}
		return null;
	}
	
	private String getMsg(RuleResult result) {
		return "规则匹配，但没方法结果，移除本规则结果 ， ruleId=" + result.getRuleVO().getId();
	}
}
