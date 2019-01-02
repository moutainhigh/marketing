package com.oristartech.rule.functions.common;

import java.util.List;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 会员类执行型方法
 * @author chenjunfei
 * 
 */
public class SimpleMemberExecuteFunction extends EchoParamFunction {
	
	/**
	 * 现在的执行型方法只是简单输出设置的参数.
	 */
	public RuleActionResult run(RuleExecuteContext context,ActionExecuteContext acContext,  ResultRuleVO ruleVO, RuleActionVO actionVO) {
		if(existMemberInfo(context)) {
		    return super.run(context, acContext, ruleVO, actionVO);
		}
		return null;
	}
	
	private boolean existMemberInfo(RuleExecuteContext context) {
		//若是测试，可以不用填入会员属性，也可以输出方法内容
		if(context.isTest()) {
			return true;
		}
		List<Object> allFacts = context.getAllFacts();
		if(!BlankUtil.isBlank(allFacts)) {
			for(Object fact : allFacts) {
				if(BizFactConstants.MEMBER_INFO.equals(BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_TYPE_KEY))) {
					return true;
				}
			}
		}
		return false;
	}
}
