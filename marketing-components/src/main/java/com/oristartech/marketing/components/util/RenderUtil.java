package com.oristartech.marketing.components.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.oristartech.api.exception.MExceptionEnum;
import com.oristartech.marketing.components.exception.MException;

/**
 * 渲染工具类
 *
 * @author wangweiheng
 * @date 2018-08-17 14:13
 */
public class RenderUtil {

    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
        } catch (IOException e) {
            throw new MException(MExceptionEnum.WRITE_ERROR);
        }
    }
}
