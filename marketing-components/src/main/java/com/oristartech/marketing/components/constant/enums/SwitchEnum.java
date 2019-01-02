package com.oristartech.marketing.components.constant.enums;

/**
 * @description: 开关 通用
 * 1 开 0 关
 * @author: zhangdongdong
 * @createDate: 2018/11/23 11:14 AM
 * @Version: 1.0
 */
public enum SwitchEnum {
    OPEN(1),
    CLOSE(0);

    private Integer code;

    SwitchEnum(Integer code) {
        this.code = code;

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}

