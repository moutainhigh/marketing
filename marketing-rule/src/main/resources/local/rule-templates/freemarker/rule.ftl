<#if (isTest ??) && (isTest == true) >
package ${DrlUtil.getDrlTestPackageName()};
<#else>
package ${DrlUtil.getDrlPackageName(rule)};
</#if>

import org.drools.core.common.InternalFactHandle;
import org.drools.core.spi.KnowledgeHelper;
import com.oristartech.rule.base.op.model.CustomOpParameter;

<#-- 导入规则事实, 条件中需要用到的类 -->
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.oristartech.rule.drools.executor.context.RuleExecuteContext;
import com.oristartech.rule.drools.executor.context.RuleMatchContext;
import com.oristartech.rule.core.executor.util.RuleExecutorUtil;

import com.oristartech.rule.drools.util.DrlDroolsFunctions;

<#-- 输出global 定义, key 是变量名称，value是变量类型 -->
<#if (globalMap ??) && (globalMap?keys ??) >
   <#list globalMap?keys as key > 
       <#lt/>global ${globalMap[key]} ${key}; 
   </#list>
</#if> 

rule "${DrlUtil.getDrlRuleName(rule)}"
    salience ${(rule.priority!0)?c }
    <#if rule.validTimeStartStr ?? >
    date-effective "${rule.validTimeStartStr}"
    </#if>
    <#if rule.validTimeEndStr ?? >
    date-expires "${rule.droolsValidTimeEndStr}"
    </#if>
    no-loop true    
    
    <#if rule.ruleSections?size lte 1>
       <@composeSimpleSections />
    <#else> 
       <@composeMultiSections />
    </#if>   
end

<#-- 组装只有一个section的rule -->
<#macro composeSimpleSections >
   when
      <#if rule.commonSection ?? >
        <@composeConditions rule.commonSection  />
      </#if>
        <@composeConditions rule.ruleSections[0] /> 
   then
      <@composeActions rule.ruleSections[0] true/>
</#macro>

<#-- 组装有多个section的rule -->
<#macro composeMultiSections >
   when
    <#if rule.commonSection ?? >
       <@composeConditions rule.commonSection />
    </#if>
    <#list rule.ruleSections as section>  
       <@composeConditions section />
       <#if (section.ruleActions ?? )&& section.ruleActions?size gt 0>
         do[section_${section.id ?c}] 
       </#if>
       <#-- 若section.isSerial空 或为false 会输出or-->
       <#if ((section.isSerial!true || (section.isSerial == false )) && (section_index lt (rule.ruleSections?size - 1) ))>
         or 
       </#if>
    </#list>
   then
    ;
   <#list rule.ruleSections as section > 
      <@composeActions section false />
   </#list>
</#macro>

<#-- 组装动作 -->
<#macro composeActions section isSimple >
  <#if isSimple == false >
   then[section_${section.id ?c}]
  </#if>
     boolean isMatchMulti = Boolean.valueOf("${RuleExecutorUtil.getMatchMultiStr(rule.ruleType)}");
     List<Object> curMatchFacts = DrlDroolsFunctions.getCurrentMatchFacts(drools);
     
     RuleExecutorUtil.runActions(${exeContextName}, ${rule.id ?c}, ${section.id ?c}L, curMatchFacts, isMatchMulti);
</#macro>

<#-- 组装条件 --> 
<#macro composeConditions section >
  <#if (section ??) && (section.ruleConditions ??) && (section.ruleConditions?size gt 0) > 
   (
    <#list section.ruleConditions as condition>
     <@getLogicOp condition true condition_index />
     <@getLeftBracket condition />
       ${DrlUtil.getConditionModifier(condition)}
       Map( (this['_type'] == "${condition.modelCategoryName}" || this['_parent'] == "${condition.modelCategoryName}")
        <#if (condition.conditionElements ??) && (condition.conditionElements?size gt 0) > 
         && (
         <#list condition.conditionElements as conEle >
            <@getLogicOp condition false conEle_index />
            <@composeCompare conEle />
         </#list>
         )
        </#if> 
       )${DrlUtil.getConditionModifierEnd(condition)}
     <@getRightBracket condition />
   </#list>
   )    
  </#if>
