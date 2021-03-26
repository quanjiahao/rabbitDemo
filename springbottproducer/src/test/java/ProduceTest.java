import config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProduceTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public  void testSend(){
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_NAME,"boot.hehe","boot my hello");
    }
}
