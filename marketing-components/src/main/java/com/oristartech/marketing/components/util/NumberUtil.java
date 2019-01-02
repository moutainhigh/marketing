package com.oristartech.marketing.components.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * @description:
 * @author: zhangdongdong
 * @createDate: 2018/11/15 4:07 PM
 * @Version: 1.0
 */
public class NumberUtil {

    /**
     * 0
     */
    private final static BigDecimal ZERO = new BigDecimal(0);
    /**
     * 1
     */
    private final static BigDecimal ONE = new BigDecimal(1);
    /**
     * -1
     */
    private final static BigDecimal NEGATIVE_ONE = new BigDecimal(-1);

    /**
     * n/d
     * 负数
     *
     * @param n     分子
     * @param d     分母
     * @param digit 精确到小数点后几位
     * @return n==0 返回值0 ,d==0 && n>0返回值1,d==0 && n<0返回值-1, n!=0 && d!=0 返回n/d
     */
    public static BigDecimal numberScaleFormat(double n, double d, int digit) {
        if (n == 0) {
            return ZERO.setScale(digit);
        }
        if (d == 0) {
            if (n > 0) {
                return ONE.setScale(digit);
            } else {
                return NEGATIVE_ONE.setScale(digit);
            }
        }
        return new BigDecimal(n).divide(new BigDecimal(d), digit, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * n/d
     *
     * @param n     分子
     * @param d     分母
     * @param digit 精确到小数点后几位
     * @return n==0 返回值0 ,d==0 && n>0返回值1,d==0 && n<0返回值-1, n!=0 && d!=0 返回n/d
     */
    public static BigDecimal numberScaleFormat(BigDecimal n, BigDecimal d, int digit) {
        if (n == null) {
            throw new IllegalArgumentException("n == null!");
        }
        if (d == null) {
            throw new IllegalArgumentException("m == null!");
        }
        if (n.doubleValue() == 0) {
            return ZERO.setScale(digit);
        }
        if (d.doubleValue() == 0) {
            if (n.doubleValue() > 0) {
                return ONE.setScale(digit);
            } else {
                return NEGATIVE_ONE.setScale(digit);
            }
        }
        return n.divide(d, digit, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算票房占比，scale为4, 向上取整
     * boxOffice/boxOfficeTotal
     *
     * @param boxOffice      当前票房
     * @param boxOfficeTotal 总票房
     * @return
     */
    public static BigDecimal boxOfficeRate(BigDecimal boxOffice, BigDecimal boxOfficeTotal) {
        final short scale = 4;
        if (boxOffice == null) {
            throw new IllegalArgumentException("boxOffice == null!");
        }
        if (boxOfficeTotal == null) {
            throw new IllegalArgumentException("boxOfficeTotal == null!");
        }
        return numberScaleFormat(boxOffice, boxOfficeTotal, scale);
    }

    /**
     * 环比 计算
     * (当前时间数-前推时间数)/前推时间数
     *
     * @param current 当前时间数
     * @param before  前推时间数
     * @param digit   精确到小数点后几位
     * @return
     */
    public static BigDecimal ringRatio(double current, double before, int digit) {
        return numberScaleFormat(current - before, before, digit);
    }

    /**
     * 环比 计算
     * (当前时间数-前推时间数)/前推时间数
     *
     * @param current 当前时间数
     * @param before  前推时间数
     * @param digit   精确到小数点后几位
     * @return
     */
    public static BigDecimal ringRatio(BigDecimal current, BigDecimal before, int digit) {
        if (current == null) {
            throw new IllegalArgumentException("current == null!");
        }
        if (before == null) {
            throw new IllegalArgumentException("before == null!");
        }
        return numberScaleFormat(current.subtract(before), before, digit);
    }

    /**
     * 环比 计算
     * (当前时间数-前推时间数)/前推时间数
     *
     * @param current 当前时间数
     * @param before  前推时间数
     * @param digit   精确到小数点后几位
     * @return
     */
    public static BigDecimal ringRatio(String current, String before, int digit) {
        if (current == null) {
            throw new IllegalArgumentException("current == null!");
        }
        if (before == null) {
            throw new IllegalArgumentException("before == null!");
        }
        return ringRatio(new BigDecimal(current), new BigDecimal(before), digit);
    }

    public static BigDecimal ringRatioNull(BigDecimal current, BigDecimal before, int digit) {
        if (current == null) {
            return null;
        }
        if (before == null) {
            return null;
        }
        return numberScaleFormat(current.subtract(before), before, digit);
    }

    public static BigDecimal ringRatioNull(Long currentLong, Long beforeLong, int digit) {
        if (currentLong == null) {
            return null;
        }
        if (beforeLong == null) {
            return null;
        }
        BigDecimal current = new BigDecimal(currentLong);
        BigDecimal before = new BigDecimal(beforeLong);
        return numberScaleFormat(current.subtract(before), before, digit);
    }

    public static BigDecimal ringRatioNull(Integer currentLong, Integer beforeLong, int digit) {
        if (currentLong == null) {
            return null;
        }
        if (beforeLong == null) {
            return null;
        }
        BigDecimal current = new BigDecimal(currentLong);
        BigDecimal before = new BigDecimal(beforeLong);
        return numberScaleFormat(current.subtract(before), before, digit);
    }

    /**
     * 字符串是否可解析为数值
     * @param str
     * @return
     */
    public static  boolean isParsable(String str){
       return NumberUtils.isParsable(str);
    }

}
