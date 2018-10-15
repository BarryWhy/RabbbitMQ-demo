package com.rabbitmq.customer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.model.Order;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Barry
 * 2018/10/15 16:46
 */
@Component
public class OrderReceiver {

    @RabbitListener(
            bindings = @QueueBinding(
            value = @Queue(value = "order-queue", durable = "true"),
            exchange = @Exchange(value = "order-exchange", durable = "true", type = "topic"),
            key = "order.*"
        )
    )
    @RabbitHandler
    public void onReveiveMessage(@Payload Order order,
                                 @Headers Map<String, Object> headers, Channel channel) throws Exception{
        //消息的消费者处理
        System.out.println("------收到消息，开始消费-------");
        System.out.println("订单ID： " +  order.getId());
        System.out.println("订单name： " +  order.getName());

        //配置文件中如果配置了acknowledge-mode为manual手工签收需要
//        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
//        channel.basicAck(deliveryTag, false); //第二个参数代表是否批量签收
    }
}
