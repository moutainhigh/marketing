package com.oristartech.rule.basic.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal; 
import java.util.Date; 

/**
 *  会员
 *  "_" 开头的属性是内部使用的属性.
 */
public  class MemberInfo  implements Serializable {
       /**
        * 类型标识, 方便转为json加上类型标识
        */
       private String _type = MemberInfo.class.getSimpleName();
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
        * 会员等级 
        * 
        */ 
       private Long cardTypeKey;
       /**
        * 账户余额 
        * 
        */ 
       private BigDecimal balance;
       /**
        * 积分余额 
        * 
        */ 
       private Integer integral;
       /**
        * 累计充值金额 
        * 
        */ 
       private BigDecimal chargeSum;
       /**
        * 累计消费金额 
        * 
        */ 
       private BigDecimal consumeSum;
       /**
        * 累计积分 
        * 
        */ 
       private Integer integralTotall;
       /**
        * 生日 
        * 
        */ 
       private Date birthday;
       /**
        * 性别 
        * 
        */ 
       private Integer sex;
       /**
        * 年龄 
        * 
        */ 
       private Integer age;
       /**
        * 开卡年限 
        * 
        */ 
       private Integer openYears;
       /**
        * 开卡日期 
        * 
        */ 
       private Date openDate;
       /**
        * 注册商户 
        * 
        */ 
       private String registerBusinessCode;
       /**
        * 会员卡号 
        * 
        */ 
       private String cardNum;
       /**
        * 手机号码 
        * 
        */ 
       private String mobileNum;
       /**
        * 会员标签 
        * 
        */ 
       private Long cardLabelId;
       /**
        * 开卡月限 
        * 
        */ 
       private Integer openMonths;
       /**
        * 首充金额
        */
       private BigDecimal firstRecharge;
    
       public BigDecimal getFirstRecharge() {
		return firstRecharge;
	}

	public void setFirstRecharge(BigDecimal firstRecharge) {
		this.firstRecharge = firstRecharge;
	}

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
      public Long getCardTypeKey() {
         return cardTypeKey;
      }
    
      public void setCardTypeKey(Long cardTypeKey) {
         this.cardTypeKey = cardTypeKey ;
      }
      public BigDecimal getBalance() {
         return balance;
      }
    
      public void setBalance(BigDecimal balance) {
         this.balance = balance ;
      }
      public Integer getIntegral() {
         return integral;
      }
    
      public void setIntegral(Integer integral) {
         this.integral = integral ;
      }
      public BigDecimal getChargeSum() {
         return chargeSum;
      }
    
      public void setChargeSum(BigDecimal chargeSum) {
         this.chargeSum = chargeSum ;
      }
      public BigDecimal getConsumeSum() {
         return consumeSum;
      }
    
      public void setConsumeSum(BigDecimal consumeSum) {
         this.consumeSum = consumeSum ;
      }
      public Integer getIntegralTotall() {
         return integralTotall;
      }
    
      public void setIntegralTotall(Integer integralTotall) {
         this.integralTotall = integralTotall ;
      }
      public Date getBirthday() {
         return birthday;
      }
    
      public void setBirthday(Date birthday) {
         this.birthday = birthday ;
      }
      public Integer getSex() {
         return sex;
      }
    
      public void setSex(Integer sex) {
         this.sex = sex ;
      }
      public Integer getAge() {
         return age;
      }
    
      public void setAge(Integer age) {
         this.age = age ;
      }
      public Integer getOpenYears() {
         return openYears;
      }
    
      public void setOpenYears(Integer openYears) {
         this.openYears = openYears ;
      }
      public Date getOpenDate() {
         return openDate;
      }
    
      public void setOpenDate(Date openDate) {
         this.openDate = openDate ;
      }
      public String getRegisterBusinessCode() {
         return registerBusinessCode;
      }
    
      public void setRegisterBusinessCode(String registerBusinessCode) {
         this.registerBusinessCode = registerBusinessCode ;
      }
      public String getCardNum() {
         return cardNum;
      }
    
      public void setCardNum(String cardNum) {
         this.cardNum = cardNum ;
      }
      public String getMobileNum() {
         return mobileNum;
      }
    
      public void setMobileNum(String mobileNum) {
         this.mobileNum = mobileNum ;
      }
      public Long getCardLabelId() {
         return cardLabelId;
      }
    
      public void setCardLabelId(Long cardLabelId) {
         this.cardLabelId = cardLabelId ;
      }
      public Integer getOpenMonths() {
         return openMonths;
      }
    
      public void setOpenMonths(Integer openMonths) {
         this.openMonths = openMonths ;
      }
}


