package com.rabbitmq.pruducer;

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

}
