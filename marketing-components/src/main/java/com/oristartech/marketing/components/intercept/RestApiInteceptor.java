package com.oristartech.marketing.components.intercept;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.oristartech.api.exception.BizExceptionEnum;
import com.oristartech.marketing.components.constant.JwtConstants;
import com.oristartech.marketing.components.constant.RestResponse;
import com.oristartech.marketing.components.util.JwtTokenUtil;
import com.oristartech.marketing.components.util.RenderUtil;

import io.jsonwebtoken.JwtException;


/**
 * Rest Api接口鉴权
 *
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */

public class RestApiInteceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return check(request, response, handlerMethod);
    }

    private boolean check(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {
        if (request.getServletPath().equals(JwtConstants.AUTH_PATH)) {
            return true;
        }
        final String requestHeader = request.getHeader(JwtConstants.AUTH_HEADER);
        String authToken;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = JwtTokenUtil.isTokenExpired(authToken);
                if (flag) {

                    RenderUtil.renderJson(response,  RestResponse.createResult(BizExceptionEnum.TOKEN_ERROR.getCode(),BizExceptionEnum.TOKEN_ERROR.getMessage()));
                    return false;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                RenderUtil.renderJson(response, RestResponse.createResult(BizExceptionEnum.TOKEN_ERROR.getCode(),BizExceptionEnum.TOKEN_ERROR.getMessage()));
                return false;
            }
        } else {
            //header没有带Bearer字段
            RenderUtil.renderJson(response, RestResponse.createResult(BizExceptionEnum.TOKEN_ERROR.getCode(),BizExceptionEnum.TOKEN_ERROR.getMessage()));
            return false;
        }
        return true;
    }

}
