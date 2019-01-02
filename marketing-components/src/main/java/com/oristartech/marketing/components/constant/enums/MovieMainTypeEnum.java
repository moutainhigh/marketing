package com.oristartech.marketing.components.constant.enums;


/**
 * @description: 影片主类型
 * @author: zhangdongdong
 * @createDate: 2018/9/3 下午2:16
 * @Version: 1.0
 */
public enum MovieMainTypeEnum {

    AIQING("AIQING","爱情"),
    DONGHUA("DONGHUA","动画"),
    DONGZUO("DONGZUO","动作"),
    XIJU("XIJU","喜剧"),
    FANZUI("FANZUI","犯罪"),
    GUZHUANG("GUZHUANG","古装"),
    JILUPIAN("JILUPIAN","纪录片"),
    JUQING("JUQING","剧情"),

    KEHUAN("KEHUAN","科幻"),
    KONGBU("KONGBU","恐怖"),
    QIANGZHAN("QIANGZHAN","枪战"),
    QIHUAN("QIHUAN","奇幻"),

    JINGSONG("JINGSONG", "惊悚"),
    WANHUI("WANHUI", "晚会"),
    XUANYI("XUANYI", "悬疑"),
    YISHUPIAN("YISHUPIAN", "艺术片"),
    ZHANZHENG("ZHANZHENG", "战争"),
    ZAINAN("ZAINAN", "灾难")
    ;
    private String code;
    private String desc;

    MovieMainTypeEnum(String code, String desc) {
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
