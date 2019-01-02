package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 票房是否包含服务
 * @author: zhangdongdong
 * @createDate: 2018/11/13 1:56 PM
 * @Version: 1.0
 */
public enum ServiceFeeTypeEnum {
    YES(1,"是"),
    NO(0,"否");

    private Integer code;
    private String desc;

    ServiceFeeTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
