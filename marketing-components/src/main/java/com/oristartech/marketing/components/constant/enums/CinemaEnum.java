package com.oristartech.marketing.components.constant.enums;

/**
 * 影院相关枚举
 * com.tthappy.m.core.constant.enums
 * 2018/9/4
 *
 * @author fanzixian
 */
public class CinemaEnum {


    /**
     * 联系人职务
     */

    public enum ContractPost{
        CINEMA_MANAGER("MANAGER","影院经理"),
        FINANCE("FINANCE","财务联系人"),
        DEVICE("DEVICE","设备联系人"),
        SELL("SELL","卖品联系人"),
        AD("AD","广告联系人")
        ;
        private String code;
        private String desc;

        ContractPost(String code, String desc) {
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
     * 服务器分辨率
     */

    public enum ServiceScreen {

        SCREEN_2K("2K", "2K"),
        SCREEN_4K("4K", "4K");
        private String code;
        private String desc;

        ServiceScreen(String code, String desc) {
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
     * 服务器品牌
     */
    public enum ServiceBrand {
        DOLB("DOLB", "DOLB杜比"),
        SONY("SONY", "SONY"),
        GDC("GDC", "GDC"),
        DOREMI("DOREMI", "DOREMI"),
        ZHICHEN("ZHICHEN", "智辰"),
        BAKE("BAKE", "巴可灵锐睿"),
        ;
        private String code;
        private String desc;

        ServiceBrand(String code, String desc) {
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
     * 放映机品牌
     */
    public enum ProjectorBrand {
        BARCO("BARCO", "BARCO"),
        SONY("SONY", "SONY"),
        NEC("NEC", "NEC"),
        CHRISTIE("CHRISTIE", "CHRISTIE");
        private String code;
        private String desc;

        ProjectorBrand(String code, String desc) {
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
     * 广告位可展示类型
     * 视频、电子图片、实物展板
     */
    public enum AdDisplayType {
        VIDEO("VIDEO", "视频"),
        IMAGE("IMAGE", "电子图片"),
        MATTER("MATTER", "实物展板");
        private String code;
        private String desc;

        AdDisplayType(String code, String desc) {
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
     * 广告位设备类型
     * LED屏、大银幕、其他
     */
    public enum AdDeviceType {
        LED("LED", "LED屏"),
        BIG_SCREEN("BIG_SCREEN", "大银幕"),
        OTHER("OTHER", "其他");
        private String code;
        private String desc;

        AdDeviceType(String code, String desc) {
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
     * 广告位类型
     * <p>
     * 静态、动态
     */
    public enum AdType {
        STATIC_STATE("STATIC_STATE", "静态"),
        DYNAMI_STATE("DYNAMI_STATE", "动态");
        private String code;
        private String desc;

        AdType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }


        @Override
        public String toString() {
            return "AdType{" +
                    "code='" + code + '\'' +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }

    /**
     * 服务商服务类型
     * 卖品服务、设备服务、软件服务、场景服务
     */
    public enum ServiceType {
        SALER_SERVICE("SALER_SERVICE", "卖品服务"),
        DEVICE_SERVICE("DEVICE_SERVICE", "设备服务"),
        SOFT_SERVICE("SOFT_SERVICE", "软件服务"),
        SCENE_SERVICE("SCENE_SERVICE", "场景服务");
        private String code;
        private String desc;

        ServiceType(String code, String desc) {
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
     * 售票系统
     * 粤科凤凰佳影影院资讯管理软件、沃思达电影院软件、火烈鸟售票系统、满天星影院综合管理系统、中鑫汇科票务系统、鼎新影院计算机售票管理系统、1905影院票务系统
     */
    public enum SaleSystem {
        YKFH("YKFH", "粤科凤凰佳影影院资讯管理软件"),
        WSD("WSD", "沃思达电影院软件"),
        HLN("HLN", "火烈鸟售票系统"),
        MTX("MTX", "满天星影院综合管理系统"),
        ZXHK("ZXHK", "中鑫汇科票务系统"),
        DXYY("DXYY", "鼎新影院计算机售票管理系统"),
        YY1905("YY1905", "1905影院票务系统");
        private String code;
        private String desc;

        SaleSystem(String code, String desc) {
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
     * 影院类型
     */
    public enum CinemaType {
        SELF_SUPPORT("SELF_SUPPORT", "直营影院"),
        JOIN_IN("JOIN_IN", "加盟影院");
        private String code;
        private String desc;

        CinemaType(String code, String desc) {
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
     * 影院状态
     */
    public enum CinemaStatus {
        OPEN("OPEN", "运营中"),
        BUILD("BUILD", "建设中"),
        PLAN("PLAN", "规划中"),
        CLOSE("CLOSE", "关闭");
        private String code;
        private String desc;


        CinemaStatus(String code, String desc) {
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
     * 荧幕类型
     */
    public enum ScreenType {
        SCREEN_2D("2D", "2D"),
        SCREEN_3D("3D", "3D"),
        SCREEN_4D("4D", "4D"),
        SCREEN_DMAX("DMAX", "DMAX"),
        SCREEN_IMAX("IMAX", "IMAX");


        public String code;
        public String desc;

        ScreenType(String code, String desc) {
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
     * 影院表
     */
    public enum CinemaTable {
        ID("id", "id"),
        IS_DEL("is_del", "是否删除"),
        CINEMA_NAME("cinema_name", "影院名称"),
        CINEMA_CODE("cinema_code", "影院编码"),
        CINEMA_UNICODE("cinema_unicode", "影院专资码"),
        MERCHANT_CINEMA_STATUS("merchant_cinema_status", "是否分配商户"),
        HOLDER_COMPANY_PROVIDE_ID("holder_company_provide_id", "所属影管ID"),
        HOLDER_COMPANY_THEATERS_ID("holder_company_theaters_id", "所属院线ID"),
        HOLDER_MERCHANT_ID("holder_merchant_id", "所属商户ID"),
        COUNTRY("country_code", "所属区县"),
        CITY("city_code", "所属城市"),
        PROVINCE("province_code", "所属省");

        private String code;
        private String desc;

        CinemaTable(String code, String desc) {
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
     * 影院数据表
     */
    public enum CinemaOperDataTable {
        ANNUAL("annual", "年份"),
        OCCUPANCY_AVG("occupancy_avg", "平均上座率"),
        ANNUAL_BOX_OFFICE("annual_box_office", "年度票房"),
        PRESENT_PERSON("present_person", "人次"),
        SESSION_COUNT("session_count", "场次"),
        IS_DEL("is_del", "是否删除"),
        ;

        private String code;
        private String desc;

        CinemaOperDataTable(String code, String desc) {
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
     * 影院影厅表
     */
    public enum CinemaHallTable {
        ID("id", "id"),
        CINEMA_ID("cinema_id", "关联的影院ID"),
        IS_DEL("is_del", "是否删除"),
        HALL_CODE("hall_code", "影厅code"),
        HALL_UNICODE("hall_unicode", "影厅专资码"),
        ;

        private String code;
        private String desc;

        CinemaHallTable(String code, String desc) {
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
     * 影院广告表
     */
    public enum CinemaAdTable {
        CINEMA_ID("cinema_id", "关联的影院ID"),
        IS_DEL("is_del", "是否删除"),
        AD_CODE("ad_code", "广告位code"),
        ID("id", "关联的影院ID"),
        ;

        private String code;
        private String desc;

        CinemaAdTable(String code, String desc) {
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
     * 影院联系人表
     */
    public enum CinemaContractTable {
        CINEMA_ID("cinema_id", "关联的影院ID"),
        ;

        private String code;
        private String desc;

        CinemaContractTable(String code, String desc) {
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
     * 影院图片表
     */
    public enum CinemaPicTable {
        CINEMA_ID("cinema_id", "关联的影院ID"),
        ID_DEL("is_del", "是否删除"),
        ;

        private String code;
        private String desc;

        CinemaPicTable(String code, String desc) {
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

    public enum CinemaSupplierTable {
        CINEMA_ID("cinema_id", "关联的影院ID"),
        SUPPLIER_CODE("supplier_code", "供应商code"),
        PRODUCT_NUN("product_nun", "产品数量"),
        ;

        private String code;
        private String desc;

        CinemaSupplierTable(String code, String desc) {
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

    public enum CinemaInvestTable {
        CINEMA_ID("cinema_id", "关联的影院ID"),
        SUPPLIER_CODE("id", "id"),
        IS_DEL("is_del", "是否删除"),
        ;

        private String code;
        private String desc;

        CinemaInvestTable(String code, String desc) {
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
