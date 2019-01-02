package com.oristartech.marketing.components.constant.enums;

/**
 * @description:
 * @author: zhangdongdong
 * @createDate: 2018/9/12 下午7:52
 * @Version: 1.0
 */
public enum ActorOrderFieldEnum  {

    BOX_OFFICE_TOTAL("box_office_total", "票房");

    private String code;
    private String desc;

    ActorOrderFieldEnum(String code,String desc) {
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
