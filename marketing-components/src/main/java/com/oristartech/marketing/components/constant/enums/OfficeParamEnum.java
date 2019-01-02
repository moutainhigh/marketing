package com.oristartech.marketing.components.constant.enums;

/**
 * com.tthappy.m.core.constant.enums
 *
 * @author fanzixian
 * @createDate 2018/11/9
 */

/**
 * 票房分析指标相关枚举
 */
public class OfficeParamEnum {

    public enum ParamTagEnum {
        PARAM_BASIC_INFO("PARAM_BASIC_INFO", "基础信息"),
        PARAM_BOX_OFFICE("PARAM_BOX_OFFICE", "票房分析"),
        PARAM_PROFIT("PARAM_PROFIT", "收益分析");
        private String code;
        private String desc;

        ParamTagEnum(String code, String desc) {
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


    public enum ModelCodeEnum {
        MODEL_MOVIE("MODEL_MOVIE", "影片票房"),
        MODEL_MOVIE_TIME("MODEL_MOVIE_TIME", "单影片票房-按时间"),
        MODEL_MOVIE_DISTRICT("MODEL_MOVIE_DISTRICT", "单影片票房-按区域"),
        MODEL_MOVIE_COMPANY("MODEL_MOVIE_COMPANY", "单影片票房-按公司"),
        MODEL_CINEMA("MODEL_CINEMA", "影院票房"),
        MODEL_CINEMA_TIME("MODEL_CINEMA_TIME", "单影院票房-按时间"),
        MODEL_CINEMA_DISTRICT("MODEL_CINEMA_DISTRICT", "单影院票房-按区域"),
        MODEL_CINEMA_HALL("MODEL_CINEMA_HALL", "单影院票房-按影厅"),
        MODEL_CHAIN("MODEL_CHAIN","院线票房"),
        MODEL_CHAIN_TIME("MODEL_CHAIN_TIME","单院线票房-按时间"),
        MODEL_CHAIN_MOVIE("MODEL_CHAIN_MOVIE","单院线票房-按影片"),
        MODEL_MANAGE("MODEL_MANAGE","影投票房"),
        MODEL_MANAGE_TIME("MODEL_MANAGE_TIME","单影投票房-按时间"),
        MODEL_MANAGE_MOVIE("MODEL_MANAGE_MOVIE","单影投票房-按影片"),
        ;
        private String code;
        private String desc;

        ModelCodeEnum(String code, String desc) {
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
