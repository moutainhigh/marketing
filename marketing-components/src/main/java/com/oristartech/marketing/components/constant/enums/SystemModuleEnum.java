package com.oristartech.marketing.components.constant.enums;

/**
 * @description:
 * @author:
 * @createDate: 2018/9/3 下午2:16
 * @Version: 1.0
 */
public   enum SystemModuleEnum {

    login_module("login_module", "登录模块"),
    merchant_module("merchant_module", "商户模块"),
    agreement_module("agreement_module", "协议模块"),
    movie_module("movie_module", "电影模块"),
    cinema_module("cinema_module", "影院模块"),
    actor_module("actor_module", "影人模块"),
    company_module("company_module", "公司模块"),
    user_module("user_module", "用户模块"),
    role_module("role_module", "角色模块"),
    system_notice_module("system_notice_module", "系统公告");

    private String code;
    private String desc;
    SystemModuleEnum(String code, String desc) {
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

    public static SystemModuleEnum valueOfDesc(String desc) {
        if (desc == null || desc.equals("")) {
            return null;
        }
        for (SystemModuleEnum value : values()) {
            if (value.getDesc().equals(desc)) {
                return value;
            }
        }
        return null;
    }


}
