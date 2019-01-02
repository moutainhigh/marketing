package com.oristartech.rule.core.core.template.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.oristartech.marketing.core.exception.ServiceRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.config.RuleProperties;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.constants.RuleTemplateContants;
import com.oristartech.rule.core.base.service.IModelCategoryService;
import com.oristartech.rule.core.core.template.service.ITemplateContentService;
import com.oristartech.rule.core.executor.util.RuleConditionUtil;
import com.oristartech.rule.core.executor.util.RuleExecutorUtil;
import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleMatchContext;
import com.oristartech.rule.drools.util.DrlUtil;
import com.oristartech.rule.vos.base.enums.ModelCategoryType;
import com.oristartech.rule.vos.base.vo.ModelCategoryVO;
import com.oristartech.rule.vos.core.vo.RuleVO;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;

@Component
public class FreeMarkerTemplateServiceImpl implements ITemplateContentService {

	private Logger log = LoggerFactory.getLogger(FreeMarkerTemplateServiceImpl.class);
	@Autowired
	private RuleProperties ruleSystemConfigProps;
	@Autowired
    private FreeMarkerConfigurer ruleFreeMarkerConfiguration;
//	private Configuration ruleFreeMarkerConfiguration;
	@Autowired
	private IModelCategoryService ruleModelCategoryService;
	
	private static final String JAVA_PACKAGE = "com.oristartech.rule.basic.model";
	
	public String toRuleContent(RuleVO ruleVO, boolean isTest) {
		if(ruleVO != null) {
			String templateFile = ruleSystemConfigProps.getRuleFtl();
			if(templateFile == null) {
				throw new ServiceRuntimeException("请提供规则模板文件路径");
			}
			Map<String, Object> context = new HashMap<String, Object>();
			Map<String, String> globalMap = new HashMap<String, String>();
			globalMap.put(RuleTemplateContants.RULE_EXE_CONTEXT_NAME, RuleExecuteContext.class.getName());
			globalMap.put(RuleTemplateContants.RULE_MATCH_CONTEXT_NAME, RuleMatchContext.class.getName());
			
			context.put("isTest", isTest);
			context.put("globalMap", globalMap);
			context.put("rule", ruleVO);
			context.put("exeContextName", RuleTemplateContants.RULE_EXE_CONTEXT_NAME);
			context.put("matchContextName", RuleTemplateContants.RULE_MATCH_CONTEXT_NAME);
			
			try {
				putStaticClassToContext(context);
				Template tpl = ruleFreeMarkerConfiguration.getConfiguration().getTemplate(templateFile);
				String content = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, context);
				if(log.isDebugEnabled()) {
					log.debug("rule drools content is :" + content);
				}
				
				return content;
			} catch (Exception e) {
				log.error("------转换规则引擎文件出错,本规则不会加载-----, 规则id：" + ruleVO.getId() + ", 规则名称：" + ruleVO.getName(), e );
			}
		}
		return null;
	}
	
//	@Override
//	public void createJavaClzFromCategory(String path, List<String> includeCategoryNames) {
//		if(BlankUtil.isBlank(includeCategoryNames)) {
//			return;
//		}
//			
//		List<ModelCategoryVO> categories = ruleModelCategoryService.getCategorys(ModelCategoryType.DROOLS);
//	    if(!BlankUtil.isBlank(categories)) {
//	    	String templateFile = ruleSystemConfigProps.getRuleModelFtl();
//	    	if(templateFile == null) {
//				throw new ServiceRuntimeException("请提供分类模板文件路径");
//			}
//	    	//在classpath创建文件目录
//	    	if(BlankUtil.isBlank(path)) {
//	    		path = this.getClass().getResource("/").getPath() + File.separator + ".." + File.separator + "model_categorys" ;
//	    	} 
//	    	if(!path.endsWith(File.separator)) {
//	    		path += File.separator;
//	    	}
//	    	path += (JAVA_PACKAGE.replace(".", File.separator));
//	    	File filePath = new File(path);
//	    	filePath.mkdirs();
//	    	
//	    	try {
//	    		FileWriter fw = null;
//	    		for(ModelCategoryVO category : categories) {
//	    			if(!includeCategoryNames.contains(category.getName())) {
//	    				continue;
//	    			}
//		    		Map<String, Object> context = new HashMap<String, Object>();
//			    	context.put("category", category);
//			    	context.put("package", JAVA_PACKAGE);
//			    	Template tpl = ruleFreeMarkerConfiguration.getConfiguration().getTemplate(templateFile);
//					String content = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, context);
//					
//					String file = filePath.getPath() + File.separator + category.getName() + ".java";
//					File f = new File(file);
//					Writer out = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
//					out.write(content);
//					out.close();
//		    	}
//	    	} catch (Exception e) {
//				throw new ServiceRuntimeException(e);
//			}
//	    }
//	}
	
//	public void createJavaClzFromCategory(String path) {
//		List<ModelCategoryVO> categories = ruleModelCategoryService.getCategorys(ModelCategoryType.DROOLS);
//		if(!BlankUtil.isBlank(categories)) {
//			List<String> includes = new ArrayList<String>();
//			for(ModelCategoryVO vo : categories) {
//				includes.add(vo.getName());
//			}
//			createJavaClzFromCategory(path, includes);
//		}
//	}
	
	public String convertModelToDroolsType(ModelCategoryVO category) {
	    if(!BlankUtil.isBlank(category)) {
	    	String templateFile = ruleSystemConfigProps.getDroolModelFtl();
	    	if(templateFile == null) {
				throw new ServiceRuntimeException("请提供分类规则模板文件路径");
			}
	    	
	    	try {
	    		Map<String, Object> context = new HashMap<String, Object>();
		    	context.put("category", category);
		    	context.put("package", JAVA_PACKAGE);
		    	Template tpl = ruleFreeMarkerConfiguration.getConfiguration().getTemplate(templateFile);
				return FreeMarkerTemplateUtils.processTemplateIntoString(tpl, context);
	    	} catch (Exception e) {
				throw new ServiceRuntimeException(e);
			}
	    }
	    return null;
	}
	
	
	private void putStaticClassToContext(Map<String, Object> context) throws Exception {
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		TemplateHashModel staticModels = wrapper.getStaticModels();
		
		TemplateHashModel stringEscapeUtils = (TemplateHashModel) staticModels.get(StringEscapeUtils.class.getName());
		context.put(StringEscapeUtils.class.getSimpleName(), stringEscapeUtils);
		
		TemplateHashModel factConstants = (TemplateHashModel) staticModels.get(FactConstants.class.getName());
		context.put(FactConstants.class.getSimpleName(), factConstants);
		
		TemplateHashModel jsonUtils = (TemplateHashModel) staticModels.get(JsonUtil.class.getName());
		context.put(JsonUtil.class.getSimpleName(), jsonUtils);
		
		TemplateHashModel drlUtil = (TemplateHashModel) staticModels.get(DrlUtil.class.getName());
		context.put(DrlUtil.class.getSimpleName(), drlUtil);
		
		TemplateHashModel ruleExecutorUtil = (TemplateHashModel) staticModels.get(RuleExecutorUtil.class.getName());
		context.put(RuleExecutorUtil.class.getSimpleName(), ruleExecutorUtil);
		
		TemplateHashModel ruleConditionUtil = (TemplateHashModel) staticModels.get(RuleConditionUtil.class.getName());
		context.put(RuleConditionUtil.class.getSimpleName(), ruleConditionUtil);
	}
	
	public void setRuleModelCategoryService(IModelCategoryService ruleModelCategoryService) {
    	this.ruleModelCategoryService = ruleModelCategoryService;
    }

}
