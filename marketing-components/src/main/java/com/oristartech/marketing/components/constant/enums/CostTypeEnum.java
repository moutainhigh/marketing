package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 影片传成本类型
 * @author: zhangdongdong
 * @createDate: 2018/9/17 上午11:25
 * @Version: 1.0
 */
public enum CostTypeEnum {
    SMALL("small","小成本"),
    SMALL_MEDIUM("smallMedium","中小成本"),
    MEDIUM("medium","中成本"),
    MEDIUM_HIGH("mediumHigh","中高成本"),
    HIGH("high","高成本"),
    SUPER_HIGH("superHigh","超高成本");

    private String code;
    private String desc;

    CostTypeEnum(String code, String desc) {
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
