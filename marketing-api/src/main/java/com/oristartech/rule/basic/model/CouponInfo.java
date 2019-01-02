package com.oristartech.rule.basic.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal; 
import java.util.Date; 

/**
 *  票券
 *  "_" 开头的属性是内部使用的属性.
 */
public  class CouponInfo  implements Serializable {
       /**
        * 类型标识, 方便转为json加上类型标识
        */
       private String _type = CouponInfo.class.getSimpleName();
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
        * 票券销售单价 
        * 
        */ 
       private BigDecimal price;
       /**
        * 销售单位 
        * 
        */ 
       private Long saleUnitId;
       /**
        * 客户 
        * 
        */ 
       private String customerCode;
       /**
        * 销售日期 
        * 
        */ 
       private Date saleDate;
       /**
        * 票券申请单号 
        * 
        */ 
       private String couponApplyCode;
       /**
        * 票券数量 
        * 
        */ 
       private BigDecimal amount;
       /**
        * 票券已使用商品类型 
        * 票券已使用类型
        */ 
       private String couponUsedItemType;
    
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
      public BigDecimal getPrice() {
         return price;
      }
    
      public void setPrice(BigDecimal price) {
         this.price = price ;
      }
      public Long getSaleUnitId() {
         return saleUnitId;
      }
    
      public void setSaleUnitId(Long saleUnitId) {
         this.saleUnitId = saleUnitId ;
      }
      public String getCustomerCode() {
         return customerCode;
      }
    
      public void setCustomerCode(String customerCode) {
         this.customerCode = customerCode ;
      }
      public Date getSaleDate() {
         return saleDate;
      }
    
      public void setSaleDate(Date saleDate) {
         this.saleDate = saleDate ;
      }
      public String getCouponApplyCode() {
         return couponApplyCode;
      }
    
      public void setCouponApplyCode(String couponApplyCode) {
         this.couponApplyCode = couponApplyCode ;
      }
      public BigDecimal getAmount() {
         return amount;
      }
    
      public void setAmount(BigDecimal amount) {
         this.amount = amount ;
      }
      public String getCouponUsedItemType() {
         return couponUsedItemType;
      }
    
      public void setCouponUsedItemType(String couponUsedItemType) {
         this.couponUsedItemType = couponUsedItemType ;
      }
}


