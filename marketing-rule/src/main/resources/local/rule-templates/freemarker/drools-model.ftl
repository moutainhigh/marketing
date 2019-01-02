<#-- 根据规则ModelCategory类生成java类  -->
package ${package};

<@generateImports />

declare ${category.name} 
    <@generateFieldDeclares />
end


<#-- 产生import 语句-->
<#macro generateImports  >
  <#if category.modelFields ??>
       <#assign importMap = {}>
       <#list category.modelFields as field>
          <#if field.type ?? && !field.type?starts_with('java.lang') >
             <#assign importMap= importMap + {field.type : "1"} />
          </#if>  
       </#list>
       <#list importMap?keys as key>
          <#lt>import ${key}; 
       </#list>
    </#if>
</#macro>

<#-- 产生属性声明 语句-->
<#macro generateFieldDeclares  >
  <#if category.modelFields ??>
    <#list category.modelFields as field>
     <#if field.type ??>
     ${field.name} : <@getFieldShortType field/> 
     </#if>
    </#list>
  </#if>
</#macro>

<#macro getFieldShortType field  >
  <#if field.type ??>
     <#t>${field.type?substring(field.type?last_index_of('.') + 1)}
  </#if>
</#macro>