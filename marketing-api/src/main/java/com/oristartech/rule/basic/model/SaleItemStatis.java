package com.oristartech.rule.basic.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal; 

/**
 *  商品统计信息
 *  "_" 开头的属性是内部使用的属性.
 */
public  class SaleItemStatis  implements Serializable {
       /**
        * 类型标识, 方便转为json加上类型标识
        */
       private String _type = SaleItemStatis.class.getSimpleName();
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
        * 商品类别 
        * 商品统计类中的类别编码
        */ 
       private String classCode;
       /**
        * 商品品牌 
        * 商品统计类中的品牌
        */ 
       private String brandId;
       /**
        * 供应商 
        * 商品统计类中的供应商
        */ 
       private String supCode;
       /**
        * 累计购买数量 
        * 
        */ 
       private BigDecimal sumAmount;
       /**
        * 累计购买金额 
        * 
        */ 
       private BigDecimal sumPrice;
    
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
      public String getClassCode() {
         return classCode;
      }
    
      public void setClassCode(String classCode) {
         this.classCode = classCode ;
      }
      public String getBrandId() {
         return brandId;
      }
    
      public void setBrandId(String brandId) {
         this.brandId = brandId ;
      }
      public String getSupCode() {
         return supCode;
      }
    
      public void setSupCode(String supCode) {
         this.supCode = supCode ;
      }
      public BigDecimal getSumAmount() {
         return sumAmount;
      }
    
      public void setSumAmount(BigDecimal sumAmount) {
         this.sumAmount = sumAmount ;
      }
      public BigDecimal getSumPrice() {
         return sumPrice;
      }
    
      public void setSumPrice(BigDecimal sumPrice) {
         this.sumPrice = sumPrice ;
      }
}


