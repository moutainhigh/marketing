package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 备案结果
 * @author: zhangdongdong
 * @createDate: 2018/9/17 下午5:20
 * @Version: 1.0
 */
public enum RemarkStatusEnum {
    PASS("pass", "通过"),
    FAIL("fail", "不通过");

    private String code;
    private String desc;

    RemarkStatusEnum(String code,String desc) {
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
