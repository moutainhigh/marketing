package com.oristartech.rule.basic.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *  第三方票券
 *  "_" 开头的属性是内部使用的属性.
 */
public  class ThirdPartyCouponInfo  implements Serializable {
       /**
        * 类型标识, 方便转为json加上类型标识
        */
       private String _type = ThirdPartyCouponInfo.class.getSimpleName();
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
        * 团购网及合作商票券 
        * 第三方票券类型
        */ 
       private String thirdPartyCouponKey;
    
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
      public String getThirdPartyCouponKey() {
         return thirdPartyCouponKey;
      }
    
      public void setThirdPartyCouponKey(String thirdPartyCouponKey) {
         this.thirdPartyCouponKey = thirdPartyCouponKey ;
      }
}


