package com.rabbitmq.pruducer;

import com.rabbitmq.constant.Constant;
import com.rabbitmq.mapper.BrokerMessageLogMapper;
import com.rabbitmq.model.BrokerMessageLogPO;
import com.rabbitmq.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Barry
 * 2018/10/15 15:46
 */
@Component
public class OrderSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Order order) throws Exception{
        //convertAndSend(Exchange名，路由key，消息体，消息唯一id)
        rabbitTemplate.convertAndSend("order-exchange", "order.routeKey",
                order, new CorrelationData(order.getMessageId()));
    }


    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;
    /**
     * 回调方法：confirm确认
     */
    private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("correlationData：" + correlationData);
            String messageId = correlationData.getId();
            if (ack) {
                // 如果confirm返回成功，则进行更新
                BrokerMessageLogPO messageLogPO = new BrokerMessageLogPO();
                messageLogPO.setMessageId(messageId);
                messageLogPO.setStatus(Constant.SEND_SUCCESS);
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageLogPO);
            } else {
                // 失败则进行具体的后续操作：重试或者补偿等
                System.out.println("异常处理...");
            }
        }
    };
}
