package com.sc.rabbit.client.json;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

interface JSONMessage {
	String toJSON();
}

//entity class
class Message implements JSONMessage {
	// data variables
	private int msgNo;
	private String from;
	private String to;
	private String header;
	private String content;

	// create GSON parser object
	private static Gson gson = new Gson();

	public int getMsgNo() {
		return msgNo;
	}

	public void setMsgNo(int msgNo) {
		this.msgNo = msgNo;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	// object to json
	@Override
	public String toJSON() {
		return gson.toJson(this);
	}

	// Json to Object
	public static Message fromJSON(String msg) {
		Gson gson = new Gson();
		return (Message) gson.fromJson(msg, Message.class);
	}

	@Override
	public String toString() {
		return "Message [msgNo=" + msgNo + ", from=" + from + ", to=" + to + ", header=" + header + ", content="
				+ content + "]";
	}

}

public class PublishJSON {

	private final static String QUEUE_NAME = "message1.queue";
	private final static String EXCHANGE_NAME = "message1.exchange";
	private static final String ROUTING_KEY = "";

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		String hostName = "localhost";
		int portNumber = 5672;
		factory.setHost(hostName);
		factory.setPort(portNumber);

		try {
			Connection connection = factory.newConnection();

			Channel channel = connection.createChannel();

			channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);

			// create Queue :
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);

			// Binding
			channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

			// Publishing JSON

			Message message = new Message();
			message.setFrom("Subramanian");
			message.setTo("John");
			message.setHeader("News From Me");
			message.setContent("Greetings from Rabbit");

			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null,message.toJSON().getBytes());

			System.out.println("Message Published");

		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
