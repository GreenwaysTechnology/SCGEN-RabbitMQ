package com.scgen.amqp.spring.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.scgen.amqp.spring.enity.Employee;


@Component
public class RabbitMQConsumer {

	@RabbitListener(queues = "${javainuse.rabbitmq.queue}")
	public void recievedMessage(Employee employee) {
		System.out.println("Recieved Message From RabbitMQ: " + employee);
	}
}