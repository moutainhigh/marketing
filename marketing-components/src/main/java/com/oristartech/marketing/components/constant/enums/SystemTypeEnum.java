package com.oristartech.marketing.components.constant.enums;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * 系统标示（菜单）
 * @Author MackCC.Sun
 * @Date 2018-11-13
 **/
public enum SystemTypeEnum {

    M("M", "M后台"),
    BI("BI", "BI系统");

    private String code;
    private String desc;

    SystemTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static final ConcurrentHashMap<String, String> DICTIONARY = new ConcurrentHashMap<>();

    static {
        for (SystemTypeEnum e : SystemTypeEnum.values()) {
            DICTIONARY.put(e.getCode(), e.getDesc());
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
