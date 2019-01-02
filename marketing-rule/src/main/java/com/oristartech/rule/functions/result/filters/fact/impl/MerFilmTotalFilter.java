package com.oristartech.rule.functions.result.filters.fact.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.DateUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.constants.FactConstants;
import com.oristartech.rule.core.result.filters.IFactFilter;
import com.oristartech.rule.core.result.filters.IFactFilterChain;

/**
 * 计算商品总价，影票总价处理器
 * @author chenjunfei
 *
 */
@Component(value="ruleMerFilmTotalFilter")
public class MerFilmTotalFilter implements IFactFilter {
	private static Logger log = LoggerFactory.getLogger(MerFilmTotalFilter.class);
	private static final String DAY_HOUR = "yyyy-MM-dd HH:mm";
	
	public void doFilter(List<Object> facts, IFactFilterChain chain) {
		if(BlankUtil.isBlank(facts)) {
			return;
		}
		
		setProperInFacts(facts);
		chain.doFilter(facts);
	}

	/**
	 * 设置相关属性
	 * @param facts
	 */
	private void setProperInFacts(List<Object> facts) {
		Map<String, Object> saleInfo = null;
		BigDecimal filmTotal = BigDecimal.ZERO;
		BigDecimal merTotal = BigDecimal.ZERO;
		Map<String, BigDecimal> merBrandAmountMap = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> merClassAmountMap = new HashMap<String, BigDecimal>();
		
		for(Object fact : facts) {
			if(fact instanceof Map) {
				String type = String.valueOf(((Map)fact).get(FactConstants.CATEGORY_TYPE_KEY));
				if(BizFactConstants.SALE_INFO.equals(type)) {
					saleInfo = (Map)fact;
				}
				
				if(BizFactConstants.MER_INFO.equals(type)) {
					addToBrandMap((Map)fact, merBrandAmountMap);
					addToMerClassMap((Map)fact, merClassAmountMap);
					
					BigDecimal price = getItemPrice(fact);
					BigDecimal amount = getItemAmount(fact);
					setFilmPrice((Map)fact, price);
					merTotal = merTotal.add(price.multiply(amount));
				}
				if(BizFactConstants.FILM_INFO.equals(type)) {
					BigDecimal price = getItemPrice(fact);
					
					setMerPrice((Map)fact, price);
					filmTotal = filmTotal.add(price);
					addFilmStartTime((Map)fact);
				}
			}
		}
		setMerFilmTotal(saleInfo, filmTotal, merTotal);
		addBrandStatisToFact(facts, merBrandAmountMap);
		addMerClassStatisToFact(facts, merClassAmountMap);
	}
	
	/**
	 * 设置影票单价
	 * @param fact
	 * @param price
	 */
	private void setFilmPrice(Map<String, Object> fact, BigDecimal price) {
		if(fact == null) {
			return;
		}
		if(BlankUtil.isBlank(fact.get(BizFactConstants.FILM_FILM_PRICE))) {
			fact.put(BizFactConstants.FILM_FILM_PRICE, price);
		}
	}
	
	/**
	 * 设置卖品单价
	 * @param fact
	 * @param price
	 */
	private void setMerPrice(Map<String, Object> fact, BigDecimal price) {
		if(fact == null) {
			return;
		}
		
		if(BlankUtil.isBlank(fact.get(BizFactConstants.MER_MER_PRICE))) {
			fact.put(BizFactConstants.MER_MER_PRICE, price);
		}
	}
	
	/**
	 * 设置影票卖品总额
	 * @param saleInfo
	 * @param filmTotal
	 * @param merTotal
	 */
	private void setMerFilmTotal(Map<String, Object> saleInfo, BigDecimal filmTotal, BigDecimal merTotal ) {
		if(saleInfo == null) {
			return;
		}
		if(BlankUtil.isBlank(saleInfo.get(BizFactConstants.SALE_INFO_FILM_SUM_PRICE))) {
			saleInfo.put(BizFactConstants.SALE_INFO_FILM_SUM_PRICE, filmTotal);
		}
		
		if(BlankUtil.isBlank(saleInfo.get(BizFactConstants.SALE_INFO_MER_SUM_PRICE))) {
			saleInfo.put(BizFactConstants.SALE_INFO_MER_SUM_PRICE, merTotal);
		}
	}
	
	/**
	 * 添加同品牌统计fact
	 * @param facts
	 * @param merBrandAmountMap
	 */
	private void addBrandStatisToFact(List<Object> facts, Map<String, BigDecimal> merBrandAmountMap) {
		if(BlankUtil.isBlank(facts) || BlankUtil.isBlank(merBrandAmountMap)) {
			return;
		}
		
		for(String brand : merBrandAmountMap.keySet()) {
			Map<String , Object> statisFact = new HashMap<String, Object>();
			statisFact.put(FactConstants.CATEGORY_TYPE_KEY, BizFactConstants.MER_STATIS_INFO);
			statisFact.put(FactConstants.FACT_PARENT, "Object");
			statisFact.put(BizFactConstants.MER_BRAND_ID, brand);
			statisFact.put(BizFactConstants.MER_SATTIS_SUM_AMOUNT, merBrandAmountMap.get(brand));
			
			facts.add(statisFact);
		}
	}
	
