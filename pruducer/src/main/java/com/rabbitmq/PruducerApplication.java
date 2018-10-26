package com.rabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.rabbitmq.mapper")
@EnableScheduling
public class PruducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruducerApplication.class, args);
	}
}
