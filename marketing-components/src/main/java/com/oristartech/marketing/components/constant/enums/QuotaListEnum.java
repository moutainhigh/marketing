package com.oristartech.marketing.components.constant.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangqingqing
 * @Date: 2018-11-20 15:18
 * @Description:
 */
public class  QuotaListEnum {
	public static final List<QuotaEnum> cinemaQuota = new ArrayList<>();
	public static final List<QuotaEnum> cinemaLineQuota = new ArrayList<>();
	static{
		cinemaQuota.add(QuotaEnum.BOX_OFFICE);
		cinemaQuota.add(QuotaEnum.SHOW_COUNT);
		cinemaQuota.add(QuotaEnum.AUDIENCE_COUNT);
		cinemaQuota.add(QuotaEnum.AVG_PRICE);
		cinemaQuota.add(QuotaEnum.AVG_SEAT_VIEW_RATE);
		cinemaQuota.add(QuotaEnum.AVG_DAY_SEAT_BOX_OFFICE);
		cinemaQuota.add(QuotaEnum.AVG_DAY_HALL_BOX_OFFICE);
		cinemaQuota.add(QuotaEnum.AVG_DAY_SHOW_COUNT);
		cinemaQuota.add(QuotaEnum.AVG_SESSION_BOX_OFFICE);
		cinemaQuota.add(QuotaEnum.AVG_SESSION_AUDIENCE_COUNT);


		cinemaLineQuota.add(QuotaEnum.BOX_OFFICE);
		cinemaLineQuota.add(QuotaEnum.SHOW_COUNT);
		cinemaLineQuota.add(QuotaEnum.AUDIENCE_COUNT);
		cinemaLineQuota.add(QuotaEnum.AVG_PRICE);
		cinemaLineQuota.add(QuotaEnum.AVG_SESSION_AUDIENCE_COUNT);
		cinemaLineQuota.add(QuotaEnum.AVG_SEAT_VIEW_RATE);
	}

	public enum QuotaEnum {
		BOX_OFFICE("boxOffice", "票房（万）"),
		AVG_PRICE("avgPrice", "平均票价"),
		AVG_DAY_SEAT_BOX_OFFICE("avgDaySeatBoxOffice", "单日单座收益"),
		AVG_DAY_HALL_BOX_OFFICE("avgDayHallBoxOffice", "单日单厅收益"),
		AVG_SESSION_BOX_OFFICE("avgSessionBoxOffice", "场均收入"),
		SHOW_COUNT("showCount", "场次"),
		AUDIENCE_COUNT("audienceCount", "人次"),
		AVG_SEAT_VIEW_RATE("avgSeatViewRate", "上座率"),
		AVG_DAY_SHOW_COUNT("avgDayShowCount", "单日单厅场次"),
		AVG_SESSION_AUDIENCE_COUNT("avgSessionAudienceCount", "场均人次");


		private String type;
		private String name;

		QuotaEnum(String type, String name) {
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
