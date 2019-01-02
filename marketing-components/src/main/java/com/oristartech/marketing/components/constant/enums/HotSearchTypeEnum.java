package com.oristartech.marketing.components.constant.enums;

/**
 * @description:
 * @author: zhangdongdong
 * @createDate: 2018/9/27 上午10:13
 * @Version: 1.0
 */
public enum HotSearchTypeEnum {
    CINEMA("cinema", "影院"),
    MOVIE("movie", "影片"),
    COMPANY("company", "公司"),
    ACTOR("actor", "影人");

    private String code;
    private String desc;

    HotSearchTypeEnum(String code,String desc) {
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
