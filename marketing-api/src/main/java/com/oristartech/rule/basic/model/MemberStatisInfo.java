package com.oristartech.rule.basic.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal; 
import java.util.Date; 

/**
 *  会员统计信息
 *  "_" 开头的属性是内部使用的属性.
 */
public  class MemberStatisInfo  implements Serializable {
       /**
        * 类型标识, 方便转为json加上类型标识
        */
       private String _type = MemberStatisInfo.class.getSimpleName();
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
        * 动态周期消费次数 
        * 动态周期消费次数
        */ 
       private Integer dynamicConsumeCount;
       /**
        * 动态周期充值次数 
        * 
        */ 
       private Integer dynamicChargeCount;
       /**
        * 动态周期消费额 
        * 
        */ 
       private BigDecimal dynamicConsumeSum;
       /**
        * 动态周期充值额 
        * 
        */ 
       private BigDecimal dynamicChargeSum;
       /**
        * 动态周期 
        * 
        */ 
       private String dynamicPeriod;
       /**
        * 指定周期 
        * 若动态周期的选择是指定周期, 则本属性必填
        */ 
       private Date appointPeriod;
       /**
        * 交易渠道 
        * 会员业务中的消费渠道
        */ 
       private String consumeWayCode;
       /**
        * 商品类型 
        * 会员支付消费明细中的商品类型
        */ 
       private String saleItemType;
       /**
        * 距离当天前N月 
        * 若动态周期的选择是前n月, 则本属性必填
        */ 
       private Integer nMonthBefore;
    
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
      public Integer getDynamicConsumeCount() {
         return dynamicConsumeCount;
      }
    
      public void setDynamicConsumeCount(Integer dynamicConsumeCount) {
         this.dynamicConsumeCount = dynamicConsumeCount ;
      }
      public Integer getDynamicChargeCount() {
         return dynamicChargeCount;
      }
    
      public void setDynamicChargeCount(Integer dynamicChargeCount) {
         this.dynamicChargeCount = dynamicChargeCount ;
      }
      public BigDecimal getDynamicConsumeSum() {
         return dynamicConsumeSum;
      }
    
      public void setDynamicConsumeSum(BigDecimal dynamicConsumeSum) {
         this.dynamicConsumeSum = dynamicConsumeSum ;
      }
      public BigDecimal getDynamicChargeSum() {
         return dynamicChargeSum;
      }
    
      public void setDynamicChargeSum(BigDecimal dynamicChargeSum) {
         this.dynamicChargeSum = dynamicChargeSum ;
      }
      public String getDynamicPeriod() {
         return dynamicPeriod;
      }
    
      public void setDynamicPeriod(String dynamicPeriod) {
         this.dynamicPeriod = dynamicPeriod ;
      }
      public Date getAppointPeriod() {
         return appointPeriod;
      }
    
      public void setAppointPeriod(Date appointPeriod) {
         this.appointPeriod = appointPeriod ;
      }
      public String getConsumeWayCode() {
         return consumeWayCode;
      }
    
      public void setConsumeWayCode(String consumeWayCode) {
         this.consumeWayCode = consumeWayCode ;
      }
      public String getSaleItemType() {
         return saleItemType;
      }
    
      public void setSaleItemType(String saleItemType) {
         this.saleItemType = saleItemType ;
      }
      public Integer getNMonthBefore() {
         return nMonthBefore;
      }
    
      public void setNMonthBefore(Integer nMonthBefore) {
         this.nMonthBefore = nMonthBefore ;
      }
}


