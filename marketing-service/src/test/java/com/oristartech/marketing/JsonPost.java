package com.oristartech.marketing;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.oristartech.marketing.core.http.HttpClientUtil;
import com.oristartech.rule.common.util.JsonUtil;

 
 
public class JsonPost {
	
	public static void main(String[] args) {
//		//Post方式提交Json字符串，按照指定币种，指定时间点查询
//		String json = "[{\"_type\":\"MemberInfo\",\"_parent\":\"Object\",\"_load\":null,\"cardTypeKey\":\"5\",\"balance\":null,\"integral\":null,\"chargeSum\":null,\"consumeSum\":null,\"integralTotall\":null,\"birthday\":null,\"sex\":null,\"age\":null,\"openYears\":null,\"openDate\":null,\"registerBusinessCode\":null,\"mobileNum\":null,\"cardLabelId\":null,\"openMonths\":null,\"firstRecharge\":\"300\",\"_typeList\":\"MemberInfo\"},{\"_type\":\"SaleInfo\",\"_parent\":\"Object\",\"_load\":null,\"sumPrice\":null,\"consumeWayCode\":\"0\",\"cinemaCode\":\"32045111\",\"cinemaAreaId\":null,\"tradeType\":\"MEMBER_ACTIVE_CARD\",\"consumerTypeKey\":null,\"businessCode\":\"32045111\",\"payTypeCode\":null,\"filmSumPrice\":null,\"merSumPrice\":null,\"filmTicketAmount\":null,\"consumerTypes\":null,\"ruleExecuteMode\":null,\"firstBuyTicket\":null,\"firstRecharge\":\"300\",\"_typeList\":\"SaleInfo\"},{\"_type\":\"RuleContextInfo\",\"_parent\":\"Object\",\"isOutputConsumeFacts\":true,\"isUseNewConsumeItem\":true,\"_typeList\":\"RuleContextInfo\"},{\"_type\":\"CommonInfo\",\"_parent\":\"Object\",\"validDate\":\"2018-12-26 12:21:32\",\"validWeek\":\"2018-12-26 12:21:32\",\"validTime\":\"2018-12-26 12:21:32\",\"holiday\":false,\"appointInvalidDate\":\"2018-12-26 12:21:32\",\"currentDate\":\"2018-12-26 12:21:32\",\"_typeList\":\"CommonInfo\"}]";
////		String json = "123";
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("typeName", "123");
//		params.put("facts", JsonUtil.objToJson(json));
//		String url = "http://localhost:8025/test/matchrule";
//		System.out.println(HttpClientUtil.sendHttpRequest(params, url, true));
		
		List<String> list = new ArrayList<String>();
		list.add("coupon");
		list.add("sale");
		list.add("mem");
		list.add("aaa");
		String str = "'"+ list.stream().collect(Collectors.joining("','")) + "'";
		System.out.println(str);
	}
	
	public static String HttpPostWithJson(String url, String json) {
		String returnValue = "这是默认返回值，接口调用失败";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try{
			//第一步：创建HttpClient对象
		 httpClient = HttpClients.createDefault();
		 	
		 	//第二步：创建httpPost对象
	        HttpPost httpPost = new HttpPost(url);
	        
	        //第三步：给httpPost设置JSON格式的参数
//	        StringEntity requestEntity = new StringEntity(json,"UTF-8");
//	        requestEntity.setContentEncoding("UTF-8");    	        
	        httpPost.setHeader("Content-type", "application/json");
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//			nvps.add(new BasicNameValuePair("facts"	, json));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
	       
	       //第四步：发送HttpPost请求，获取返回值
	       returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法
	      
		}
		 catch(Exception e)
		{
			 e.printStackTrace();
		}
		
		finally {
	       try {
			httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
		 //第五步：处理返回值
	     return returnValue;
	}
	
 
 
}
