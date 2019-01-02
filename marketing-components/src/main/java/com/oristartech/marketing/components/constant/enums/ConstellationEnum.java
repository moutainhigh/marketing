package com.oristartech.marketing.components.constant.enums;


/**
 * @description:
 * @author: zhangdongdong
 * @createDate: 2018/9/3 上午11:57
 * @Version: 1.0
 */
public enum ConstellationEnum {

    Aries("Aries", "白羊座"),
    Taurus("Taurus", "金牛座"),
    Gemini("Gemini", "双子座"),
    Cancer("Cancer", "巨蟹座"),
    Leo("Leo", "狮子座"),
    Virgo("Virgo", "处女座"),
    Libra("Libra", "天秤座"),
    Scorpio("Scorpio", "天蝎座"),
    Sagittarius("Sagittarius", "射手座"),
    Capricorn("Capricorn", "摩羯座"),
    Aquarius("Aquarius", "水瓶座"),
    Pisces("Pisces", "双鱼座");

    private String code;
    private String desc;

    ConstellationEnum(String code, String desc) {
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

    public static void main(String[] args) {
       for(MovieNatureEnum constellationEnum: MovieNatureEnum.values()){
           System.out.print(constellationEnum.getDesc()+":"+constellationEnum.getCode()+",");
       }
    }
}
