package com.oristartech.marketing.components.constant.enums;
/**
 *
 * 功能描述:影投指标概览枚举
 * @return:
 * @auther: Jifei•Xia
 * @date: 2018/11/16 6:13 PM
 */
public enum CompanyIndexEnum {
    BOXOFFICE("boxOffice","票房（万）"),
    SHOWCOUNT("showCount","场次"),
    AUDIENCECOUNT("audienceCount","人次（万）"),
    AVGPRICE("avgPrice","平均票价"),
    AVGSESSIONAUDIENCECOUNT("avgSessionAudienceCount","场均人次"),
    AVGSEATVIEWRATE("avgSeatViewRate","上座率"),
    AVGSESSIONBOXOFFICE("avgSessionBoxOffice","场均收入"),
    AVGDAYSEATBOXOFFICE("avgDaySeatBoxOffice","单日单座收益"),
    AVGDAYHALLBOXOFFICE("avgDayHallBoxOffice","单日单厅收益"),
    AVGDAYSHOWCOUNT("avgDayShowCount","单日单厅场次")
    ;
    private String code;
    private String desc;
    CompanyIndexEnum(String code, String desc){
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
