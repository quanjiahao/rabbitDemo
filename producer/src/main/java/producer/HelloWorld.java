package producer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
* 发送消息*/
public class HelloWorld {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1 建立链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //2 设置参数
        connectionFactory.setHost("10.0.0.53"); // ip 默认值localhost
        connectionFactory.setPort(5672); // 默认就是5672
        connectionFactory.setVirtualHost("/test");  // 虚拟机
        connectionFactory.setUsername("jiahao"); //默认 guest
        connectionFactory.setPassword("123");  //默认guest
        //3 创建连接
        Connection connection = connectionFactory.newConnection();
        //4 创建channel
        Channel channel = connection.createChannel();
        //5 创建队列
        // 声明（创建）队列
        /**
         * 参数1：队列名称
         * 参数2：是否定义持久化队列
         * 参数3：是否独占本次连接
         * 参数4：是否在不使用的时候自动删除队列
         * 参数5：队列其它参数
         */
        //如果没有hello world队列，将创建队列
        channel.queueDeclare("hello_world",true,false,false,null);
        /**
         * 参数1：交换机名称，如果没有指定则使用默认Default Exchage
         * 参数2：路由key,简单模式可以传递队列名称
         * 参数3：消息其它属性
         * 参数4：消息内容
         */
        String body = "hello rabbit";
        //6 发送消息
        channel.basicPublish("","hello_world",null,body.getBytes());
        channel.close();
        connection.close();
    }
}
