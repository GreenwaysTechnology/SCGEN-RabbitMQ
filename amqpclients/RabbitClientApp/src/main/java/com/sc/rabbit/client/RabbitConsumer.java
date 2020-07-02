package com.sc.rabbit.client;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class RabbitConsumer {

	private static final String QUEUE_NAME = "scgenQ2";
	private static final String ROUTING_KEY = "";

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		String hostName = "localhost";
		int portNumber = 5672;
		factory.setHost(hostName);
		factory.setPort(portNumber);

		try {
			// create /establish amqp protocal/tcpip connection
			Connection connection = factory.newConnection();

			// Get Channel(Single Session through which you send message)
			Channel channel = connection.createChannel();
				System.out.println(" Waiting for Messages. If you want exit, you can Press CTRL+C");
			// Consume : Async programming : callbacks
			DeliverCallback deliverCallback = (ConsumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");
				System.out.println("Got : " + message);
			};
			// pass the callback function to basicConsume
			//basicConsume(queueName,successCallback,failureCallback)
			channel.basicConsume(QUEUE_NAME, deliverCallback, ctag -> {
				System.out.println("Consumer canceld due to some reasons");
			});

		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
