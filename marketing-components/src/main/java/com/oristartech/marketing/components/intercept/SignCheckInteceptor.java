package com.oristartech.marketing.components.intercept;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.oristartech.api.exception.BizExceptionEnum;
import com.oristartech.marketing.components.constant.RestResponse;
import com.oristartech.marketing.components.util.RedisClient;
import com.oristartech.marketing.components.util.RenderUtil;

/**
 * @Auther: wangqingqing
 * @Date: 2018-10-18 10:41
 * @Description:
 */
public class SignCheckInteceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger("SignCheckInteceptor");
	private int seconds = 60;
	@Autowired
	private RedisClient redisClient;

	/***
	 *spring mvc的请求过滤
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			String path = request.getServletPath();
			Map<String, Object> map = new HashMap<String, Object>();
			//接受前台的数据 sign是加密的字符串
			String sign = request.getHeader("sign");
			//前台的时间戳
			String timestamp = request.getHeader("timestamp");
			//用户的凭证，这个根据业务写
			String token = request.getHeader("authorization");
			logger.info("===>sign验证-----------------sign:{},timestamp:{},authorization:{}",sign, timestamp, token);
			//判断是否为空 为空直接返回false
			if (StringUtils.isNoneBlank(sign) && (StringUtils.isNotBlank(timestamp) & timestamp.length() == 13)) {
				//验证请求sign是否已使用
				String oldSign = redisClient.get(sign);
				if (StringUtils.isNotBlank(oldSign)){
					logger.info("接口访问失败,签名已使用,重复请求，访问路径：----" + path);
					RenderUtil.renderJson(response, RestResponse.createResult(BizExceptionEnum.SIGN_ERROR.getCode(), BizExceptionEnum.SIGN_ERROR.getMessage()));
					return false;
				}
				System.out.println("======="+oldSign);
				//验证请求超时
				Long _timestamp = Long.parseLong(timestamp);
				if (System.currentTimeMillis() - _timestamp > seconds * 1000) {
					logger.info("接口访问失败,请求超时,访问路径：----" + path);
					RenderUtil.renderJson(response, RestResponse.createResult(BizExceptionEnum.SIGN_EXPIRED.getCode(), BizExceptionEnum.SIGN_EXPIRED.getMessage()));
					return false;
				}
				//验证签名
				boolean signBool = checkSign(token, sign, timestamp);
				if (!signBool) {
					RenderUtil.renderJson(response, RestResponse.createResult(BizExceptionEnum.SIGN_ERROR.getCode(), BizExceptionEnum.SIGN_ERROR.getMessage()));
					logger.info("接口访问失败,验签不正确,访问路径：----" + path);
					return false;
				}
			} else {
				RenderUtil.renderJson(response, RestResponse.createResult(BizExceptionEnum.SIGN_ERROR.getCode(), BizExceptionEnum.SIGN_ERROR.getMessage()));
				logger.info("接口访问失败,验签不正确,访问路径：----" + path);
				return false;
			}
			logger.info("接口访问成功,访问路径：----" + path);
			redisClient.setWithExpireTime(sign,"true",seconds * 1000);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 检测KEY是否正确
	 */
	public static boolean checkSign(String token, String sign,String timestamp){
		return DigestUtils.md5Hex(token+",tthappy.cn,"+timestamp).equalsIgnoreCase(sign);
	}
}
