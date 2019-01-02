package com.oristartech.rule.functions.sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.RuleConstans;
import com.oristartech.rule.drools.executor.context.ActionExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.result.ResultRuleVO;
import com.oristartech.rule.vos.result.action.RuleActionResult;

/**
 * 按座位等级修改单票售价
 * @author user
 *
 */
public class FilmSeatGradePriceModify extends FilmTicketPriceModify {
	//座位等级参数
	protected static final String SEAT_GRADE_PARAM = "seatGrade";
	
	public RuleActionResult run(RuleExecuteContext context, ActionExecuteContext acContext, ResultRuleVO ruleVO, RuleActionVO actionVO) {
		return super.run(context, acContext, ruleVO, actionVO);
	}
	
	/**
	 * 获取可以进行修改的影票事实
	 * @return
	 */
	@Override
	protected List<Object> getModifyFilmFacts(RuleExecuteContext context, ActionExecuteContext acContext,
			ResultRuleVO ruleVO, RuleActionVO actionVO) {
		List<Object> filmFacts = super.getModifyFilmFacts(context, acContext, ruleVO, actionVO);
		List<String> seatGrades = getSeatGrades(actionVO.getParameterMap());
		
		log.info("<run> ruleid=" + ruleVO.getId() + " actionid=" + actionVO.getId() + " params=" + JsonUtil.objToJson(seatGrades)
			+ " merFacts=" + JsonUtil.objToJson(filmFacts));
		
		if(BlankUtil.isBlank(filmFacts)) {
			log.info("<run> filmFacts=null");
			return null;
		}
		
		//根据方法中的座位等级过滤.
		if(!BlankUtil.isBlank(filmFacts)) {
			if(!BlankUtil.isBlank(seatGrades)) {
				Iterator<Object> it = filmFacts.iterator();
				while(it.hasNext()) {
					Object fact = it.next();
					String seatGrade = BeanUtils.getPropertyStr(fact, BizFactConstants.FILM_SEAT_GRADE);
					if(BlankUtil.isBlank(seatGrade) || !seatGrades.contains(seatGrade)) {
						it.remove();
					}
				}
			}
		}
		
		return filmFacts;
	}
	
	private List<String> getSeatGrades(Map<String, String> params) {
		String seatGrades = params.get(SEAT_GRADE_PARAM);
		if(!BlankUtil.isBlank(seatGrades)) {
			String[] fields = seatGrades.split(RuleConstans.RULE_PARAMETER_SPLITER);
			return Arrays.asList(fields);
		}
		return new ArrayList<String>();
	}
	
	/**
	 * 校验基本参数
	 * @param params
	 * @return
	 */
	@Override
	protected boolean validate(Map<String, String> params) {
		String seatGrades = params.get(SEAT_GRADE_PARAM);
		if(BlankUtil.isBlank(seatGrades)) {
			return false;
		}
		
		return super.validate(params);
	}
}
