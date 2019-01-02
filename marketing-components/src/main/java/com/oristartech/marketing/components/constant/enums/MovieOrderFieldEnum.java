package com.oristartech.marketing.components.constant.enums;


/**
 * @description:
 * @author: zhangdongdong
 * @createDate: 2018/9/12 下午7:53
 * @Version: 1.0
 */
public enum MovieOrderFieldEnum  {
    BOX_OFFICE_TOTAL("box_office_total", "票房"),
    SHOW_DATE("show_date", "上映日期"),
    ATTENDANCE_RATE("attendance_rate", "上座率");

    private String code;
    private String desc;

    MovieOrderFieldEnum(String code,String desc) {
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
