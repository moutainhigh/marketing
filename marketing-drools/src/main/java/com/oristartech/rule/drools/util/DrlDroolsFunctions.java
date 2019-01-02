package com.oristartech.rule.drools.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.core.common.InternalFactHandle;
import org.drools.core.spi.KnowledgeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.FactConstants;
/**
 * 建立drl中的动作工具方法
 * @author chenjunfei
 *
 */
public class DrlDroolsFunctions {
	protected static final Logger log = LoggerFactory.getLogger(DrlDroolsFunctions.class);
	private static final String DROOLS_PK = "org.drools";
	/**
	 * 把当前匹配的事实添加到context中
	 * @param sectionId
	 * @param context
	 * @param droolHelper
	 */
	public static List<Object> getCurrentMatchFacts( KnowledgeHelper droolHelper) {
		InternalFactHandle[] fhs = droolHelper.getTuple().toFactHandles();
	    if(fhs == null || fhs.length < 1) {
	    	return null;
	    }
	    List<Object> matchs = new ArrayList<Object>();
	    for(InternalFactHandle fh : fhs) {
    	   Object fact = fh.getObject();
    	   //若是drools的类, 表示获得的object匹配的tuple不是直接是fact, 现在暂时只处理直接是fact的情况.
    	   if(fact.getClass().getName().startsWith(DROOLS_PK)) {
    		   continue;
    	   }
    	   //因为fact是unmodifiableMap, 要clone一份 
    	   //若条件前面的drools的collect关键字,可能是集合
    	   if(fact instanceof Collection) {
    		   for(Object ft : (Collection)fact) {
    	    	   if(ft.getClass().getName().startsWith(DROOLS_PK)) {
    	    		   continue;
    	    	   }
    			   cloneOneFact(matchs, ft);
    		   }
    	   } else {
    		   cloneOneFact(matchs, fact);
    	   }
	    }
	    return matchs;
	}

	private static void cloneOneFact(List<Object> matchs, Object fact) {
		Object factClone = null;
 	   	try {
 	   		Boolean isFact = (Boolean)BeanUtils.getProperty(fact, FactConstants.IS_FACT_KEY);
 	   		if(isFact != null && isFact == true) {
 			   String num = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_NUM_KEY);
 			   if(BlankUtil.isBlank(num) || existMatchFact(matchs, num)) {
 				  return ; 
 			   }
 			   if(fact instanceof Map) {
	    		   factClone = new HashMap();
    			   BeanUtils.copyProperties(factClone, fact);
    			   if(((Map)factClone).size() < 1) {
    				   return ;
    			   }
 			   } else {
	    		   factClone = BeanUtils.cloneBean(fact);
	    	   }
 			   
 			   if(factClone != null && !matchs.contains(factClone)) {
 				   matchs.add(factClone);
 			   }
 	   		}
       } catch(Exception e) {
    	   log.error("get match fact error " + fact.toString(), e);
    	   throw new RuleExecuteRuntimeException(e);
       }
	}
	
	private static boolean existMatchFact(List<Object> matchs, String num) {
		for(Object fact : matchs) {
			String factNum = BeanUtils.getPropertyStr(fact, FactConstants.CATEGORY_NUM_KEY);
			if(num != null && num.equals(factNum)) {
				return true;
			} 
		}
		return false;
	}
}
