package com.oristartech.marketing.components.constant.enums;

import java.util.Hashtable;

/**
 * @Description
 * redis key 前缀汇总
 * @Author Mackcc.Sun
 * @Date 2018-09-27
 **/
public enum RedisKeyPrefixEnum {

    LOGIN_KAPTCHA("loginkaptcha:", "M后台登陆验证码 redis key"),
    AUTH_TOKEN("token:", "M后台登陆token redis key"),
    BOOKMARK("m-bookmark:", "M后台收藏夹 redis key"),
    AUTH_TOKEN_EXPIRE_METHOD("M_AUTH_TOKEN_EXPIRE_METHOD:","token过期不同区分方式 redis key"),
    USERRECEIVENOTICESTATUSUPDATEFLAG("userReceiveNoticeStatusUpdateFlag:","用户接受消息更新标示 redis key"),
    USERRECEIVENOTICENUM("userReceiveNoticeNum:","用户接收消息缓存对象 redis key")
    ;

    private String code;
    private String desc;

    RedisKeyPrefixEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static final Hashtable<String, String> DICTIONARY = new Hashtable<>();

    static {
        for (RedisKeyPrefixEnum userEnum : RedisKeyPrefixEnum.values()) {
            DICTIONARY.put(userEnum.getCode(), userEnum.getDesc());
        }
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

    public static String getDescByCode(String code){
        if (code == null || code.equals("")) {
            return "";
        }
        for (RedisKeyPrefixEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value.getDesc();
            }
        }
        return "";
    }


}
