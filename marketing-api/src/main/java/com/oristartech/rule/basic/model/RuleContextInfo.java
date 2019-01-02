package com.oristartech.rule.basic.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *  规则执行信息-用于控制规则行为
 *  "_" 开头的属性是内部使用的属性.
 */
public  class RuleContextInfo  implements Serializable {
       /**
        * 类型标识, 方便转为json加上类型标识
        */
       private String _type = RuleContextInfo.class.getSimpleName();
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
       
       /**
        * 修改票价时是否忽略方法中票数限制 
        * 
        */ 
       private Boolean ignoreFnTicketAmountLimit;
       /**
        * 修改票价时是否忽略卡等级票数限制 
        * 
        */ 
       private Boolean ignoreCardTicketAmountLimit;
       /**
        * 是否输出消耗的商品事实 
        * 
        */ 
       private Boolean isOutputConsumeFacts;
       /**
        * 规则结果用map返回 
        * 
        */ 
       private Boolean isMapResult;
       /**
        * 忽略比较属性 
        * 自定义的比较符号, 可以根据本属性忽略比较,直接返回true, 多个用逗号隔开,每个格式是categoryName-fieldName
        */ 
       private String ignoreCategoryFields;
       /**
        * 是否返回最优价格组合 
        * 
        */ 
       private Boolean isOutGroupPriceResult;
       /**
        * 是否启用新的消耗属性 
        * 
        */ 
       private Boolean isUseNewConsumeItem;
    
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
      public Boolean getIgnoreFnTicketAmountLimit() {
         return ignoreFnTicketAmountLimit;
      }
    
      public void setIgnoreFnTicketAmountLimit(Boolean ignoreFnTicketAmountLimit) {
         this.ignoreFnTicketAmountLimit = ignoreFnTicketAmountLimit ;
      }
      public Boolean getIgnoreCardTicketAmountLimit() {
         return ignoreCardTicketAmountLimit;
      }
    
      public void setIgnoreCardTicketAmountLimit(Boolean ignoreCardTicketAmountLimit) {
         this.ignoreCardTicketAmountLimit = ignoreCardTicketAmountLimit ;
      }
      public Boolean getIsOutputConsumeFacts() {
         return isOutputConsumeFacts;
      }
    
      public void setIsOutputConsumeFacts(Boolean isOutputConsumeFacts) {
         this.isOutputConsumeFacts = isOutputConsumeFacts ;
      }
      public Boolean getIsMapResult() {
         return isMapResult;
      }
    
      public void setIsMapResult(Boolean isMapResult) {
         this.isMapResult = isMapResult ;
      }
      public String getIgnoreCategoryFields() {
         return ignoreCategoryFields;
      }
    
      public void setIgnoreCategoryFields(String ignoreCategoryFields) {
         this.ignoreCategoryFields = ignoreCategoryFields ;
      }
      public Boolean getIsOutGroupPriceResult() {
         return isOutGroupPriceResult;
      }
    
      public void setIsOutGroupPriceResult(Boolean isOutGroupPriceResult) {
         this.isOutGroupPriceResult = isOutGroupPriceResult ;
      }
      public Boolean getIsUseNewConsumeItem() {
         return isUseNewConsumeItem;
      }
    
      public void setIsUseNewConsumeItem(Boolean isUseNewConsumeItem) {
         this.isUseNewConsumeItem = isUseNewConsumeItem ;
      }
}


