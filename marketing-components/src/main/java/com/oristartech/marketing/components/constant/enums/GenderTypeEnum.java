package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 性别
 * @author: zhangdongdong
 * @createDate: 2018/9/17 下午4:31
 * @Version: 1.0
 */
public enum GenderTypeEnum {
    MALE("male", "男"),
    FEMALE("female", "女");

    private String code;
    private String desc;

    GenderTypeEnum(String code,String desc) {
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
