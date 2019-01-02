package com.oristartech.marketing.components.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import com.oristartech.marketing.components.config.properties.RocketmqProducerProperties;


/**
 * @Auther: hexu
 * @Date: 2018/9/12 16:43
 * @Description:
 */
@SpringBootConfiguration
@ConditionalOnProperty(prefix = "rocketmq.producer", name = "isOnOff", havingValue = "on", matchIfMissing = true)
public class MQProducerConfiguration {

    private Logger logger = LoggerFactory.getLogger(MQProducerConfiguration.class);

    //    /**
//     * 发送同一类消息的设置为同一个group，保证唯一,默认不需要设置，rocketmq会使用ip@pid(pid代表jvm名字)作为唯一标示
//     */
//    @Value("${rocketmq.producer.groupName}")
//    private String groupName;
//    @Value("${rocketmq.producer.namesrvAddr}")
//    private String namesrvAddr;
//    /**
//     * 消息最大大小，默认4M
//     */
//    @Value("${rocketmq.producer.maxMessageSize}")
//    private Integer maxMessageSize ;
//    /**
//     * 消息发送超时时间，默认3秒
//     */
//    @Value("${rocketmq.producer.sendMsgTimeout}")
//    private Integer sendMsgTimeout;
//    /**
//     * 消息发送失败重试次数，默认2次
//     */
//    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
//    private Integer retryTimesWhenSendFailed;

    @Autowired
    private RocketmqProducerProperties rocketmqProducerProperties;

    @Bean
    public DefaultMQProducer getRocketMQProducer() {
        if (rocketmqProducerProperties.getIsOnOff() == null || !rocketmqProducerProperties.getIsOnOff().equals("on")) {
            return null;
        }
        if (StringUtils.isEmpty(rocketmqProducerProperties.getGroupName())) {
            return null;
        }
        if (StringUtils.isEmpty(rocketmqProducerProperties.getNamesrvAddr())) {
            return null;
        }
        DefaultMQProducer producer;
        producer = new DefaultMQProducer(rocketmqProducerProperties.getGroupName());
        producer.setNamesrvAddr(rocketmqProducerProperties.getNamesrvAddr());
        //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
        //producer.setInstanceName(instanceName);
        if (rocketmqProducerProperties.getMaxMessageSize() != null) {
            producer.setMaxMessageSize(rocketmqProducerProperties.getMaxMessageSize());
        }
        if (rocketmqProducerProperties.getSendMsgTimeout() != null) {
            producer.setSendMsgTimeout(rocketmqProducerProperties.getSendMsgTimeout());
        }
        //如果发送消息失败，设置重试次数，默认为2次
        if (rocketmqProducerProperties.getRetryTimesWhenSendFailed() != null) {
            producer.setRetryTimesWhenSendFailed(rocketmqProducerProperties.getRetryTimesWhenSendFailed());
        }

        try {
            producer.start();

            logger.info("MQ producer 启动成功 组:{} ", producer.getProducerGroup());

        } catch (MQClientException e) {
        }
        return producer;
    }


}
