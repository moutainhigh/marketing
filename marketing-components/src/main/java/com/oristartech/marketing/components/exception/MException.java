package com.oristartech.marketing.components.exception;

import com.oristartech.api.exception.ServiceExceptionEnum;

/**
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */
public class MException extends RuntimeException {

    private Integer code;

    private String message;

    public MException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public MException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
