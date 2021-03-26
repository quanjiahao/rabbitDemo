package consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_WorkQueue2 {
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
        channel.queueDeclare("work_queue",true,false,false,null);
        //监听消息

        Consumer consumer =new DefaultConsumer(channel){
            // 收到消息后，自动执行该方法

            @Override
            /**
             * consumerTag 消息者标签，在channel.basicConsume时候可以指定
             * envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志(收到消息失败后是否需要重新发送)
             * properties 属性信息
             * body 消息
             */
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                System.out.println(consumerTag);
//                //路由key
//                System.out.println("路由key为：" + envelope.getRoutingKey());
//                //交换机
//                System.out.println("交换机为：" + envelope.getExchange());
//                //消息id
//                System.out.println("消息id为：" + envelope.getDeliveryTag());
//                //收到的消息
                System.out.println("接收到的消息为：" + new String(body));
            }
        };
        /**
         * 参数1：队列名称
         * 参数2：是否自动确认，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置为false则需要手动确认
         * 参数3：消息接收到后回调
         */
        //6 接受
        channel.basicConsume("work_queue",true,consumer);
        //不要关闭资源

    }
}
