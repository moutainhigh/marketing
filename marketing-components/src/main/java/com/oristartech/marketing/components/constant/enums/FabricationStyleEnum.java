package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 影片制式
 * @author: zhangdongdong
 * @createDate: 2018/9/17 下午5:59
 * @Version: 1.0
 */
public enum FabricationStyleEnum {

    TYPE_2D("2D","2D"),
    TYPE_3D("3D","3D"),
    IMAX_2D("IMAX(2D)","IMAX(2D)"),
    IMAX_3D("IMAX(3D)","IMAX(3D)"),
    CGS_2D("CGS_2D","中国巨幕(2D)"),
    CGS_3D("CGS_3D","中国巨幕(3D)"),
    ;

    private String code;
    private String desc;

    FabricationStyleEnum(String code, String desc) {
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
