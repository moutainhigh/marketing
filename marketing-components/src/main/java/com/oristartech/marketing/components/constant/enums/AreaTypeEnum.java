package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 区域类型
 * @author: zhangdongdong
 * @createDate: 2018/11/13 1:45 PM
 * @Version: 1.0
 */
public enum AreaTypeEnum {
    PROVINCE("PROVINCE","省市区"),
    REGION("REGION","大区"),
    LEVEL("LEVEL","城市等级");

    private String code;
    private String desc;

    AreaTypeEnum(String code,String desc) {
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
