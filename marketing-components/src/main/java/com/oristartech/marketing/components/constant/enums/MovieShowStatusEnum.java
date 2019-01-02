package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 影片上映状态
 * @author: zhangdongdong
 * @createDate: 2018/9/20 下午7:23
 * @Version: 1.0
 */
public enum MovieShowStatusEnum {

    RELEASED("released", "已上映"),
    NOT_RELEASED("notReleased", "未上映");

    private String code;
    private String desc;

    MovieShowStatusEnum(String code,String desc) {
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
