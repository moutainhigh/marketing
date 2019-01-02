package com.oristartech.marketing.components.constant.enums;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;


public enum RoleEnum {

    ACTIVE("ACTIVE", "启用"),
    FREEZE("FREEZE", "停用");

    private String code;
    private String desc;

    RoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static final ConcurrentHashMap<String, String> DICTIONARY = new ConcurrentHashMap<>();

    static {
        for (RoleEnum userEnum : RoleEnum.values()) {
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

        return DICTIONARY.get(code);
    }


}
