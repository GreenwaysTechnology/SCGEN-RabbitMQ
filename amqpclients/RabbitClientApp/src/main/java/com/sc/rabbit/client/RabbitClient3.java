package com.sc.rabbit.client;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitClient3 {
	private static final String EXCHANGE_NAME = "scgenexchange";
	private static final String QUEUE_NAME = "scgenQ1";
	private static final String ROUTING_KEY = "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// factory declation
		ConnectionFactory factory = new ConnectionFactory();
		String hostName = "localhost";
		int portNumber = 5672;
		factory.setHost(hostName);
		factory.setPort(portNumber);
//		factory.setVirtualHost(virtualHost);
//		factory.setUsername(username);
//		factory.setPassword(password);

		try {
			// create /establish amqp protocal/tcpip connection
			Connection connection = factory.newConnection();

			// Get Channel(Single Session through which you send message)
			Channel channel = connection.createChannel();
			// Communication with Exchange and Queues

			channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);

			// create Queue :
			channel.queueDeclare(QUEUE_NAME, true, true, false, null);

			// Binding
			channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

			System.out.println(QUEUE_NAME + EXCHANGE_NAME + " Binding  Created!");

		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
