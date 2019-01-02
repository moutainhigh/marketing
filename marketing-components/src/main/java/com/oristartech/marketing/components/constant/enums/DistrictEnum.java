package com.oristartech.marketing.components.constant.enums;

/**
 * com.tthappy.m.core.constant.enums
 * fanzixian
 * 2018/11/8
 */

/**
 * 省市县枚举
 */
public class DistrictEnum {

    public enum LvEnum {

        PROVINCE("1", "省"),
        CITY("2", "市"),
        COUNTY("3", "县");
        private String code;
        private String desc;

        LvEnum(String code, String desc) {
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

    public enum LocationLvEnum {

        LOCATION_ONE("LOCATION_ONE", "一线城市"),
        LOCATION_TWO("LOCATION_TWO", "二级城市"),
        LOCATION_THREE("LOCATION_THREE", "三级城市"),
        LOCATION_FOUR("LOCATION_FOUR", "四级城市"),
        LOCATION_FIVE("LOCATION_FIVE", "五级城市");
        private String code;
        private String desc;

        LocationLvEnum(String code, String desc) {
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


    public enum RegionCodeEnum{
        NORTHEAST("REGION_NORTHEAST","东北地区"),
        NORTH("REGION_NORTH","华北地区"),
        EAST("REGION_EAST","华东地区"),
        SOUTH("REGION_SOUTH","华南地区"),
        SOUTH_CENTRAL("REGION_SOUTH_CENTRAL","华中地区"),
        NORTHWEST("REGION_NORTHWEST","西北地区"),
        SOUTHWEST("REGION_SOUTHWEST","西南地区")
        ;
        private String code;
        private String desc;

        RegionCodeEnum(String code, String desc) {
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
}
