package com.oristartech.marketing.core.http;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.ServiceException;
import com.oristartech.rule.common.util.BlankUtil;

/**
 * http 客户请求工具类
 * @author chenjunfei
 *
 */
public class HttpClientUtil {
	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	//设置数据传输超时时间(毫秒)
	private static int SO_TIMEOUT = 20000;
	//设置连接超时时间(毫秒)
	private static int CONNECT_TIMEOUT = 20000;
	
	/**
	 * 发送http请求
	 * @param params
	 * @param url
	 * @param isPost
	 * @return
	 */
	public static HttpResult sendHttpRequest(Map<String, String> params, String url, Boolean isPost) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(!BlankUtil.isBlank(params)) {
			for(String key : params.keySet()) {
				String value = params.get(key);
				if(value != null) {
					nvps.add(new BasicNameValuePair(key	, value));
				}
			}			
		}
		
		if(log.isDebugEnabled()) {
			log.debug("---------Http请求，url=" + url +"  ,parameters =" + params.toString());
		}
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpRequestBase httpRequest = null;
		CloseableHttpResponse response = null;
		try {
			if(isPost) {
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
				httpRequest = httpPost;
				
			} else {
				httpRequest = new HttpGet(url);
			}
			
			RequestConfig requestConfig = RequestConfig.custom()
			                                           .setSocketTimeout(SO_TIMEOUT)
			                                           .setConnectTimeout(CONNECT_TIMEOUT).build();//设置请求和传输超时时间
			httpRequest.setConfig(requestConfig);
			
			response = httpclient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			String resContent = EntityUtils.toString(entity);
			StatusLine statusLine = response.getStatusLine();
			HttpResult result = new HttpResult();
			result.setHttpStatus(String.valueOf(statusLine.getStatusCode()));
			result.setContent(resContent);
			
			if(log.isDebugEnabled()) {
				log.debug("----------HttpClientUtil response status=" + response.getStatusLine()
						  + " response content = " + resContent);
			}
			
			if(!result.isOk()) {
				log.error(getErrorMsg(url, params, resContent));
			}
			return result;
			
		} catch (Exception e) {
			throw new ServiceException(getErrorMsg(url, params) , e);
		}  finally {
			closeConnection(response);
			closeConnection(httpclient);
		}
	}
	
	private static String getErrorMsg(String url, Map<String, String> params) {
		return "-----http请求错误：url=" 
				+ url + ",param=" + (params != null ? params.toString() : "");
	}
	
	private static String getErrorMsg(String url, Map<String, String> params, String content) {
		return "-----http请求异常：url=" 
				+ url + ", content=" 
				+ content + ",param=" + (params != null ? params.toString() : "");
	}
	
	private static void closeConnection(Closeable con) {
		try {
			if(con != null) {
				con.close();
			}
		} catch (Exception e) {
			throw new ServiceException("Exception http connect : " , e);
		}
	}
}
