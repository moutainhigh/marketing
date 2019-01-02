package com.oristartech.marketing.components.constant.enums;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wangqingqing
 * @Date: 2018-11-26 10:31
 * @Description:
 */
public class DetailListEnum {

	public static final List<DetailEnum> baseList = new ArrayList<>();
	public static final Map<String, DetailEnum> ringRatioMap = new HashMap<>();
	public static final Map<String, DetailEnum> yearOnYearMap = new HashMap<>();

	static{
		baseList.add(DetailEnum.BOX_OFFICE);
		baseList.add(DetailEnum.SHOW_COUNT);
		baseList.add(DetailEnum.AUDIENCE_COUNT);
		baseList.add(DetailEnum.OFFER_SEAT);

		ringRatioMap.put(DetailEnum.BOX_OFFICE.getType(), DetailEnum.BOX_OFFICE_RING_RATIO);
		ringRatioMap.put(DetailEnum.SHOW_COUNT.getType(), DetailEnum.SHOW_COUNT_RING_RATIO);
		ringRatioMap.put(DetailEnum.AUDIENCE_COUNT.getType(), DetailEnum.AUDIENCE_COUNT_RING_RATIO);
		ringRatioMap.put(DetailEnum.OFFER_SEAT.getType(), DetailEnum.OFFER_SEAT_RING_RATIO);

		yearOnYearMap.put(DetailEnum.BOX_OFFICE.getType(), DetailEnum.BOX_OFFICE_YEAR_ON_YEAR);
		yearOnYearMap.put(DetailEnum.SHOW_COUNT.getType(), DetailEnum.SHOW_COUNT_YEAR_ON_YEAR);
		yearOnYearMap.put(DetailEnum.AUDIENCE_COUNT.getType(), DetailEnum.AUDIENCE_COUNT_YEAR_ON_YEAR);
		yearOnYearMap.put(DetailEnum.OFFER_SEAT.getType(), DetailEnum.OFFER_SEAT_YEAR_ON_YEAR);
	}

	public enum DetailEnum{
		BOX_OFFICE("boxOffice","票房"),
		SHOW_COUNT("showCount","场次"),
		AUDIENCE_COUNT("audienceCount","人次"),
		OFFER_SEAT("offerSeat","排座"),

		BOX_OFFICE_RING_RATIO("boxOfficeRingRatio","票房环比"),
		SHOW_COUNT_RING_RATIO("showCountRingRatio","场次环比"),
		AUDIENCE_COUNT_RING_RATIO("audienceCountRingRatio","人次环比"),
		OFFER_SEAT_RING_RATIO("offerSeatRingRatio","排座环比"),

		BOX_OFFICE_YEAR_ON_YEAR("boxOfficeYearOnYear","票房同比"),
		SHOW_COUNT_YEAR_ON_YEAR("showCountYearOnYear","场次同比"),
		AUDIENCE_COUNT_YEAR_ON_YEAR("audienceCountYearOnYear","人次同比"),
		OFFER_SEAT_YEAR_ON_YEAR("offerSeatYearOnYear","排座同比");

		private String type;
		private String name;


		DetailEnum(String type, String name) {
			this.type = type;
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
