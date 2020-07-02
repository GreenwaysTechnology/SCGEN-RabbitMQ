package com.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

//public class PublishToQueue {
//    private final static String QUEUE_NAME = "hello";
//
//    public static void main(String[] args) {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection;
//
//        try {
//            connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            String message = "Hello World Rabbit MQ!";
//            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
//            System.out.println(" [x] Sent '" + message + "'");
//        } catch (Exception es) {
//
//        }
//    }
//}

//Direct
//public class PublishToQueue {
//    private final static String EXCHANGE_NAME = "demo.exchange";
//    private final static String routingKey = "demokey";
//
//    public static void main(String[] args) {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection;
//
//        try {
//            connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            byte[] messageBodyBytes = "Hello, world!".getBytes();
//            channel.basicPublish(EXCHANGE_NAME, routingKey, null, messageBodyBytes);
//        } catch (Exception es) {
//            System.out.println(es);
//        }
//    }
//}

//fanout
//public class PublishToQueue {
//    private final static String EXCHANGE_NAME = "demo.fanout";
//    private final static String routingKey = "";
//
//    public static void main(String[] args) {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection;
//
//        try {
//            connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            byte[] messageBodyBytes = "Hello, world!".getBytes();
//            channel.basicPublish(EXCHANGE_NAME, routingKey, null, messageBodyBytes);
//            System.out.println(" [x] Sent '" + messageBodyBytes + "'");
//
//        } catch (Exception es) {
//            System.out.println(es);
//        }
//    }
//}
//topics
//public class PublishToQueue {
//    private final static String EXCHANGE_NAME = "demo.topic";
//    private final static String routingKey = "topic.#";
//
//    public static void main(String[] args) {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection;
//
//        try {
//            connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            byte[] messageBodyBytes = "Hello, world!".getBytes();
//            channel.basicPublish(EXCHANGE_NAME, routingKey, null, messageBodyBytes);
//            System.out.println(" [x] Sent '" + messageBodyBytes + "'");
//
//        } catch (Exception es) {
//            System.out.println(es);
//        }
//    }
//}
//headers
public class PublishToQueue {
    private final static String EXCHANGE_NAME = "demo.headers";
    private final static String routingKey = "formate=pdf,type=report";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;

        try {
            connection = factory.newConnection();
            Channel channel = connection.createChannel();
            byte[] messageBodyBytes = "Hello, world!".getBytes();
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, messageBodyBytes);
            System.out.println(" [x] Sent '" + messageBodyBytes + "'");

        } catch (Exception es) {
            System.out.println(es);
        }
    }
}