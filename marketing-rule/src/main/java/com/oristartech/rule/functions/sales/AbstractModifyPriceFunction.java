package com.oristartech.rule.functions.sales;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.oristartech.marketing.core.exception.RuleExecuteRuntimeException;
import com.oristartech.rule.common.util.BeanUtils;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.constants.BizFactConstants;
import com.oristartech.rule.core.functions.BizFunctionUtil;
import com.oristartech.rule.functions.util.DecimalRoundMode;


/**
 * 修改价格相关类的共有父类
 * @author Administrator
 *
 */
public abstract class AbstractModifyPriceFunction extends AbstractRuleExecutorFunction {
	//折扣后加减N元
	protected static final String ADD_AMOUNT_AFTER_DISCOUNT = "addAmountAfterDiscount";
	
	/**
	 * 因为现在卖品是一个一个加, SaleItemKey把所有的key用逗号连接, 现在根据amount ,去掉多余的key.
	 * @param resultFact
	 */
	protected void splitSaleItemKeys(Map<String, Object> resultFact, BigDecimal modifyAmount, String excludeKeys) {
		String saleItemKey = (String)resultFact.get(BizFactConstants.SALE_ITEM_KEY);
		if(BlankUtil.isBlank(saleItemKey)) {
			return;
		}
		
		int amount = modifyAmount.intValue();
		String[] keys = saleItemKey.split(BizFactConstants.SALE_ITEM_KEY_SPLITER) ;
		//数量多于,等于key,表示用全部key
		if(amount >= keys.length) {
			return;
		}
		
		List<String> keyList = new ArrayList<String>();
		int addCount = 0;
		for(int i=0 ; i < keys.length; i++) {
			if(excludeKeys == null || excludeKeys.indexOf(keys[i]) == -1 ) {
				keyList.add(keys[i]);
				addCount ++;
				if(addCount >= amount) {
					break;
				}
			}
		}
		resultFact.put(BizFactConstants.SALE_ITEM_KEY, StringUtils.join(keyList, BizFactConstants.SALE_ITEM_KEY_SPLITER));
	}
	
	//移除不是销售的fact
	protected void removeNotSaleFact(List<Object> facts) {
		if(!BlankUtil.isBlank(facts)) {
			Iterator<Object> it = facts.iterator();
			while(it.hasNext()) {
				Object fact = it.next();
				if(!BizFunctionUtil.isSaleInfo(fact)) {
					it.remove();
				}
			}
		}
	}
	
	//移除不是销售的fact
	protected void removeNotSaleFact(List<Object> facts, String itemType) {
		if(!BlankUtil.isBlank(facts)) {
			Iterator<Object> it = facts.iterator();
			while(it.hasNext()) {
				Object fact = it.next();
				String saleItemType = BeanUtils.getPropertyStr(fact, BizFactConstants.SALE_ITEM_TYPE);
				if(!BizFunctionUtil.isSaleInfo(fact) || !itemType.equals(saleItemType)) {
					it.remove();
				}
			}
		}
	}
	
	protected void validateMerFact(Object fact) {
		String merKey = BeanUtils.getPropertyStr(fact, BizFactConstants.MER_KEY);
		BigDecimal price = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.PRICE);
		BigDecimal amount = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.AMOUNT);
		if(price == null) {
			throw new RuleExecuteRuntimeException("必须设置价格");
		}
		if(amount == null) {
			throw new RuleExecuteRuntimeException("必须设置数量");
		}
		if(merKey == null) {
			throw new RuleExecuteRuntimeException("必须设置商品key");
		}
	}
	
	protected void validateFilmFact(Object fact) {
		BigDecimal price = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.PRICE);
		BigDecimal lowestPrice = BizFunctionUtil.getBigDecimal(fact, BizFactConstants.LOW_PRICE);
		if(price == null) {
			throw new RuleExecuteRuntimeException("必须设置价格");
		}
		if(lowestPrice == null) {
			throw new RuleExecuteRuntimeException("必须设置最低发行价");
		}
	}
	//商品价格倒序排序
	protected void sortSaleFact(List<Object> facts ) {
		if(BlankUtil.isBlank(facts) || facts.size() < 2) {
			return;
		}
		
		Collections.sort(facts, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				BigDecimal price1 = BizFunctionUtil.getBigDecimal(o1, BizFactConstants.PRICE);
				BigDecimal price2 = BizFunctionUtil.getBigDecimal(o2, BizFactConstants.PRICE);
			    return price2.compareTo(price1);
			}
		});
	}
	
	/**
	 * 获取正确的价格,必须大于等于0
	 * @param newPrice
	 * @param price
	 * @return
	 */
	protected BigDecimal getCorrectPrice(BigDecimal newPrice, BigDecimal price) {
		if(newPrice == null) {
			newPrice = price;
		} else if(newPrice.compareTo(BigDecimal.ZERO) < 0) {
			newPrice = BigDecimal.ZERO;
		}
		return newPrice;
	}
	
	/**
	 * 据设置的方法调整小数,控制进位
	 * @param newPrice
	 * @param adjustMethod
	 * @return
	 */
	public BigDecimal roundDecimalNumber(BigDecimal price, String roundMode) {
		if(price == null) {
			return price;
		}
		int mode = DecimalRoundMode.convertBigDecimalRoundMode(roundMode, DecimalRoundMode.ROUND_UNNECESSARY);
		if(mode == BigDecimal.ROUND_UNNECESSARY) {
			return price;
		}
		BigDecimal newPrice = price.setScale(0, mode);
		return newPrice;
	}
	
	/**
	 * 计算折扣后加减N元后的总价格
	 * @param curPrice
	 * @param params
	 * @return
	 */
	protected BigDecimal addAmountAfterDiscount(BigDecimal curPrice, Map<String, String> params) {
		if(curPrice == null) {
			return curPrice;
		}
		BigDecimal addAmount = BizFunctionUtil.getBigDecimal(params, ADD_AMOUNT_AFTER_DISCOUNT);
		
		if(addAmount == null) {
			return curPrice;
		}
		
		return curPrice.add(addAmount);
	}
}
