package com.oristartech.marketing.components.util;

import java.util.regex.Pattern;

/**
 * @description: 正则 验证工具类
 * @author: zhangdongdong
 * @createDate: 2018/9/26 下午2:03
 * @Version: 1.0
 */
public class RegexUtil {

    /**
     * 参考地址url: https://github.com/zly394/EmojiRegex
     * 包含所有 emoji 的正则表达式
     */
   public static final String EMOJI_REG=".*(?:[\\uD83C\\uDF00-\\uD83D\\uDDFF]|[\\uD83E\\uDD00-\\uD83E\\uDDFF]|[\\uD83D\\uDE00-\\uD83D\\uDE4F]|[\\uD83D\\uDE80-\\uD83D\\uDEFF]|[\\u2600-\\u26FF]\\uFE0F?|[\\u2700-\\u27BF]\\uFE0F?|\\u24C2\\uFE0F?|[\\uD83C\\uDDE6-\\uD83C\\uDDFF]{1,2}|[\\uD83C\\uDD70\\uD83C\\uDD71\\uD83C\\uDD7E\\uD83C\\uDD7F\\uD83C\\uDD8E\\uD83C\\uDD91-\\uD83C\\uDD9A]\\uFE0F?|[\\u0023\\u002A\\u0030-\\u0039]\\uFE0F?\\u20E3|[\\u2194-\\u2199\\u21A9-\\u21AA]\\uFE0F?|[\\u2B05-\\u2B07\\u2B1B\\u2B1C\\u2B50\\u2B55]\\uFE0F?|[\\u2934\\u2935]\\uFE0F?|[\\u3030\\u303D]\\uFE0F?|[\\u3297\\u3299]\\uFE0F?|[\\uD83C\\uDE01\\uD83C\\uDE02\\uD83C\\uDE1A\\uD83C\\uDE2F\\uD83C\\uDE32-\\uD83C\\uDE3A\\uD83C\\uDE50\\uD83C\\uDE51]\\uFE0F?|[\\u203C\\u2049]\\uFE0F?|[\\u25AA\\u25AB\\u25B6\\u25C0\\u25FB-\\u25FE]\\uFE0F?|[\\u00A9\\u00AE]\\uFE0F?|[\\u2122\\u2139]\\uFE0F?|\\uD83C\\uDC04\\uFE0F?|\\uD83C\\uDCCF\\uFE0F?|[\\u231A\\u231B\\u2328\\u23CF\\u23E9-\\u23F3\\u23F8-\\u23FA]\\uFE0F?).*";

    /**
     * 验证日期字符串 是否是yyyy-MM-dd 格式
     * 2018-08-18
     */
    public static final String DATE_YYYMMDD="[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";

    /**
     * 备案立项号 格式验证
     * 格式： 影剧备字[2013]第1263号
     *     影立合字（2014）第62号
     * @param code 备案立项号
     * @return true 通过验证  false 不匹配
     */
    public static boolean checkRemarkCode(String code) {
        if (code == null || code.trim().equals("")) {
            return false;
        }
        String regex = "影[\\u4e00-\\u9fa5]备字\\[[0-9]{4}\\]第[0-9]{3,}号";
        String regex2 = "影合立字（[0-9]{4}）第[0-9]{3,}号";
        return Pattern.matches(regex,code) || Pattern.matches(regex2,code);
    }

    /**
     * 影片专资编码 格式验证
     * 格式： 000001922018
     *
     * @param code 专资码
     * @return true 通过验证  false 不匹配
     */
    public static boolean checkMovieUnicode(String code){
        if (code == null || code.trim().equals("")) {
            return false;
        }
        String regex = "[0-9]{3}[A-Za-z0-9][0-9]{8}";
        return Pattern.matches(regex,code);
    }

    /**
     * 验证Email
     * @param email email地址，格式：zhangwuji@163.com，zhangwuji@xxx.com xxx代表邮件服务商
     * @return true 通过验证  false 不匹配
     */
    public static boolean checkEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }

        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }


    /**
     * 验证手机号码
     * @param mobile 移动、联通、电信运营商的号码段
     * @return true 通过验证  false 不匹配
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[345678]\\d{9}$";
        return Pattern.matches(regex,mobile);
    }

    /**
     * 验证日期字符串 是否是yyyy-MM-dd 格式
     * 2018-08-18
     * @param date
     * @return
     */
    public  static boolean validDateYYYMMDD(String date){
        if(date == null || date.trim().length()<=0){
            return false;
        }
        return Pattern.matches(DATE_YYYMMDD, date);
    }

    public static void main(String[] args) {
        String code = "影纪备字[2017]第071号";
        boolean rt = RegexUtil.checkRemarkCode(code);
        System.out.println(code+":"+rt);

        code = "影合立字（2018）第019号";
        rt = RegexUtil.checkRemarkCode(code);
        System.out.println(code+":"+rt);

        code = "000001922018";
        rt = RegexUtil.checkMovieUnicode(code);
        System.out.println(code+":"+rt);

        code = "zhangdongdong@163.com";
        rt = RegexUtil.checkEmail(code);
        System.out.println(code+":"+rt);

        code = "15566220509";
        rt = RegexUtil.checkMobile(code);
        System.out.println(code+":"+rt);

        code = "2018-08-18";
        rt = RegexUtil.validDateYYYMMDD(code);
        System.out.println(code+":"+rt);

        code="a于文文嗷a嗷?Ad";
        code="a文😄于";
        rt =   Pattern.matches(EMOJI_REG, code);
        System.out.println(code+":"+rt);
    }

}
