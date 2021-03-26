package producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Topics {
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
            //5 创建交换机
            /**
             * 声明交换机
             * 参数1：交换机名称
             * 参数2：交换机类型，fanout、topic、direct、headers
             */
            String exchangeName = "test_topic";
            channel.exchangeDeclare( exchangeName, BuiltinExchangeType.TOPIC,true,false,false,null);
             // 6 创建队列
            String q1Name = "topic_q1";
            String q2Name = "topic_q2";
            channel.queueDeclare(q1Name,true,false,false,null);
            channel.queueDeclare(q2Name,true,false,false,null);
            /**
             *
             * 1. queue:队列名称
             * 2.exchange： 交换机名称
             * 3.routingkey:路由键 null if fanout
             */
            //routing key
            // error日志存入数据库，order存入数据库
            // 7.绑定队列和交换机
            channel.queueBind(q1Name,exchangeName,"#.error");
            channel.queueBind(q1Name,exchangeName,"order.*");
            channel.queueBind(q2Name,exchangeName,"*.*");
            // 8.发送消息
            String body ="you subscribed";
            channel.basicPublish(exchangeName,"",null,body.getBytes());

            channel.close();
            connection.close();

    }
}

