package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 图片 影像
 * @author: zhangdongdong
 * @createDate: 2018/9/3 上午11:12
 * @Version: 1.0
 */
public enum FileTypeEnum {

    PICTURE("picture","图片"),
    POSTER("poster","海报"),
    VIDEO("video","影像");

    private String code;
    private String desc;

    FileTypeEnum(String code, String desc) {
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
