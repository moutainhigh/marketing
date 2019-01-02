package com.oristartech.marketing.components.util;


import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.oristartech.marketing.components.constant.RestResponse;

/**
 *
 */
public class EmojiUtil {

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (source == null) {
            return false;
        }
        int len = source.length();

        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            boolean isEmoji = isEmojiCharacter(hs);
            if (isEmoji) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmojiCharacter(int codePoint) {

        return (codePoint >= 0x2600 && codePoint <= 0x27BF) // 杂项符号与符号字体
                || codePoint == 0x303D
                || codePoint == 0x2049
                || codePoint == 0x203C
                || (codePoint >= 0x2000 && codePoint <= 0x200F)//
                || (codePoint >= 0x2028 && codePoint <= 0x202F)//
                || codePoint == 0x205F //
                || (codePoint >= 0x2065 && codePoint <= 0x206F)//
                /* 标点符号占用区域 */
                || (codePoint >= 0x2100 && codePoint <= 0x214F)// 字母符号
                || (codePoint >= 0x2300 && codePoint <= 0x23FF)// 各种技术符号
                || (codePoint >= 0x2B00 && codePoint <= 0x2BFF)// 箭头A
                || (codePoint >= 0x2900 && codePoint <= 0x297F)// 箭头B
                || (codePoint >= 0x3200 && codePoint <= 0x32FF)// 中文符号
                || (codePoint >= 0xD800 && codePoint <= 0xDFFF)// 高低位替代符保留区域
                || (codePoint >= 0xE000 && codePoint <= 0xF8FF)// 私有保留区域
                || (codePoint >= 0xFE00 && codePoint <= 0xFE0F)// 变异选择器
                || codePoint >= 0x10000; // Plane在第二平面以上的，char都不可以存，全部都转
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        source = source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            } else {
                buf.append("*");
            }
        }

        if (buf == null) {
            return source;//如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }

    }


    /**
     * javaBean 属性 是 Sting类型，javaBean类型、Collection<String>或者Collection<JavaBean>类型中String类型
     * 是否包含表情符号。暂不支持对Map类型属性做处理
     * @param obj
     * @return
     */
    public static boolean isContainsEmoji(Object obj)  {
        if(obj == null){
            return false;
        }
        if(!isJavaBean(obj.getClass())){
            throw new IllegalArgumentException("param must be a javaBean!");
        }
        Class<?> sourceType = obj.getClass();
        while (!Object.class.equals(sourceType) && sourceType != null) {
            Field[] fields = sourceType.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldValue = null;
                try {
                    fieldValue = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

//                System.out.print(field.getType().getName()+"\t\t");
//                System.out.print(field.getName());
//                System.out.println(" "+fieldValue);

                if(fieldValue == null){
                    continue;
                }
                if(field.getType().isPrimitive()){
                    continue;
                }
                if(isWrapperPrimitive(field.getType())){
                    continue;
                }
                if(field.getType().equals(String.class)){
                    //System.out.println("field string:"+field.getType().getName());
                    boolean isContains = containsEmoji((String) fieldValue);
                    if(isContains){
                        return true;
                    }
                    continue;
                }
                if(isJavaBean(field.getType())){
                    boolean isContains = isContainsEmoji(fieldValue);
                    if(isContains){
                        return true;
                    }
                }
                else if(fieldValue instanceof Collection){
                    Collection list = (Collection) fieldValue;
                    Iterator iterator = list.iterator();
                    while (iterator.hasNext()){
                        Object a = iterator.next();
                        if(a.getClass().equals(String.class)){
                            boolean isContains = containsEmoji((String) a);
                            if(isContains){
                                return true;
                            }
                            continue;
                        }
                        if(isWrapperPrimitive(a.getClass())){
                            continue;
                        }
                        if(isJavaBean(a.getClass())){
                            boolean isContains = isContainsEmoji(a);
                            if(isContains){
                                return true;
                            }
                        }
                    }

                }
            }
            sourceType = sourceType.getSuperclass();
        }
        return false;
    }

    /**
     * 判断是否是基本类型的包装类型
     * @param c
     * @return
     */
    private static boolean isWrapperPrimitive(Class c){
        try {
            return  ((Class)c.getDeclaredField("TYPE").get(null)).isPrimitive();
        } catch (IllegalAccessException e) {
            return false;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    /**
     * 判断类是否一个javaBean
     * @param type
     * @return
     */
    private static final boolean isJavaBean(Type type){
        if(null == type )
            throw new IllegalArgumentException("type must be not null");
        // 根据 getDeserializer 返回值类型判断是否为 java bean 类型
        return getDeserializer(type) instanceof JavaBeanDeserializer;
    }

    private static final ObjectDeserializer getDeserializer(Type type){
        return ParserConfig.global.getDeserializer(type);
    }


    public static void main(String[] args) {
        String source = "-》！¥#@，。/】【】|()（）……^{}`*,.<>?？🍂";
        //   String source = "aa?s.,-1!~`;'";
        int a = 1;
        boolean rt;
        // rt = containsEmoji(source);

        //System.out.println("rt:" + rt);
       // a = 1;
        //  rt = isContainsEmoji(a);
        //  System.out.println("rt:" + rt);

        RestResponse restResponse = new RestResponse(1, "➡️");
        rt = isContainsEmoji(restResponse);
        System.out.println("rt:" + rt);
        System.out.println("----------------->");

        List<String> dataList = new ArrayList<>();
        dataList.add("a");
        dataList.add("b");
        dataList.add("😄");
        restResponse = RestResponse.createSuccessResult(dataList);
        rt = isContainsEmoji(restResponse);

        System.out.println("rt:" + rt);


    }
}