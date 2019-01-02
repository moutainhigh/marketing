package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 职业类型
 * @author: zhangdongdong
 * @createDate: 2018/9/3 上午10:25
 * @Version: 1.0
 */
public enum CategoryEnum {

    ACTOR("actor", "演员"),
    DIRECTOR("director", "导演"),
    SCREENWRITER("screenwriter", "编剧"),
    PRODUCER("producer", "制片"),
    EXECUTIVE_PRODUCER("executiveProducer", "监制"),
    FILMING_DIRECTOR("filmingDirector", "摄影指导"),
    FILM_EDITING("filmEditing", "剪辑"),
    AR_DIRECTOR("artDirector", "美术指导"),
    RECORDING("recording", "录音"),
    ACTION_GUIDANCE("actionGuidance", "动作指导"),
    DUBBING("dubbing", "配音"),
    PHOTOGRAPHER("photographer", "摄影师"),
    COMPOSER("composer", "作曲"),
    SET_DESIGNER("setDesigner", "布景师"),
    COSTUME_DESIGNER("costumeDesigner", "服装设计"),
    ;

    private String code;
    private String desc;

    CategoryEnum(String code,String desc) {
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
