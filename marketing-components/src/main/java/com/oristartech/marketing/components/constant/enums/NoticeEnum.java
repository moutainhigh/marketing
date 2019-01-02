package com.oristartech.marketing.components.constant.enums;

import java.util.concurrent.ConcurrentHashMap;


public class NoticeEnum {


    public enum NoticeStatusEnum {

        ACTIVE("ACTIVE", "启用"),
        FREEZE("FREEZE", "停用");
        
        private String code;
        private String desc;

        NoticeStatusEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static final ConcurrentHashMap<String, String> DICTIONARY = new ConcurrentHashMap<>();

        static {
            for (NoticeStatusEnum e : NoticeStatusEnum.values()) {
                DICTIONARY.put(e.getCode(), e.getDesc());
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
            return DICTIONARY.get(code);
        }
    }

    public enum NoticeSendChannelEnum {

        ALL("ALL", "全部"),
        M("M", "M后台"),
        MERCHANT("MERCHANT", "商户中台"),
        POS("POS", "pos系统");

        private String code;
        private String desc;

        NoticeSendChannelEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static final ConcurrentHashMap<String, String> DICTIONARY = new ConcurrentHashMap<>();

        static {
            for (NoticeSendChannelEnum e : NoticeSendChannelEnum.values()) {
                DICTIONARY.put(e.getCode(), e.getDesc());
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
            return DICTIONARY.get(code);
        }


    }


    public enum NoticeSendModelEnum {

        IMMEDIATELY("IMMEDIATELY", "即时发送"),
        TIMING("TIMING", "定时发送")
        ;

        private String code;
        private String desc;

        NoticeSendModelEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static final ConcurrentHashMap<String, String> DICTIONARY = new ConcurrentHashMap<>();

        static {
            for (NoticeSendModelEnum e : NoticeSendModelEnum.values()) {
                DICTIONARY.put(e.getCode(), e.getDesc());
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
            return DICTIONARY.get(code);
        }


    }

    public enum NoticeSendStatusEnum {

        DRAFT("DRAFT", "存草稿"),
        SEND("SEND", "已发送"),
        REVOKE("REVOKE", "已撤回");

        private String code;
        private String desc;

        NoticeSendStatusEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static final ConcurrentHashMap<String, String> DICTIONARY = new ConcurrentHashMap<>();

        static {
            for (NoticeSendStatusEnum e : NoticeSendStatusEnum.values()) {
                DICTIONARY.put(e.getCode(), e.getDesc());
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
            return DICTIONARY.get(code);
        }


    }

    public enum UserNoticeStatusEnum {

        ACTIVE("RECEIVE", "未读取"),
        FREEZE("READ", "已读取");

        private String code;
        private String desc;

        UserNoticeStatusEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static final ConcurrentHashMap<String, String> DICTIONARY = new ConcurrentHashMap<>();

        static {
            for (UserNoticeStatusEnum e : UserNoticeStatusEnum.values()) {
                DICTIONARY.put(e.getCode(), e.getDesc());
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
            for (UserNoticeStatusEnum value : values()) {
                if (value.getCode().equals(code)) {
                    return value.getDesc();
                }
            }
            return "";
        }


    }

}
