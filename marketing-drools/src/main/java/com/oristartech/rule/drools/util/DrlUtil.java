package com.oristartech.rule.drools.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.vos.core.vo.RuleActionParameterVO;
import com.oristartech.rule.vos.core.vo.RuleActionVO;
import com.oristartech.rule.vos.core.vo.RuleConditionElementVO;
import com.oristartech.rule.vos.core.vo.RuleConditionVO;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleSectionVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

/**
 * 生成drl文件字符串工具类
 * @author user
 *
 */
public class DrlUtil {
	private final static String DRL_PACKAGE_PREFIX = "com.oristartech.marketing.rule.drl.";
	private final static String DRL_PACKAGE_PREFIX_TEST = "Test_";
	private static int testCount = 0;
	private final static String TEST_KNOWLEDGE_BASE_NAME = "_TEST_KNOWLEDGE_BASE_";
	
	private static final String COLLECT_KEY = "collect";
	
	public static String getConditionModifier(RuleConditionVO con) {
		if(!BlankUtil.isBlank(con.getModifier())) {
			if(con.getModifier().contains(COLLECT_KEY)) {
				return "ArrayList() from collect (";
			} else {
				return con.getModifier() + " ( ";
			}
		}
		return "";
	}
	
	public static String getConditionModifierEnd(RuleConditionVO con) {
		if(!BlankUtil.isBlank(con.getModifier())) {
			return ")";
		}
		return "";
	}
	
	/**
	 * 获取drl文件的package 名
	 * @param ruleType
	 * @return
	 */
	public static String getDrlPackageName(RuleVO rule) {
		return DRL_PACKAGE_PREFIX + getDrlRuleName(rule);
	}
	
	/**
	 * 创建判空条件.因为在模板写, 总有换行, 令规则文件凌乱,所以写在这里
	 * @param conEle
	 * @param isLeft
	 * @return
	 */
	public static String getNotNullCondition(RuleConditionElementVO conEle, boolean isLeft) {
		StringBuilder sbr = new StringBuilder();
		if(conEle.getIsNotNullOp() != null && conEle.getIsNotNullOp() == true && isLeft) {
			sbr.append("( ");
			sbr.append("this['" + conEle.getModelFieldName() + "']");
			sbr.append(" != null && ");
		} else if(conEle.getIsNotNullOp() != null && conEle.getIsNotNullOp() == true && isLeft == false) {
			sbr.append(" )");
		}
		return sbr.toString();
	}
	/**
	 * 返回acton参数
	 * @param action
	 * @return
	 */
	public static String getActionParameters(RuleActionVO action) {
		if(action != null) {
			List<RuleActionParameterVO> params = new ArrayList<RuleActionParameterVO>();
			if(!BlankUtil.isBlank(action.getParameters())) {
				for(RuleActionParameterVO paramVO : action.getParameters()) {
					if(paramVO.getIsFile() == null || paramVO.getIsFile() == false)  {
						params.add(paramVO);
					}
				}
				return StringEscapeUtils.escapeJava(JsonUtil.objToJson(params));
			}
		}
		return "";
	}
	
	/**
	 * 获取测试package name, 因为测试的规则,测试完要整个package删除, 所以PackageName唯一.
	 * @return
	 */
	public synchronized static String getDrlTestPackageName() {
		testCount = testCount % 1000000;
		String name = DRL_PACKAGE_PREFIX + DRL_PACKAGE_PREFIX_TEST + testCount ;
		testCount += 1;
		return name;
	}
	
	/**
	 * 获取在规则文件中的规则名字
	 * @param ruleVO
	 * @return
	 */
	public static String getDrlRuleName(RuleVO rule) {
		return "Group_" + rule.getRuleGroupId() + "_Rule_" + rule.getId() + "_" + rule.getRuleType() ;
	}
	
	public static String getTestKnowledgeType() {
		return TEST_KNOWLEDGE_BASE_NAME;
	}
	
	public static void initRuleGroupIdsForTest(RuleGroupVO groupVO) {
		int groupId = 1;
		//每个测试都在各种的package名下进行测试, 所以group id可以一样
		groupVO.setId(1);
		int ruleId = 1;
		Long sectionId = 1L;
		Long actionId = 1L;
		if( groupVO.getRuleSectionVO() != null ){
			groupVO.getRuleSectionVO().setId(sectionId);
			sectionId ++;
			if(!BlankUtil.isBlank(groupVO.getRuleSectionVO().getRuleActions())) {
				for(RuleActionVO action : groupVO.getRuleSectionVO().getRuleActions()) {
					action.setId(actionId);
					actionId ++;
				}
			}
		}
		
		if(!BlankUtil.isBlank(groupVO.getRules())) {
			for(RuleVO rule : groupVO.getRules()) {
				rule.setId(ruleId);
				ruleId++;
				if( !BlankUtil.isBlank(rule.getRuleSections()) ){
					for(RuleSectionVO section : rule.getRuleSections()) {
						section.setId(sectionId);
						sectionId ++;
						if(!BlankUtil.isBlank(section.getRuleActions())) {
							for(RuleActionVO action : section.getRuleActions()) {
								action.setId(actionId);
								actionId ++;
							}
						}
					}
					
				}
			}
		}
	}
}
