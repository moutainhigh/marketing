package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 影片语言
 * @author: zhangdongdong
 * @createDate: 2018/9/26 上午11:28
 * @Version: 1.0
 */
public enum LanguageEnum {
    MANDARIN("mandarin", "国语"),
    CANTONESE("cantonese", "粤语"),
    ENGLISH("english", "英语"),
    FRENCH("french", "法语"),
    JAPANESE("japanese", "日语"),
    THAI("thai", "泰语"),
    GERMAN("german", "德语"),
    KOREAN("korean", "韩语");

    private String code;
    private String desc;

    LanguageEnum(String code,String desc) {
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
