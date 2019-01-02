package com.oristartech.api.exception;

/**
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */
public interface ServiceExceptionEnum {

    /**
     * 获取异常编码
     */
    Integer getCode();

    /**
     * 获取异常信息
     */
    String getMessage();
}
