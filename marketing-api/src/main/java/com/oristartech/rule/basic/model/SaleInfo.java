package com.oristartech.rule.basic.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal; 

/**
 *  交易信息
 *  "_" 开头的属性是内部使用的属性.
 */
public  class SaleInfo  implements Serializable {
       /**
        * 类型标识, 方便转为json加上类型标识
        */
       private String _type = SaleInfo.class.getSimpleName();
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
        * 整单交易金额 
        * 整单应收金额
        */ 
       private BigDecimal sumPrice;
       /**
        * 交易渠道 
        * 交易过程中的交易渠道
        */ 
       private String consumeWayCode;
       /**
        * 影院编码 
        * 
        */ 
       private String cinemaCode;
       /**
        * 交易影院行政区域 
        * 
        */ 
       private String cinemaAreaId;
       /**
        * 交易类型 
        * 
        */ 
       private String tradeType;
       /**
        * 消费者身份 
        * 
        */ 
       private Long consumerTypeKey;
       /**
        * 交易商户 
        * 
        */ 
       private String businessCode;
       /**
        * 支付方式 
        * 
        */ 
       private String payTypeCode;
       /**
        * 影票类应收交易金额 
        * 只要包含影票类应收总额
        */ 
       private BigDecimal filmSumPrice;
       /**
        * 卖品类应收交易金额 
        * 只要包含卖品类应收总额
        */ 
       private BigDecimal merSumPrice;
       /**
        * 影票商品数量 
        * 只要包含影票票数
        */ 
       private Integer filmTicketAmount;
       /**
        * 会员等级code列表 
        * 
        */ 
       private String consumerTypes;
       /**
        * 需要匹配规则的执行模式 
        * 若空, 两个都匹配
        */ 
       private String ruleExecuteMode;
       
       /**
        * 首次购票
        */
       private Boolean firstBuyTicket;
    
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
      public BigDecimal getSumPrice() {
         return sumPrice;
      }
    
      public void setSumPrice(BigDecimal sumPrice) {
         this.sumPrice = sumPrice ;
      }
      public String getConsumeWayCode() {
         return consumeWayCode;
      }
    
      public void setConsumeWayCode(String consumeWayCode) {
         this.consumeWayCode = consumeWayCode ;
      }
      public String getCinemaCode() {
         return cinemaCode;
      }
    
      public void setCinemaCode(String cinemaCode) {
         this.cinemaCode = cinemaCode ;
      }
      public String getCinemaAreaId() {
         return cinemaAreaId;
      }
    
      public void setCinemaAreaId(String cinemaAreaId) {
         this.cinemaAreaId = cinemaAreaId ;
      }
      public String getTradeType() {
         return tradeType;
      }
    
      public void setTradeType(String tradeType) {
         this.tradeType = tradeType ;
      }
      public Long getConsumerTypeKey() {
         return consumerTypeKey;
      }
    
      public void setConsumerTypeKey(Long consumerTypeKey) {
         this.consumerTypeKey = consumerTypeKey ;
      }
      public String getBusinessCode() {
         return businessCode;
      }
    
      public void setBusinessCode(String businessCode) {
         this.businessCode = businessCode ;
      }
      public String getPayTypeCode() {
         return payTypeCode;
      }
    
      public void setPayTypeCode(String payTypeCode) {
         this.payTypeCode = payTypeCode ;
      }
      public BigDecimal getFilmSumPrice() {
         return filmSumPrice;
      }
    
      public void setFilmSumPrice(BigDecimal filmSumPrice) {
         this.filmSumPrice = filmSumPrice ;
      }
      public BigDecimal getMerSumPrice() {
         return merSumPrice;
      }
    
      public void setMerSumPrice(BigDecimal merSumPrice) {
         this.merSumPrice = merSumPrice ;
      }
      public Integer getFilmTicketAmount() {
         return filmTicketAmount;
      }
    
      public void setFilmTicketAmount(Integer filmTicketAmount) {
         this.filmTicketAmount = filmTicketAmount ;
      }
      public String getConsumerTypes() {
         return consumerTypes;
      }
    
      public void setConsumerTypes(String consumerTypes) {
         this.consumerTypes = consumerTypes ;
      }
      public String getRuleExecuteMode() {
         return ruleExecuteMode;
      }
    
      public void setRuleExecuteMode(String ruleExecuteMode) {
         this.ruleExecuteMode = ruleExecuteMode ;
      }

      public Boolean getFirstBuyTicket() {
		return firstBuyTicket;
      }

      public void setFirstBuyTicket(Boolean firstBuyTicket) {
		this.firstBuyTicket = firstBuyTicket;
      }
      
}


