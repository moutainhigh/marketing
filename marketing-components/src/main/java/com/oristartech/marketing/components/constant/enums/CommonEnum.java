package com.oristartech.marketing.components.constant.enums;

import java.util.Hashtable;


public enum CommonEnum {

    DELETE("1", "已删除"),
    NORMAL("0", "正常");

    private String code;
    private String desc;

    CommonEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static final Hashtable<String, String> DICTIONARY = new Hashtable<>();

    static {
        for (CommonEnum categoryEnum : CommonEnum.values()) {
            DICTIONARY.put(categoryEnum.getCode(), categoryEnum.getDesc());
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
}
