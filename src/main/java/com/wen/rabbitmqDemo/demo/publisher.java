package com.wen.rabbitmqDemo.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class publisher {
	public static String persistent_queue = "persistent_queue";
	 
    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory =  new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("wen");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("wen");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        boolean isPersist = true;
        channel.queueDeclare(persistent_queue,isPersist,false,false,null);
        int index = 1;
        while(index++<1000){
            String message = "这是第 "+index+" 条信息";
            channel.basicPublish("",persistent_queue, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes("utf-8"));
            System.out.println(message);
        }
        channel.close();
        connection.close();
    }
}
