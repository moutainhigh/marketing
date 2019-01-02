package com.oristartech.marketing.components.constant;

/**
 * @Description
 * 短信以及验证码过期时间
 * @Author MackCC.Sun
 * @Date 2018-10-16
 **/
public class CacheExpireTimeConstants {

    public static final Integer KAPTCHA_EXPIRE_TIME = 5*60;//second

    public static final Integer AUTH_EXPIRE_TIME = 5*60;//second

    public static final Integer M_SMS_EXPIRE_TIME = 5*60*1000;//millisecond

    public static final Integer GUANJIA_SMS_EXPIRE_TIME = 30*60*1000;//millisecond

    public static final Integer NOTICE_EXPIRE_TIME = 5*60*1000;//millisecond

    public static final Integer USERRECEIVENOTICESTATUSUPDATEFLAG_BASE_EXPIRE_TIME = 7*24*60*60;//second

    public static final Integer USERRECEIVENOTICENUM_BASE_EXPIRE_TIME = 60;//second
}
