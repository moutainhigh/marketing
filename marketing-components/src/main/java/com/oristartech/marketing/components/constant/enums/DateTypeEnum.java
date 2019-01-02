package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 时间选择 类型
 * @author: zhangdongdong
 * @createDate: 2018/11/13 1:45 PM
 * @Version: 1.0
 */
public enum DateTypeEnum {
    DAY("DAY","天"),
    WEEK("WEEK","周"),
    MONTH("MONTH","月"),
    YEAR("YEAR","年"),
    USER_DEFINED("USER_DEFINED","自定义");

    private String code;
    private String desc;

    DateTypeEnum(String code, String desc) {
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
