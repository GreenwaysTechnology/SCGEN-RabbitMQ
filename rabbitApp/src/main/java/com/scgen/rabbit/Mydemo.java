package com.scgen.rabbit;

import com.google.gson.Gson;
import com.rabbitmq.client.*;

import java.nio.charset.Charset;

//create Exchange through code
//public class Mydemo {
//    private final static String EXCHANGE_NAME = "aggreements";
//
//    public static void main(String[] args) {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        try {
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            //Use existing Exchange, if not will throw error
//           // channel.exchangeDeclarePassive(EXCHANGE_NAME);
//            channel.exchangeDeclare(EXCHANGE_NAME,"direct",true);
//            System.out.println("Exchange Created");
//        } catch (Exception es) {
//            System.out.println(es);
//        }
//    }
//}
//create Queue
//public class Mydemo {
//    private final static String QUEUE_NAME = "QueueA";
//
//    public static void main(String[] args) {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        try {
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            channel.queueDeclare(QUEUE_NAME, true, true, false, null);
//            System.out.println("Queue Created");
//        } catch (Exception es) {
//            System.out.println(es);
//        }
//    }
//}

//declare exchange,queue,bind them
//public class Mydemo {
//    private final static String QUEUE_NAME = "QueueA";
//    private final static String EXCHANGE_NAME = "aggreements";
//    private final static String ROUTING_KEY = "";
//
//
//    public static void main(String[] args) {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        try {
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            //create exchange
//            channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
//            //create queue
//            channel.queueDeclare(QUEUE_NAME, true, true, false, null);
//            //bind them : fanout routingKey is empty
//            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
//            System.out.println(EXCHANGE_NAME + QUEUE_NAME + "binding done");
//        } catch (Exception es) {
//            System.out.println(es);
//        }
//    }
//}

//declare exchange,queue,bind them, publish message

//public class Mydemo {
//    private final static String QUEUE_NAME = "QueueA";
//    private final static String EXCHANGE_NAME = "aggreements";
//    private final static String ROUTING_KEY = "";
//
//
//    public static void main(String[] args) {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        try {
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            //create exchange
//            channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
//            //create queue
//            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//            //bind them : fanout routingKey is empty
//            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
//            System.out.println(EXCHANGE_NAME + QUEUE_NAME + "binding done");
//            //
//            System.out.println("Publishing Message");
//            String message = "Welcome to RabbitMQ!";
//            for (int i = 0; i < 5; i++) {
//                String msg ="Message" + i;
//                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null,msg.getBytes("UTF-8"));
//            }
//            System.out.println("Message Published");
//            channel.close();
//            connection.close();
//
//        } catch (Exception es) {
//            System.out.println(es);
//        }
//
//    }
//}
//Consumer
//public class Mydemo {
//    private final static String QUEUE_NAME = "QueueA";
//    private final static String ROUTING_KEY = "";
//
//
//    public static void main(String[] args) {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        try {
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            //create exchange
//            //create queue
//            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                String message = new String(delivery.getBody(), "UTF-8");
//                System.out.println(" [x] Received '" + message + "'");
//            };
//            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
//            });
//
//        } catch (Exception es) {
//            System.out.println(es);
//        }
//
//    }
//}

interface JSONMessage {
    String toJSON();
}

class Message implements JSONMessage {
    private int msgNo;
    private String from;
    private String to;
    private String header;
    private String content;

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

    private static Gson gson = new Gson();

    public String toJSON() {
        return gson.toJson(this);
    }

    public static Message fromJSON(String msg) {
        Gson gson = new Gson();
        return (Message) gson.fromJson(msg, Message.class);
    }

    public String toString() {
        return String.format("Message No: %d From: %s To: %s " +
                        "Header: %s Content: %s",
                msgNo, from, to, header, content);
    }

}

public class Mydemo {
    private final static String QUEUE_NAME = "QueueA";
    private final static String EXCHANGE_NAME = "aggreements";
    private final static String ROUTING_KEY = "";


    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //create exchange
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
            //create queue
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            //bind them : fanout routingKey is empty
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            System.out.println(EXCHANGE_NAME + QUEUE_NAME + "binding done");
            //
            Message msg = new Message();
            msg.setFrom("John");
            msg.setTo("Nicky");
            msg.setHeader("Hello World");
            msg.setContent("Hello World Again");

            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.toJSON().getBytes());

            System.out.println("Message Published");
            channel.close();
            connection.close();

        } catch (Exception es) {
            System.out.println(es);
        }

    }
}