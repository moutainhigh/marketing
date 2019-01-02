package com.oristartech.marketing.components.util;


import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description: list 转换成map 工具类
 *    要求：jdk版本 大于等于1.8
 * @author: zhangdongdong
 * @createDate: 2018/8/27 下午7:28
 * @Version: 1.0
 */
public class List2Map<T,K> {


    /**
     * List -> Map
     * 需要注意的是：
     *  toMap 如果集合对象有重复的key，会报错Duplicate key ....
     *  例如：apple1,apple12的id都为1。
     * @param list 需要处理的对象列表
     * @param keyMapper 根据哪个属性 分组
     * @param <K> 属性类型
     * @param <T> 元素类型
     * @return Map<K,T>
     */
    public static <K, T> Map<K,T> toMap(List<T> list, Function< T, K> keyMapper){
        return list.stream().collect(Collectors.toMap(keyMapper,Function.identity()));
    }

    /**
     * List -> Map
     * 需要注意的是：
     *  toMap  如果有重复的key, 根据 合并函数 选择保留哪一个
     *  例如：(k1, k2)->k1 apple1,apple12的id都为1。
     * @param list 需要处理的对象列表
     * @param keyMapper 根据哪个属性 分组
     * @param mergeFunction 合并函数
     * @param <K> 属性类型
     * @param <T> 元素类型
     * @return Map<K,T>
     */
    public static <K, T> Map<K,T> toMap(List<T> list, Function< T, K> keyMapper,
                                         BinaryOperator<T> mergeFunction){
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity(), mergeFunction));
    }

    /**
     * List里面的对象元素，以某个属性来分组. 不允许null key
     * 例如:以id分组，将id相同的放在一起
     * @param list 需要处理的对象列表
     * @param keyMapper 根据哪个属性 分组
     * @param <K> 属性类型
     * @param <T> 元素类型
     * @return  Map<K,List<T>>
     */
    public static <K, T> Map<K,List<T>> groupingBy(List<T> list,Function< T, K> keyMapper){
        return list.stream().collect(Collectors.groupingBy(keyMapper));
    }

    /**
     * List里面的对象元素，以某个属性来分组. 允许null key
     * 例如:以id分组，将id相同的放在一起
     * @param list 需要处理的对象列表
     * @param keyMapper 根据哪个属性 分组
     * @param <K> 属性类型
     * @param <T> 元素类型
     * @return  Map<K,List<T>>
     */
    public static <K, T> Map<K,List<T>> groupingByWithNullKeys(List<T> list,Function< T, K> keyMapper){
        //分组之前先过滤null key
        List<T> nullList = list.stream().filter(p -> keyMapper.apply(p) == null).collect(Collectors.toList());
        Map<K, List<T>> map = list.stream().filter(p -> keyMapper.apply(p) != null).collect(Collectors.groupingBy(keyMapper));
        if(nullList!= null && !nullList.isEmpty()){
            map.put(null, nullList);
        }
        return  map;
    }

}