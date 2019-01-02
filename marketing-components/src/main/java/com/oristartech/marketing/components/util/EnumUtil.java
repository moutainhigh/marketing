package com.oristartech.marketing.components.util;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * jdk1.8
 * @description: 枚举工具类
 * @author: zhangdongdong
 * @createDate: 2018/9/14 上午11:20
 * @Version: 1.0
 */
public class EnumUtil {

    /**
     * 根据条件获取枚举对象
     * @param className 枚举类
     * @param predicate 筛选条件
     * @param <T> 枚举类
     * @return
     */
    public static <T> Optional<T> getEnumObject(Class<T> className, Predicate<T> predicate) {
        if (!className.isEnum()) {
            throw  new RuntimeException("非枚举类型！");
        }

        return Arrays.stream(className.getEnumConstants()).filter(predicate).findAny();
    }

    /**
     * 根据条件判断枚举 是否包含 对应值的对象
     * @param className  枚举类
     * @param keyMapper 比较的属性
     * @param code  比较的值
     * @param <T> 枚举类
     * @param <K> 比较的值 类型
     * @return
     */
    public static <T,K> boolean contains(Class<T> className, Function<T, K> keyMapper, K code ){
        if (!className.isEnum()) {
            throw  new RuntimeException("非枚举类型！");
        }
       return Arrays.stream(className.getEnumConstants()).anyMatch(e->keyMapper.apply(e).equals(code));
    }

    /**
     * 根据条件获取枚举对象 map
     *
     * @param className 枚举类
     * @param keyMapper map的key 枚举类的属性
     * @param <T> 枚举类
     * @param <K> 枚举类的属性类型
     * @return
     */
    public static <T,K> Map<K,T>  getEnumMap(Class<T> className, Function<T, K> keyMapper) {
        if (!className.isEnum()) {
            throw  new RuntimeException("非枚举类型！");
        }

        return Arrays.stream(className.getEnumConstants()).collect(Collectors.toMap(keyMapper,a->a));
    }

}
