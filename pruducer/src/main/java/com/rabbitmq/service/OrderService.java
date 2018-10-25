package com.rabbitmq.service;

import com.rabbitmq.constant.Constant;
import com.rabbitmq.mapper.BrokerMessageLogMapper;
import com.rabbitmq.mapper.OrderMapper;
import com.rabbitmq.model.BrokerMessageLogPO;
import com.rabbitmq.model.Order;
import com.rabbitmq.pruducer.OrderSender;
import com.rabbitmq.util.FastJsonConvertUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Barry
 * 2018/10/25 15:34
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;
    @Autowired
    private OrderSender orderSender;

    /**
     * 创建订单
     *
     * @param order 订单
     */
    public void create(Order order) throws Exception {
        // 当前时间
        Date orderTime = new Date();
        // 业务数据入库
        this.orderMapper.insert(order);
        // 消息日志入库
        BrokerMessageLogPO messageLogPO = new BrokerMessageLogPO();
        messageLogPO.setMessageId(order.getMessageId());
        messageLogPO.setMessage(FastJsonConvertUtils.convertObjectToJson(order));
        messageLogPO.setTryCount(0);
        messageLogPO.setStatus(Constant.SENDING);
        messageLogPO.setNextRetry(DateUtils.addMinutes(orderTime, Constant.ORDER_TIMEOUT));
        this.brokerMessageLogMapper.insert(messageLogPO);
        // 发送消息
        this.orderSender.send(order);
    }
}
