package com.oristartech.rule.core.executor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.core.base.service.IModelCategoryService;
import com.oristartech.rule.core.core.template.service.ITemplateContentService;
import com.oristartech.rule.drools.executor.AbstractDroolsManagerServiceImpl;
import com.oristartech.rule.drools.executor.AbstractRuleExecutorServiceImpl;
import com.oristartech.rule.drools.executor.AbstractRuleKnowledgeWrapper;
import com.oristartech.rule.drools.executor.RuleKnowledgeWrapper;
import com.oristartech.rule.drools.util.DrlUtil;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;
import com.oristartech.rule.vos.core.enums.RuleStatus;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

@Component
public class RuleDroolsManagerServiceImpl extends AbstractDroolsManagerServiceImpl {

	@Override
	protected AbstractRuleKnowledgeWrapper getRuleKnowledgeWrapper() {
	    return RuleKnowledgeWrapper.getInstance();
	}

	private static final int WRAN_COMPILE_TIME = 5000; 
	
	private static final Logger log = LoggerFactory.getLogger(AbstractRuleExecutorServiceImpl.class);
	
	@Autowired
	private ITemplateContentService templateContentService;
	@Autowired
	private IModelCategoryService ruleModelCategoryService;
	
	/**
	 * 现在的规则是一个规则一个package。是为了方便移除，并且
	 * 好像drools5.6，6.1在移除rule但package还存在是有内存泄露。
	 * 所以现在是直接移除规则整个package
	 */
	public void removeRuleInEngine(RuleGroupVO ruleGroup) {
		removePackageInEngine(ruleGroup);
	}
	
	public List<String> loadRuleToWorkMemory(RuleGroupVO ruleGroup) {
		return loadRuleToWorkMemory(ruleGroup, null, false);
	}
	
	public List<String> loadRuleToWorkMemory(List<RuleGroupVO> ruleGroups) {
	    return loadRuleToWorkMemory(ruleGroups, null, false);
	}
	
	/**
	 * 加载若干规则组到WorkMemory, 若isTest是true只会加载到测试package中
	 * @param ruleGroups
	 * @param knowledgeType 可空, 若是空, 默认用规则类型
	 * @param isTest 是否是测试状态
	 * @return 返回生成的规则package 名称列表
	 */
	public List<String> loadRuleToWorkMemory(List<RuleGroupVO> ruleGroups, String knowledgeMapKey, boolean isTest) {
		if(BlankUtil.isBlank(ruleGroups)) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		for(int i =0 ; i < ruleGroups.size(); i++) {
			List<String> packages = loadRuleToWorkMemory(ruleGroups.get(i), knowledgeMapKey,  isTest);
			if(!BlankUtil.isBlank(packages)) {
				result.addAll(packages);
			}
		}
		return result;
	}

	/**
	 * 移除单个rule
	 * @param ruleGroup
	 */
	protected void removeRuleInEngineInternal(RuleGroupVO ruleGroup) {
		if(ruleGroup != null && !BlankUtil.isBlank(ruleGroup.getRules())) {
			List<String> names = new ArrayList<String>();
			String knowledgeMapKey = ruleGroup.getRules().get(0).getRuleType();
			String packageName = DrlUtil.getDrlPackageName(ruleGroup.getRules().get(0));
			for(RuleVO ruleVO : ruleGroup.getRules()) {
				String ruleName = DrlUtil.getDrlRuleName(ruleVO);
				names.add(ruleName);
			}
			getRuleKnowledgeWrapper().removeRule(String.valueOf(ruleGroup.getTenantId()),knowledgeMapKey, packageName, names);
		}
	}
	
	/**
	 * 移除指定类型的规则包名
	 * @param knowledgeType
	 * @param pkNames 要移除的package 包名
	 */
	public void removePackageInEngine(RuleGroupVO ruleGroup) {
		if(ruleGroup != null && !BlankUtil.isBlank(ruleGroup.getRules())) {
			String knowledgeMapKey = ruleGroup.getRules().get(0).getRuleType();
			List<String> packageNames = new ArrayList<String>();
			for(RuleVO vo : ruleGroup.getRules()) {
				String pkName = DrlUtil.getDrlPackageName(vo);
				if(!packageNames.contains(pkName)) {
					packageNames.add(pkName);
				}
			}
			getRuleKnowledgeWrapper().removeKnowledgePackage(String.valueOf(ruleGroup.getTenantId()),knowledgeMapKey, packageNames);
		}
	}
	
	protected void removePackageInEngine(String tenantId,String knowledgeMapKey, List<String> pkNames) {
		getRuleKnowledgeWrapper().removeKnowledgePackage(tenantId,knowledgeMapKey, pkNames);
    }
	
	
	public void removeKBaseInTest(String tenantId,String knowledgeMapKey) {
		getRuleKnowledgeWrapper().removeKBase(tenantId,knowledgeMapKey);
	    
	}
	
