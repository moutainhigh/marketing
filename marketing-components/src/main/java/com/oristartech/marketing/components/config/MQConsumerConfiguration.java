package com.oristartech.marketing.components.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.oristartech.marketing.components.config.properties.RocketmqConsumerproperties;
import com.oristartech.marketing.components.listener.MQConsumeMsgListenerProcessor;

/**
 * @Auther: hexu
 * @Date: 2018/9/12 16:50
 * @Description:
 */
@SpringBootConfiguration
@ConditionalOnProperty(prefix = "rocketmq.consumer", name = "isOnOff", havingValue = "on", matchIfMissing = true)
public class MQConsumerConfiguration {

//    @Value("${rocketmq.consumer.namesrvAddr}")
//    private String namesrvAddr;
//    @Value("${rocketmq.consumer.groupName}")
//    private String groupName;
//    @Value("${rocketmq.consumer.consumeThreadMin}")
//    private int consumeThreadMin;
//    @Value("${rocketmq.consumer.consumeThreadMax}")
//    private int consumeThreadMax;
//    @Value("${rocketmq.consumer.topics}")
//    private String topics;
//    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
//    private int consumeMessageBatchMaxSize;


    private Logger logger = LoggerFactory.getLogger(MQConsumerConfiguration.class);

    @Autowired
    private RocketmqConsumerproperties rocketmqConsumerproperties;

    @Autowired
    private MQConsumeMsgListenerProcessor mqMessageListenerProcessor;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() {

        if (rocketmqConsumerproperties.getIsOnOff() == null || !rocketmqConsumerproperties.getIsOnOff().equals("on")) {
            return null;
        }

        if (StringUtils.isEmpty(rocketmqConsumerproperties.getGroupName())) {
//            throw new RocketMQException(RocketMQErrorEnum.PARAMM_NULL,"groupName is null !!!",false);
            return null;
        }
        if (StringUtils.isEmpty(rocketmqConsumerproperties.getNamesrvAddr())) {
//            throw new RocketMQException(RocketMQErrorEnum.PARAMM_NULL,"namesrvAddr is null !!!",false);
            return null;
        }
        if (StringUtils.isEmpty(rocketmqConsumerproperties.getTopics())) {
//            throw new RocketMQException(RocketMQErrorEnum.PARAMM_NULL,"topics is null !!!",false);
            return null;
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketmqConsumerproperties.getGroupName());
        consumer.setNamesrvAddr(rocketmqConsumerproperties.getNamesrvAddr());
        consumer.setConsumeThreadMin(rocketmqConsumerproperties.getConsumeThreadMin());
        consumer.setConsumeThreadMax(rocketmqConsumerproperties.getConsumeThreadMax());
        consumer.registerMessageListener(mqMessageListenerProcessor);
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        /**
         * 设置消费模型，集群还是广播，默认为集群
         */
        consumer.setMessageModel(MessageModel.BROADCASTING);
        /**
         * 设置一次消费消息的条数，默认为1条
         */
        consumer.setConsumeMessageBatchMaxSize(rocketmqConsumerproperties.getConsumeMessageBatchMaxSize());
        try {
            /**
             * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
             */
            String[] topicTagsArr = rocketmqConsumerproperties.getTopics().split(";");
            for (String topicTags : topicTagsArr) {
                String[] topicTag = topicTags.split("~");
                consumer.subscribe(topicTag[0], topicTag[1]);
            }
            consumer.start();
            String json = JSON.toJSONString(consumer.getSubscription());
            logger.info("MQ consumer 启动成功{}", topicTagsArr);
//            LOGGER.info("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}",groupName,topics,namesrvAddr);
        } catch (MQClientException e) {
//            LOGGER.error("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}",groupName,topics,namesrvAddr,e);
//            throw new MQClientException(e);
        }
        return consumer;
    }

}
