package com.oristartech.marketing.components.constant.enums;

import java.util.Hashtable;

/**
 *商户相关枚举类型
 */
public enum MerchantEnum {

    INIT_PASSWORD("123456", "商户初始化密码"),
    START_MERCHANT("1","启用商户"),
    STOP_MERCHANT("2","停用商户"),
    DEL_MERCHANT_CINEMA("0","删除商户影院"),
    START_MERCHANT_CINEMA("1","启动商户影院"),
    STOP_MERCHANT_CINEMA("2","冻结商户影院"),
    SEND_SMS_RATE("60","发送短信频率"),
    MAX_UPLOAD_SIZE("52428800","附件最大限制");


    private String code;
    private String desc;

    MerchantEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
