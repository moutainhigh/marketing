package com.oristartech.marketing.components.constant.enums;

/**
 * 公司相关 enum
 * com.tthappy.m.core.constant.enums
 * fanzixian
 * 2018/9/4
 */
public class CompanyEnum {


    /**
     * 公司类型enum
     */
    public enum CompanyCategoryEnum{
        CINEMA_CHAIN("CINEMA_CHAIN","院线公司"),
        MOVIE_MANAGE("MOVIE_MANAGE","影投公司"),
        PRODUCED("PRODUCED","出品公司"),
        PRODUCTION("PRODUCTION","制片公司"),
        ISSUED("ISSUED","发行公司"),
        PROPAGANDA("PROPAGANDA","宣传公司");
        private String code;
        private String desc;

        CompanyCategoryEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }
    }


    /**
     * 公司表相关表字段
     */
    public enum CompanyTableEnum{
        COMPANY_NAME("company_name","公司名称字段"),
        ANNUAL_BOX_OFFICE("annual_box_office","年度票房字段"),
        PARTI_BOX_OFFICE("parti_box_office","参与作品票房字段"),
        COMPANY_UNICODE("company_unicode","公司专资码"),
        IS_DEL("is_del","是否删除字段"),
        ;
        private String code;
        private String desc;
        CompanyTableEnum(String code,String desc){
            this.code = code;
            this.desc = desc;
        }
        public String getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }
    }

    /**
     * 公司类型表相关表字段
     */
    public enum CompanyCategoryTableEnum{
        CATEGORY("category","公司类型"),
        COMPANY_ID("company_id","关联公司表ID"),
        IS_DEL("is_del","是否删除字段");
        private String code;
        private String desc;
        CompanyCategoryTableEnum(String code,String desc){
            this.code = code;
            this.desc = desc;
        }
        public String getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }
    }



}
