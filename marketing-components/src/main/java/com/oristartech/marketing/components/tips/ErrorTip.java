package com.oristartech.marketing.components.tips;

/**
 * 返回给前台的错误提示
 *
 * @author wangweiheng
 * @date 2018-08-17
 */
public class ErrorTip extends Tip {

    public ErrorTip(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
}
