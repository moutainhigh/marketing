package com.oristartech.marketing.components.constant;

import com.oristartech.api.exception.BaseException;
import com.oristartech.api.exception.BizExceptionEnum;
import com.oristartech.marketing.components.tips.SuccessTip;

/**
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */
public class RestResponse<T> {

    private int code;

    private long timestamp;

    private String msg;

    private T data;


    public RestResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp = System.currentTimeMillis();
    }

    public RestResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }


    public long getTimestamp() {
        return timestamp;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public static <T> RestResponse<T> createSuccessResult(T data) {
        return new RestResponse<T>(new SuccessTip().getCode(), new SuccessTip().getMessage(), data);
    }

    public static <T> RestResponse<T> createSuccessResult() {
        return new RestResponse<T>(new SuccessTip().getCode(), new SuccessTip().getMessage());
    }

    public static <T> RestResponse<T> createDefaultSuccResult() {
        return new RestResponse<T>(new SuccessTip().getCode(), "OK");
    }

    public static <T> RestResponse<T> createResult(int code, String msg, T data) {
        return new RestResponse<T>(code, msg, data);
    }

    public static <T> RestResponse<T> createResult(BizExceptionEnum bizExceptionEnum, T data) {
        return new RestResponse<T>(bizExceptionEnum.getCode(), bizExceptionEnum.getMessage(), data);
    }

    public static <T> RestResponse<T> createResult(BizExceptionEnum bizExceptionEnum) {
        return createResult(bizExceptionEnum.getCode(), bizExceptionEnum.getMessage());
    }
    public static <T> RestResponse<T> createResult(BaseException baseException) {
        return new RestResponse<T>(baseException.getCode(), baseException.getMessage());
    }

    public static <T> RestResponse<T> createResult(int code, String msg) {
        return new RestResponse<T>(code, msg);
    }

    //    @JsonIgnore
//    @JSONField(serialize=false)
    public boolean isSuccessCode() {
        return new SuccessTip().getCode() == code;
    }

    //    @JsonIgnore
//    @JSONField(serialize=false)
    public boolean isSuccess() {
        return isSuccessCode();
    }
}