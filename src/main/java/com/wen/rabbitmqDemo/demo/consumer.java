package com.wen.rabbitmqDemo.demo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.*;

public class consumer {
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory =  new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setUsername("wen");
        connectionFactory.setPassword("123456");
//        connectionFactory.setPort(15672);
        connectionFactory.setVirtualHost("wen");
        Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);//告诉MQ SERVER如果未发送确认后，不要继续发送消息给我
        channel.queueDeclare(publisher.persistent_queue,true,false,false,null);
        boolean autoAck = false;//关闭确认消息，由我们自己来控制
        final Consumer consumer =  new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String messsage = new String(body,"utf-8");
                System.out.println("收到:"+messsage);
                try{
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(publisher.persistent_queue,autoAck,consumer);
	}
}
