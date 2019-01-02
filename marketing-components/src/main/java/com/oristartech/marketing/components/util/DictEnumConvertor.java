package com.oristartech.marketing.components.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.EnumUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:DictEnumConvertor
 * @Description: 解决枚举返回json只存在code列表不包含desc的情况
 *
//                List<UserEnum> res = EnumUtils.getEnumList(UserEnum.class);
//
//                res.forEach(e ->{
//                    JSONObject j = new JSONObject();
//                    j.put("code",e.getCode());
//                    j.put("desc",e.getDesc());
//                    result.add(j);
//                });
 * @Author sunquanchao(272351724 @ qq.com)
 * @Date:2018/9/28 下午4:56
 * @Version 1.0
 **/

public class DictEnumConvertor {


    public static <E extends Enum<E>> List<E> getEnumList(final Class<E> enumClass) {
        List result = new ArrayList();
        List<E> res = EnumUtils.getEnumList(enumClass);
        res.forEach(e -> {
            try {
                JSONObject j = new JSONObject();

                Method code = e.getClass().getMethod("getCode");
                j.put("code", code.invoke(e).toString());

                Method desc = e.getClass().getMethod("getDesc");
                if(desc == null){
                    desc = e.getClass().getMethod("getValue");
                }
                j.put("desc", desc.invoke(e).toString());
                result.add(j);
            } catch (Exception ee) {
                // do nothing
            }

        });
        return result;
    }

}