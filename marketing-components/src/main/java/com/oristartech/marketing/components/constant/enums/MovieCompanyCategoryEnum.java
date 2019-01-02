package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 影片 使用CompanyCategoryEnum 的部分类型
 * @author: zhangdongdong
 * @createDate: 2018/10/17 下午6:08
 * @Version: 1.0
 */
public enum MovieCompanyCategoryEnum {
    PRODUCED("PRODUCED","出品公司"),
    PRODUCTION("PRODUCTION","制片公司"),
    ISSUED("ISSUED","发行公司"),
    PROPAGANDA("PROPAGANDA","宣传公司");

    private String code;
    private String desc;

    MovieCompanyCategoryEnum(String code, String desc) {
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
