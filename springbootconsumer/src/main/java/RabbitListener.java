import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Component
public class RabbitListener {
    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "bootqueue")
    public void ListenerQueue(Message message){
        System.out.println(message);
    }
}