</#macro>

<#-- 获取逻辑关系符 -->
<#macro getLogicOp condition isCon count >
  <#assign orAnd = ""/>
  <#list condition.conditionElements as conEle>
    <#if isCon == true >
       <#if conEle.isAnd ?? && conEle.isAnd == false>
          <#assign orAnd = "or"/>
       <#else>
          <#assign orAnd = "and" />
       </#if>
    <#else>
       <#if conEle.isAnd?? && conEle.isAnd == false >
          <#assign orAnd = "||" />
       <#else>
          <#assign orAnd = "&&" />
       </#if>
    </#if>
    <#break/>
  </#list>
  <#-- 分类条件和子条件缩进不一样 -->
  <#if count gt 0>
      <#if isCon == true>
      ${orAnd}
      <#else>
          ${orAnd}
      </#if>
  </#if>
</#macro>

<#-- 获取左括号 -->
<#macro getLeftBracket condition >
  <#list condition.conditionElements as conEle> 
    <#if (conEle.leftBracketNum) ?? && (conEle.leftBracketNum gt 0)>
      <#list 1..conEle.leftBracketNum as count>
        (
      </#list>
      <#-- 只判断一个 -->
      <#break/>
    </#if>
  </#list>
</#macro>

<#-- 获取右括号 -->
<#macro getRightBracket condition >
  <#list condition.conditionElements as conEle>
   <#if (conEle.rightBracketNum ??) && (conEle.rightBracketNum gt 0) >
     <#list 1..conEle.rightBracketNum as count>
        )
     </#list>
     <#-- 只判断一个 -->
     <#break />
   </#if>
  </#list>
</#macro>

<#-- 组装条件比较 -->
<#macro composeCompare conEle >
    <#if (conEle.isCustomOpCode ??) && conEle.isCustomOpCode == true > 
       ${conEle.opCode}(CustomOpParameter.create(${matchContextName}, 
                                                 ${rule.id ?c}, 
                                                 ${conEle.ruleConditionId ?c}L,
                                                 ${conEle.id ?c}L,
                                                 '${RuleConditionUtil.getModelCategoryName(conEle)}' ,
                                                 '${RuleConditionUtil.getRealFieldName(conEle)}' ,
                                                 "${StringEscapeUtils.escapeJava(conEle.operand! )}" ,
                                                 this,
                                                 this['${RuleConditionUtil.getRealFieldName(conEle)}']
                                                  ))   
    <#elseif ((conEle.opNum ?? ) && (conEle.opNum lt 0) ) && (!(conEle.isPlainOp ??) || conEle.isPlainOp == false) >
       <@composeNotNull conEle true/>
        this['${RuleConditionUtil.getRealFieldName(conEle)}'] ${conEle.opCode} (${RuleConditionUtil.getMultiOperand(conEle)})
       <@composeNotNull conEle false/>
    <#else> 
       <@composeNotNull conEle true/> 
        this['${RuleConditionUtil.getRealFieldName(conEle)}'] ${conEle.opCode} ${RuleConditionUtil.getSingleOperandFromCon(conEle)} 
       <@composeNotNull conEle false/>
    </#if>
</#macro>

<#-- 传教非空判断  -->
<#macro composeNotNull conEle isLeft>
    <#if (conEle.isNotNullOp ?? ) && (conEle.isNotNullOp == true) && isLeft == true> 
        ( this['${RuleConditionUtil.getRealFieldName(conEle)}'] != null && 
    <#elseif (conEle.isNotNullOp ?? ) && (conEle.isNotNullOp == true) && isLeft == false> 
        ) 
    </#if>
</#macro>
