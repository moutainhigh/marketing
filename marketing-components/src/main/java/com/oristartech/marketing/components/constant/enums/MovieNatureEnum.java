package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 影片性质
 * @author: zhangdongdong
 * @createDate: 2018/9/4 下午2:14
 * @Version: 1.0
 */
public enum MovieNatureEnum {

    COOPERATION("cooperation", "合拍片"),
    DOMESTIC("domestic", "国产片"),
    IMPORTED("imported", "进口片");

    private String code;
    private String desc;

    MovieNatureEnum(String code,String desc) {
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
