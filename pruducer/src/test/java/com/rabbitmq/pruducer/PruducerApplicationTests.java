package com.rabbitmq.pruducer;

import com.rabbitmq.model.Order;
import com.rabbitmq.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PruducerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private OrderSender orderSender;

	@Test
	public void testSend() throws Exception{
		Order order = new Order("201810150000002", "订单测试2", UUID.randomUUID().toString());
		orderSender.send(order);
	}

	@Autowired
	private OrderService orderService;
	@Test
	public void testCreateOrder() throws Exception {
		Order order = new Order();
		order.setId(String.valueOf(System.currentTimeMillis()));
		order.setName("测试创建订单");
		order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString().replaceAll("-",""));
		this.orderService.create(order);
	}
}
