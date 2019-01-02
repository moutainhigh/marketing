package com.oristartech.marketing.components.tips;

/**
 * 返回给前台的成功提示
 *
 * @author wangweiheng
 * @date 2018-08-17
 */
public class SuccessTip extends Tip {

    public SuccessTip() {
        super.code = 200;
        super.message = "操作成功";
    }
}
