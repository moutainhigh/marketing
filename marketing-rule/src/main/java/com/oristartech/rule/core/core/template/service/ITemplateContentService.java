package com.oristartech.rule.core.core.template.service;

import java.util.List;

import com.oristartech.rule.vos.base.vo.ModelCategoryVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

/**
 * 把数据库对应的记录，根据对应模板转换成对应格式
 * @author chenjunfei
 *
 */
public interface ITemplateContentService {
	/**
	 * 根据规则vo, 输出规则内容
	 * @param ruleVO
	 * @return
	 */
	public String toRuleContent(RuleVO ruleVO, boolean isTest);
	
//	/**
//	 * 把所有modelcategory变为java class文件, 若参数path是空, 则生成的文件在class同级目录, 而且名称是 model_categorys下面.
//	 * @param path 指定文件生成位置
//	 */
//	public void createJavaClzFromCategory(String path) ;
//	
//	/**
//	 * 把modelcategory变为java class文件, 若参数path是空, 则生成的文件在class同级目录, 而且名称是 model_categorys下面.
//	 * @param includeCategoryNames 包含的category
//	 * @param path 指定文件生成位置
//	 */
//	public void createJavaClzFromCategory(String path, List<String> includeCategoryNames) ;
	
	/**
	 * 把modelcategory 声明为drools的动态声明类型, 可以用该类型操作属性, 否则全部用map代表category
	 * @param category
	 * @return
	 */
	public String convertModelToDroolsType(ModelCategoryVO category);
}
