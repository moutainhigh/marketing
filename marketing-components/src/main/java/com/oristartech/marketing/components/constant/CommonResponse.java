package com.oristartech.marketing.components.constant;

import java.io.Serializable;
/**
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */
public class CommonResponse implements Serializable {
    /** 响应号 */
    private String responseNo;

    /** 响应时间 */
    private String responseTime;

    /** 版本号 */
    private String version = "1.0.0";

    /** 返回编码 */
    private String code;

    /** 返回信息 */
    private String msg;

    /** 响应数据 */
    private String responseData;

    public String getResponseNo() {
        return responseNo;
    }

    public void setResponseNo(String responseNo) {
        this.responseNo = responseNo;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
