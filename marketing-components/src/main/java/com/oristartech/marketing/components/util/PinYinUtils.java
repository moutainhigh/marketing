package com.oristartech.marketing.components.util;/**
 * @Auther: summerboy
 * @Date: 2018/9/12 18:10
 * @Description:
 */

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @description: TODO
 * @author: summerboy
 * @createDate: 2018/9/12
 * @Version: 1.0
 */
public class PinYinUtils {
    /**
     * 将汉字转换为全拼
     *
     * @param str
     * @return
     */
    public static String getPinYin(String str) {

        //将汉字转换为字符
        char[] chars = str.toCharArray();

        //创建与字符等长的字符串数组
        String[] strings = new String[chars.length];

        // 设置汉字拼音输出的格式 决定输出的拼音格式，比如大小写，是否带有音标
        HanyuPinyinOutputFormat hyformat = new HanyuPinyinOutputFormat();
        hyformat.setCaseType(HanyuPinyinCaseType.LOWERCASE);//小写字母
        hyformat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//去掉声调
        hyformat.setVCharType(HanyuPinyinVCharType.WITH_V);

        String divide = "";
        int length1 = chars.length;
        try {
            for (int i = 0; i < length1; i++) {
                // 判断能否为汉字字符
                if (Character.toString(chars[i]).matches("[\\u4E00-\\u9FA5]+")) {

                    strings = PinyinHelper.toHanyuPinyinStringArray(chars[i], hyformat);// 将汉字的几种全拼都存到strings数组中

                    divide += strings[0];// 取出该汉字全拼的第一种读音并连接到字符串divide后
                } else {
                    // 如果不是汉字字符，间接取出字符并连接到字符串divide后
                    divide += Character.toString(chars[i]);
                }
            }

        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }

        return divide;

    }

    /**
     * 提取每个汉字的首字母
     *
     * @param str
     * @return String
     */
    public static String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 将字符串转换成ASCII码
     *
     * @param cnStr
     * @return String
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        // 将字符串转换成字节序列
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            // 将每个字符转换成ASCII码
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

    public static void main(String[] args) {
        System.out.println(getPinYinHeadChar("你好"));// nh

        System.out.println(getPinYin("你好"));//nihao
        System.out.println("你好".length());
        System.out.println("nihao".length());
        System.out.println("ni你好hao".length());
    }



}
