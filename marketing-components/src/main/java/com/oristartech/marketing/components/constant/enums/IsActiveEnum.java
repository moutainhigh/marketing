package com.oristartech.marketing.components.constant.enums;

/**
 * @description:
 * @author: zhangdongdong
 * @createDate: 2018/9/19 下午7:47
 * @Version: 1.0
 */
public enum IsActiveEnum {
    YES(1,"是"),
    NO(0,"否");

    private Integer code;
    private String desc;

    IsActiveEnum(Integer code, String desc) {
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