	/**
	 * 添加同类型统计fact
	 * @param facts
	 * @param merBrandAmountMap
	 */
	private void addMerClassStatisToFact(List<Object> facts, Map<String, BigDecimal> merClassAmountMap) {
		if(BlankUtil.isBlank(facts) || BlankUtil.isBlank(merClassAmountMap)) {
			return;
		}
		for(String merClass : merClassAmountMap.keySet()) {
			Map<String , Object> statisFact = new HashMap<String, Object>();
			statisFact.put(FactConstants.CATEGORY_TYPE_KEY, BizFactConstants.MER_STATIS_INFO);
			statisFact.put(FactConstants.FACT_PARENT, "Object");
			statisFact.put(BizFactConstants.MER_CLASS_CODE, merClass);
			statisFact.put(BizFactConstants.MER_SATTIS_SUM_AMOUNT, merClassAmountMap.get(merClass));
			
			facts.add(statisFact);
		}
	}
	
	/**
	 * 添加同品牌统计数据
	 * @param mer
	 * @param merBrandAmountMap
	 */
	private void addToBrandMap(Map mer, Map<String, BigDecimal> merBrandAmountMap) {
		String brand = (String)mer.get(BizFactConstants.MER_BRAND_ID);
		if(BlankUtil.isBlank(brand)) {
			return;
		}
		BigDecimal amount = merBrandAmountMap.get(brand);
		if(amount == null) {
			amount = BigDecimal.ZERO;
		}
		Object merAmount = mer.get(BizFactConstants.AMOUNT);
		if(merAmount != null) {
			amount = amount.add( new BigDecimal(merAmount.toString()));
		} else {
			amount = amount.add(BigDecimal.ONE);
		}
		merBrandAmountMap.put(brand, amount);
	}
	
	/**
	 * 添加同卖品类别数据
	 * @param mer
	 * @param merClassAmountMap
	 */
	private void addToMerClassMap(Map mer, Map<String, BigDecimal> merClassAmountMap) {
		String merClass = (String)mer.get(BizFactConstants.MER_CLASS_CODE);
		if(BlankUtil.isBlank(merClass)) {
			return;
		}
		BigDecimal amount = merClassAmountMap.get(merClass);
		if(amount == null) {
			amount = BigDecimal.ZERO;
		}
		Object merAmount = mer.get(BizFactConstants.AMOUNT);
		if(merAmount != null) {
			amount = amount.add( new BigDecimal(merAmount.toString()));
		} else {
			amount = amount.add(BigDecimal.ONE);
		}
		
		merClassAmountMap.put(merClass, amount);
	}
	
	/**
	 * 设置放映前n天，前n小时属性
	 * @param film
	 */
	private void addFilmStartTime(Map<String, Object> film) {
		if(film != null) {
			String  planStartTime = (String)film.get(BizFactConstants.FILM_PLAN_START_TIME);
			if(!BlankUtil.isBlank(planStartTime)) {
				Date startTime = DateUtil.convertStrToDate(planStartTime, DateUtil.DEFAULT_DATE_FORMAT);
				film.put(BizFactConstants.FILM_START_DAY_BEFORE, getDayBefore(startTime));
				film.put(BizFactConstants.FILM_START_HOUR_BEFORE, getHourBefore(startTime));
			}
		}
	}
	
	/**
	 * 获取放映前小时数
	 * @param startTime
	 * @return
	 */
	private Integer getHourBefore(Date startTime) {
		Date startDay = DateUtil.convertStrToDate(DateUtil.convertDateToStr(startTime, DAY_HOUR), DAY_HOUR);
		Date nowDate =  DateUtil.convertStrToDate(DateUtil.convertDateToStr(new Date(), DAY_HOUR), DAY_HOUR);
		
		int before = (int)DateUtil.dateDiff("hh", nowDate, startDay);
		if(before < 0) {
			//若过了放映时间，设null
			return null;
		}
		return before;
	}
	
	/**
	 * 获取放映前天数
	 * @param startTime
	 * @return
	 */
	private Integer getDayBefore(Date startTime) {
		Date startDay = DateUtil.convertStrToDate(
								DateUtil.convertDateToStr(startTime, DateUtil.DEFAULT_SHORT_DATE_FORMAT),
								DateUtil.DEFAULT_SHORT_DATE_FORMAT);
		
		Date nowDate =  DateUtil.convertStrToDate(
				DateUtil.convertDateToStr(new Date(), DateUtil.DEFAULT_SHORT_DATE_FORMAT),
				DateUtil.DEFAULT_SHORT_DATE_FORMAT);
		
		
		int before = (int)DateUtil.dateDiff("dd", nowDate, startDay);
		if(before < 0) {
			//若过了放映时间，设null
			return null;
		}
		return before;
	}
	
	/**
	 * 获取商品单价
	 * @param fact
	 * @return
	 */
	private BigDecimal getItemPrice(Object fact) {
		Object p = ((Map)fact).get(BizFactConstants.PRICE);
		if(p != null) {
			return new BigDecimal(p.toString());
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * 获取数量
	 * @param fact
	 * @return
	 */
	private BigDecimal getItemAmount(Object fact) {
		Object p = ((Map)fact).get(BizFactConstants.AMOUNT);
		if(p != null) {
			return new BigDecimal(p.toString());
		}
		return BigDecimal.ONE;
	}
	
}
