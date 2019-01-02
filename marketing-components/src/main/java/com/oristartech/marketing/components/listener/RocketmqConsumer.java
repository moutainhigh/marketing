package com.oristartech.marketing.components.listener;


/**
 * @Auther: hexu
 * @Date: 2018/9/12 17:36
 * @Description:
 */
public interface RocketmqConsumer {

    void excute(String jsonData, String topic);

}