	public boolean existRulesInEngine(RuleGroupVO ruleGroup) {
		if(ruleGroup != null && !BlankUtil.isBlank(ruleGroup.getRules())) {
			boolean result = true;
			for(RuleVO ruleVO : ruleGroup.getRules()) {
				String knowledgeMapKey = ruleVO.getRuleType();
				String packageName = DrlUtil.getDrlPackageName(ruleVO);
				String ruleName = DrlUtil.getDrlRuleName(ruleVO);
				boolean exist = getRuleKnowledgeWrapper().existRule(String.valueOf(ruleGroup.getTenantId()),knowledgeMapKey, packageName, ruleName);
				if(exist == false) {
					return false;
				}
			}
			return true;
		}
	    return false;
	}

	/**
	 * 加载一个规则组到WorkMemory, 若isTest是true只会加载到测试package中
	 * @param ruleGroups
	 * @param knowledgeType 可空, 若是空, 默认用规则类型
	 * @param isTest 是否是测试状态
	 * @return 返回生成的规则package 名称列表
	 */
	public List<String> loadRuleToWorkMemory(RuleGroupVO groupVO, String knowledgeMapKey,  boolean isTest) {
		if(groupVO != null) {
			List<ModelCategoryVO> mergeCategoryVOs = ruleModelCategoryService.getAllCanMergeFieldCon(); 
			List<String> pkNames = new ArrayList<String>();
			List<RuleVO> ruleVOs = groupVO.getRules();
			if(!RuleStatus.RUNNING.equals(groupVO.getStatus()) && !isTest ) {
				log.error("======can not load not running status rule group to engine!, group id:" + groupVO.getId() + "========");
				return null;
			}
			for(RuleVO ruleVO : ruleVOs) {
				if(!RuleStatus.RUNNING.equals(ruleVO.getStatus()) && !isTest) {
					log.error("======can not load not running status rule group to engine!, rule id:" + ruleVO.getId() + "========");
					return null;
				}
				Map<String, List<String>> removeRulesPackages = new HashMap<String, List<String>>();
				if(BlankUtil.isBlank(knowledgeMapKey)) {
					if(isTest) {
						knowledgeMapKey = DrlUtil.getTestKnowledgeType();
					} else {
						knowledgeMapKey = ruleVO.getRuleType();
						String packageName = DrlUtil.getDrlPackageName(ruleVO);
						String ruleName = DrlUtil.getDrlRuleName(ruleVO);
						if(removeRulesPackages.get(packageName) == null) {
							removeRulesPackages.put(packageName, new ArrayList<String>());
						}
						removeRulesPackages.get(packageName).add(ruleName);
					}
				}
				ruleVO.setCommonSection(groupVO.getRuleSectionVO());
				ruleVO.mergeConditons(mergeCategoryVOs);
				try {
//					解析drl文件到规则引擎内存中 Knowledgebase 规则文件解析出来的drl文件中的Java代码 invoke对应规则的方法对规则进行分析
					String ruleContent = templateContentService.toRuleContent(ruleVO, isTest);
//					System.out.println(ruleContent);
					long start = System.currentTimeMillis();
					pkNames.addAll(getRuleKnowledgeWrapper().addRules(String.valueOf(groupVO.getTenantId()),knowledgeMapKey, removeRulesPackages, ruleContent));
					
//					if(ruleContent.contains("Group_403_Rule_434_SaleActivity")){
//						for(int i=0;i<10000;i++){
//						
//						if(i>=1){
//							ruleContent = ruleContent.replaceFirst("package com.oristartech.marketing.rule.drl.Group_403_Rule_434_SaleActivity"+(i-1), "package com.oristartech.marketing.rule.drl.Group_403_Rule_434_SaleActivity"+i);
//						}else{
//							ruleContent = ruleContent.replaceFirst("package com.oristartech.marketing.rule.drl.Group_403_Rule_434_SaleActivity", "package com.oristartech.marketing.rule.drl.Group_403_Rule_434_SaleActivity"+i);
//						}
//						pkNames.addAll(getRuleKnowledgeWrapper().addRules(knowledgeMapKey, null, ruleContent));
//						if(i%1000 == 0)
//							System.out.println(i);
//						}
//					}
					long end = System.currentTimeMillis() - start;
					
					if(end >= WRAN_COMPILE_TIME) {
						log.warn("compile time was too long, time=" + end + ", ruleId=" + ruleVO.getId() );
					}
				} catch(Exception e) {
					log.error("------加载规则引擎文件出错, 本规则不会加载-----, 规则id：" + ruleVO.getId() + ", 规则名称：" + ruleVO.getName(), e );
				}
				
			}
			return pkNames;
		}
		return null;
	}
}
