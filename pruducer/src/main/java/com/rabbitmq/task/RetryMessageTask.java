package com.rabbitmq.task;

import com.rabbitmq.constant.Constant;
import com.rabbitmq.mapper.BrokerMessageLogMapper;
import com.rabbitmq.model.BrokerMessageLogPO;
import com.rabbitmq.model.Order;
import com.rabbitmq.pruducer.OrderSender;
import com.rabbitmq.util.FastJsonConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Barry
 * 2018/10/25 15:44
 */
@Component
@Lazy(false)
public class RetryMessageTask {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderSender orderSender;
    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * 启动完成3秒后开始执行，每隔10秒执行一次
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void retrySend() {
        logger.debug("重发消息定时任务开始");
        // 查询 status = 0 和 timeout 的消息日志
        List<BrokerMessageLogPO> pos = this.brokerMessageLogMapper.listSendFailureAndTimeoutMessage();
        for (BrokerMessageLogPO po : pos) {
            logger.debug("处理消息日志：{}",po);
            if (po.getTryCount() >= Constant.MAX_RETRY_COUNT) {
                // 更新状态为失败
                BrokerMessageLogPO messageLogPO = new BrokerMessageLogPO();
                messageLogPO.setMessageId(po.getMessageId());
                messageLogPO.setStatus(Constant.SEND_FAILURE);
                this.brokerMessageLogMapper.changeBrokerMessageLogStatus(messageLogPO);
            } else {
                // 进行重试，重试次数+1
                this.brokerMessageLogMapper.updateRetryCount(po);
                Order reSendOrder = FastJsonConvertUtils.convertJsonToObject(po.getMessage(), Order.class);
                try {
                    this.orderSender.send(reSendOrder);
                } catch (Exception ex) {
                    // 异常处理
                    logger.error("消息发送异常：{}", ex);
                }
            }
        }
        logger.debug("重发消息定时任务结束");
    }
}
