package com.oristartech.rule.basic.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore; 

/**
 *  基础信息
 *  "_" 开头的属性是内部使用的属性.
 */
public  class CommonInfo  implements Serializable {
       /**
        * 类型标识, 方便转为json加上类型标识
        */
       private String _type = CommonInfo.class.getSimpleName();
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
        * 有效日期 
        * 
        */ 
       private Date validDate = new Date();
       /**
        * 有效星期 
        * 
        */ 
       private Date validWeek = new Date();
       /**
        * 有效时段 
        * 
        */ 
       private Date validTime = new Date();
       /**
        * 节假日 
        * 节假日
        */ 
       private Boolean holiday;
       /**
        * 指定排除日期范围 
        * 指定排除日期范围的排除
        */ 
       private Date appointInvalidDate = new Date();
       /**
        * 当前日期 
        * 
        */ 
       private Date currentDate = new Date();
    
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
      public Date getValidDate() {
         return validDate;
      }
    
      public void setValidDate(Date validDate) {
         this.validDate = validDate ;
      }
      public Date getValidWeek() {
         return validWeek;
      }
    
      public void setValidWeek(Date validWeek) {
         this.validWeek = validWeek ;
      }
      public Date getValidTime() {
         return validTime;
      }
    
      public void setValidTime(Date validTime) {
         this.validTime = validTime ;
      }
      public Boolean getHoliday() {
         return holiday;
      }
    
      public void setHoliday(Boolean holiday) {
         this.holiday = holiday ;
      }
      public Date getAppointInvalidDate() {
         return appointInvalidDate;
      }
    
      public void setAppointInvalidDate(Date appointInvalidDate) {
         this.appointInvalidDate = appointInvalidDate ;
      }
      public Date getCurrentDate() {
         return currentDate;
      }
    
      public void setCurrentDate(Date currentDate) {
         this.currentDate = currentDate ;
      }
}


