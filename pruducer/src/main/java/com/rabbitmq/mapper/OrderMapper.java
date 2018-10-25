package com.rabbitmq.mapper;

import com.rabbitmq.model.Order;
import org.springframework.stereotype.Repository;

/**
 * Created by Barry
 * 2018/10/25 15:40
 */
@Repository
public interface OrderMapper {

    /**
     * 新增
     *
     * @param order 订单
     */
    void insert(Order order);

}
