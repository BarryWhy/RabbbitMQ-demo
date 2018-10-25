package com.rabbitmq.mapper;

import com.rabbitmq.model.BrokerMessageLogPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Barry
 * 2018/10/25 15:38
 */
@Repository
public interface BrokerMessageLogMapper {

    /**
     * 创建消息日志
     *
     * @param messageLogPO 消息日志
     */
    void insert(BrokerMessageLogPO messageLogPO);

    /**
     * 更新消息状态
     *
     * @param messageLogPO 消息日志
     */
    void changeBrokerMessageLogStatus(BrokerMessageLogPO messageLogPO);

    /**
     * 查询消息状态为0 且 已经超时的消息
     *
     * @return 消息日志集合
     */
    List<BrokerMessageLogPO> listSendFailureAndTimeoutMessage();

    /**
     * 更新重试次数+1
     *
     * @param po 消息日志
     */
    void updateRetryCount(BrokerMessageLogPO po);
}
