package com.oristartech.marketing.components.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @Auther: hexu
 * @Date: 2018/11/5 15:52
 * @Description:
 */
@ControllerAdvice
public class ExceptionFilter {

    private static Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public RestResponse Handle(Exception e) {
//
//        if (e instanceof MException) {
//            MException mpException = (MException) e;
//            return new RestResponse(mpException.getCode(), mpException.getMessage());
//        } else {
//            logger.error("未处理的异常", e);
//            return new RestResponse(500, "服务器未知错误");
//        }
//
//    }

}
