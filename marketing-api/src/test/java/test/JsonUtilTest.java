package test;

import com.oristartech.marketing.vo.MarketingSearchVO;
import com.oristartech.rule.common.util.JsonUtil;

public class JsonUtilTest {

	public static void main(String[] args) {
		MarketingSearchVO vo = new MarketingSearchVO();
		vo.setBusinessCode("1234");
		vo.setSearchState("2345");
		System.out.println(JsonUtil.objToJson(vo));
	}

}
