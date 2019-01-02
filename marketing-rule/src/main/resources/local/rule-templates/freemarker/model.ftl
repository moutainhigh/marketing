<#-- 根据规则ModelCategory类生成java类  -->
package ${package};

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
<@generateImports />

/**
 *  ${category.cnName!}
 *  "_" 开头的属性是内部使用的属性.
 */
public <#if (category.isAbstract ??) && (category.isAbstract == true) >abstract</#if> class ${category.name} <#if category.parentName ??> extends ${ category.parentName}</#if> implements Serializable {
       /**
        * 类型标识, 方便转为json加上类型标识
        */
       private String _type = ${category.name}.class.getSimpleName();
       //分类对象号码标识
       @JsonIgnore
       private Integer _num;
    
       //标识是否是事实
       @JsonIgnore
       private Boolean _fact = true;
       
       //父类
       private String _parent;
       
       //是否需要主动加载
       private Boolean _load;
       
    <@generateFieldDeclares />
    
       public String get_typeList() {
           StringBuilder sb = new StringBuilder();
       
           Class clz = this.getClass();
           while(!clz.getSimpleName().equals(Object.class.getSimpleName())) {
               sb.append(clz.getSimpleName());
               sb.append(",");
               clz = clz.getSuperclass();
           }
           return sb.replace(sb.length() - 1 , sb.length(), "").toString();
       }
       
       public void set_typeList(String typeList) {
           //为了方便用工具setting, 例如DozerMapper, 提供了个冗余方法. 
       }
       
       public String get_parent() {
           return this.getClass().getSuperclass().getSimpleName();
       }
       
       public void set_parent(String parent) {
           //为了方便用工具setting, 例如DozerMapper, 提供了个冗余方法. 
       }
       
       public String get_type() {
           return _type;
       }
    
       public void set_type(String type) {
           this._type = type;
       }
    
       public Integer get_num() {
           return _num;
       }
       public void set_num( Integer num){
           this._num = num;
       }
       public Boolean get_fact() {
           return _fact;
       }
       public void set_fact(Boolean fact) {
           this._fact = fact;
       }
       
       public Boolean get_load() {
           return _load;
       }
    
       public void set_load(Boolean load) {
           this._load = load;
       }
    <@generateMethodDeclares />
}


<#-- 产生import 语句-->
<#macro generateImports  >
  <#if category.modelFields ??>
       <#assign importMap = {}>
       <#list category.modelFields as field>
          <#if field.type ?? && !field.type?starts_with('java.lang') >
             <#assign importMap = importMap + {field.type : "1"} />
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
    <#if !(field.isNotExportToVO ??) || (field.isNotExportToVO == false)>
     <#if field.type ??>
       /**
        * ${field.cnName!} 
        * ${field.remark!}
        */ 
        <#if category.name == 'CommonInfo' && field.type?index_of('Date') gt 0>
       private <@getFieldShortType field/> ${field.name} = new <@getFieldShortType field/>();
        <#else>
       private <@getFieldShortType field/> ${field.name};
        </#if>
      </#if>
    </#if>
  </#list>
  <#if category.name == 'FilmTicketInfo'>
       /**
        * 商品类型 
        * 
        */ 
       private String saleItemType = "0";
  </#if>
  <#if category.name == 'MerchandiseInfo'>
       /**
        * 商品类型 
        * 
        */ 
       private String saleItemType = "1";
  </#if>
 </#if>
</#macro>

<#-- 产生方法声明 语句-->
<#macro generateMethodDeclares  >
  <#if category.modelFields ??>
    <#list category.modelFields as field>
    <#if !(field.isNotExportToVO ??) || (field.isNotExportToVO == false)>
      <#if field.name ?? && field.type ??>
      public <@getFieldShortType field /> get${field.name?cap_first}() {
         return ${field.name};
      }
    
      public void set${field.name?cap_first}(<@getFieldShortType field/> ${field.name}) {
         this.${field.name} = ${field.name} ;
      }
      </#if>
    </#if>  
   </#list>
   <#if category.name == 'FilmTicketInfo'>
      public String getSaleItemType() {
         return this.saleItemType ; 
      }
      public void setSaleItemType(String saleItemType) {
         this.saleItemType = "0";  //影票固定0;
      }
   </#if>
   <#if category.name == 'MerchandiseInfo'>
      public String getSaleItemType() {
         return this.saleItemType ; 
      }
      
      public void setSaleItemType(String saleItemType) {
         this.saleItemType = "1";  //卖品固定1;
      }
   </#if>
  </#if>
</#macro>

<#macro getFieldShortType field  >
  <#if field.type ??>
     <#t>${field.type?substring(field.type?last_index_of('.') + 1)}
  </#if>
</#macro>