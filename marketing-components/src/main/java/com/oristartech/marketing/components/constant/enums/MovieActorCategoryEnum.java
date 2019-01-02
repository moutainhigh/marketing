package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 演职人员类型
 * @author: zhangdongdong
 * @createDate: 2018/9/3 上午10:25
 * @Version: 1.0
 */
public enum MovieActorCategoryEnum {

    DIRECTOR("director", "导演"),
    STARRING("starring", "主演"),
    ACTOR("actor", "参演"),
    SCREENWRITER("screenwriter", "编剧"),
    PRODUCER("producer", "制片"),
    EXECUTIVE_PRODUCER("executiveProducer", "监制"),
    DUBBING("dubbing", "配音"),

    PUBLISHER("publisher", "出品人"),
    PHOTOGRAPHER("photographer", "摄影师"),
    FILM_EDITING("filmEditing", "剪辑师"),

    MAKEUP_ARTIST("makeupArtist", "化妆师"),
    FILMING_DIRECTOR("filmingDirector", "摄影指导"),
    CLIP_GUIDE("clipGuide", "剪辑指导"),

    AR_DIRECTOR("artDirector", "美术指导"),
    ACTION_GUIDANCE("actionGuidance", "动作指导"),
    PRODUCTION_DESIGNER("productionDesigner", "艺术指导"),

    COSTUME_DESIGNER("costumeDesigner", "服装设计"),
    RECORDING("recording", "录音"),
    COMPOSER("composer", "作曲"),
    VISUAL_EFFECTS("visualEffects", "视觉特效"),
    ASSISTANT_DIRECTOR("assistantDirector", "副导演"),
    SET_DESIGNER("setDesigner", "布景师"),
    ;

    private String code;
    private String desc;

    MovieActorCategoryEnum(String code, String desc) {
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
