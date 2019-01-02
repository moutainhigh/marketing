package com.oristartech.marketing.components.http;

/**
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */
public class HttpResult {

    private Integer code;
    private String data;

    public HttpResult(Integer code, String data) {
        this.code = code;
        this.data = data;
    }

    public HttpResult() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
