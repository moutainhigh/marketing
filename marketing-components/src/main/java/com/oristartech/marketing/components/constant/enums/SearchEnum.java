package com.oristartech.marketing.components.constant.enums;

/**
 * @Auther: hexu
 * @Date: 2018/10/10 16:26
 * @Description:
 */
public enum SearchEnum {

    CINEMA("CINEMA", "影院"),
    ACTOR("ACTOR", "影人"),
    MOVIE("MOVIE", "影片"),
    CINEMA_CHAIN("COMPANY_CINEMA_CHAIN", "院线公司"),
    MOVIE_MANAGE("COMPANY_MOVIE_MANAGE", "影投公司"),
    PRODUCED("COMPANY_PRODUCED", "出品公司"),
    PRODUCTION("COMPANY_PRODUCTION", "制片公司"),
    ISSUED("COMPANY_ISSUED", "发行公司"),
    PROPAGANDA("COMPANY_PROPAGANDA", "宣传公司"),
    MERCHANT("MERCHANT","商户");

    private String code;
    private String desc;

    SearchEnum(String code, String desc) {
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
