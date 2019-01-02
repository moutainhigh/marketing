package com.oristartech.marketing.components.constant.enums;

/**
 * @Description 不能修改的权限
 * @Author MackCC.Sun
 * @Date 2018-10-15
 **/
public enum PermModuleEnum {


    home("home", "首页"),
    system("system", "系统管理"),
    perm("perm", "人员权限"),
    log("log", "日志查询"),
    dict("dict", "字典管理"),
    user("user", "人员管理"),
    role("role", "角色管理"),
    menu("menu", "菜单管理"),

    /*base("base", "基础信息管理"),
    merc("merc", "商户管理"),
    notice("notice", "系统公告"),
    mess("emss", "消息管理"),
    movie("movie", "电影信息"),
    cinemaInfo("cinemaInfo", "影院信息"),
    company("company", "公司信息"),
    fileaker("fileaker", "影人信息"),
    mercmanage("mercmanage", "商户管理"),
    mercagreement("emss", "协议管理"),*/


    ;

    private String code;
    private String desc;

    PermModuleEnum(String code, String desc) {
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

    public static PermModuleEnum valueOfDesc(String desc) {
        if (desc == null || desc.equals("")) {
            return null;
        }
        for (PermModuleEnum value : values()) {
            if (value.getDesc().equals(desc)) {
                return value;
            }
        }
        return null;
    }

    public static String getDescByCode(String code){
        if (code == null || code.equals("")) {
            return "";
        }
        for (PermModuleEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value.getDesc();
            }
        }
        return "";
    }


}
