package com.oristartech.marketing.components.constant.enums;

import java.util.Hashtable;


/**
 * @Description
 * token的生成的类型来源标识别
 * @Author Mackcc.Sun
 * @Date 2018-09-27
 **/
public enum AuthTokenTypeEnum {

    ADMIN("M-ADMIN", "M后台标识"),
    App("M-APP", "管家APP")
    ;

    private String code;
    private String desc;

    AuthTokenTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static final Hashtable<String, String> DICTIONARY = new Hashtable<>();

    static {
        for (AuthTokenTypeEnum userEnum : AuthTokenTypeEnum.values()) {
            DICTIONARY.put(userEnum.getCode(), userEnum.getDesc());
        }
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

    public static String getDescByCode(String code){
        if (code == null || code.equals("")) {
            return "";
        }
        for (AuthTokenTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value.getDesc();
            }
        }
        return "";
    }


}
