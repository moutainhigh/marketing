package com.oristartech.marketing.components.listener;

import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.oristartech.marketing.components.config.properties.RocketmqConsumerproperties;

/**
 * @Auther: hexu
 * @Date: 2018/9/12 17:03
 * @Description:
 */
@Component
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {

    private Logger logger = LoggerFactory.getLogger(MQConsumeMsgListenerProcessor.class);

    @Autowired
    private RocketmqConsumerproperties rocketmqConsumerproperties;

    @Autowired(required = false)
    RocketmqConsumer rocketmqConsumer;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if (CollectionUtils.isEmpty(msgs)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = msgs.get(0);


        //TODO 判断该消息是否重复消费（RocketMQ不保证消息不重复，如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重）
        //TODO 获取该消息重试次数
        int reconsume = messageExt.getReconsumeTimes();
        if (reconsume == 3) {//消息已经重试了3次，如果不需要再次消费，则返回成功
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        //TODO 处理对应的业务逻辑
        try {
            String data = new String(messageExt.getBody());
            rocketmqConsumer.excute(data, messageExt.getTopic());
        } catch (Exception e) {
            logger.error("mq消费失败:::topic:{}:::body:{}", messageExt.getTopic(), new String(messageExt.getBody()));
            logger.error("", e);
        }

        // 如果没有return success ，consumer会重新消费该消息，直到return success
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
