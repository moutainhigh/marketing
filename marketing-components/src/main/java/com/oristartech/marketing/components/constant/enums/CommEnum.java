package com.oristartech.marketing.components.constant.enums;

/**
 * com.tthappy.m.core.constant.enums
 * fanzixian
 * 2018/9/4
 */
public enum  CommEnum {
    DESC("desc","降序排序"),
    ASC("asc","升序排列")
    ;
    private String code;
    private String desc;
    CommEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }
    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

}
