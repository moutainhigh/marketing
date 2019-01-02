package com.oristartech.marketing.components.constant.enums;


/**
 * @description: 影片类型
 * @author: zhangdongdong
 * @createDate: 2018/9/3 下午2:16
 * @Version: 1.0
 */
public enum MovieTypeEnum {

    DONGZUO("DONGZUO", "动作"),
    ZHANZHENG("ZHANZHENG", "战争"),
    JUNSHI("JUNSHI", "军事"),
    JUQING("JUQING", "剧情"),
    FANZUI("FANZUI", "犯罪"),
    XIJU("XIJU", "喜剧"),
    XUANYI("XUANYI", "悬疑"),
    AIQING("AIQING", "爱情"),
    KEHUAN("KEHUAN", "科幻"),
    HUAIJIU("HUAIJIU", "怀旧"),
    QIHUAN("QIHUAN", "奇幻"),
    MAOXIAN("MAOXIAN", "冒险"),
    YUNDONG("YUNDONG", "运动"),
    JINGSONG("JINGSONG", "惊悚"),
    DONGHUA("DONGHUA", "动画"),
    QINGCHUN("QINGCHUN", "青春"),
    ZHUANJI("ZHUANJI", "传记"),
    GONGLU("GONGLU", "公路"),
    QINQING("QINQING", "亲情"),
    YINYUE("YINYUE", "音乐"),
    ZAINAN("ZAINAN", "灾难"),
    CHUANYUE("CHUANYUE", "穿越"),
    GEWU("GEWU", "歌舞"),
    HESUI("HESUI", "贺岁"),
    ZHENRENXIU("ZHENRENXIU", "真人秀"),
    ZONGYIDADIANYING("ZONGYIDADIANYING", "综艺大电影"),
    JINGFEI("JINGFEI", "警匪"),
    WENYI("WENYI", "文艺"),
    DONGWU("DONGWU", "动物"),
    GUZHUANG("GUZHUANG", "古装"),
    LIZHI("LIZHI", "励志"),
    YOUQING("YOUQING", "友情"),
    XUANHUAN("XUANHUAN", "玄幻"),
    ERTONG("ERTONG", "儿童"),
    ZHICHANG("ZHICHANG", "职场"),
    JILUPIAN("JILUPIAN", "纪录片"),
    ZHUXUANLU("ZHUXUANLU", "主旋律"),
    GEMING("GEMING", "革命"),
    WUXIA("WUXIA", "武侠"),
    XINLI("XINLI", "心理"),
    XIBU("XIBU", "西部"),
    KONGBU("KONGBU", "恐怖"),
    GONGFU("GONGFU", "功夫"),
    QIANGZHAN("QIANGZHAN", "枪战"),
    NONGCUN("NONGCUN", "农村"),
    TONGXING("TONGXING", "同性"),
    KEJIAO("KEJIAO", "科教片"),
    FANFU("FANFU", "反腐"),
    ZHENTAN("ZHENTAN", "侦探"),
    DUSHI("DUSHI", "都市"),
    LINGYI("LINGYI", "灵异"),
    HEISEYOUMO("HEISEYOUMO", "黑色幽默"),
    HEIBANG("HEIBANG", "黑帮"),
    YISHUPIAN("YISHUPIAN", "艺术片"),
    WUTAIYISHUPIAN("WUTAIYISHUPIAN", "舞台艺术片"),
    SHENHUA("SHENHUA", "神话"),
    JUNLU("JUNLU", "军旅"),
    NUXING("NUXING", "女性"),
    JINGJI("JINGJI", "竞技"),
    MINZU("MINZU", "民族"),
    XIQU("XIQU", "戏曲"),
    WANHUI("WANHUI", "晚会");

    private String code;
    private String desc;

    MovieTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static MovieTypeEnum valueOfDesc(String desc) {
        if (desc == null || desc.equals("")) {
            return null;
        }
        for (MovieTypeEnum value : values()) {
            if (value.getDesc().equals(desc)) {
                return value;
            }
        }
        return null;
    }


}
