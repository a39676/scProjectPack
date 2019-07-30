package gateway.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import scAppCommon.constant.MQQueueConstant;

@Configuration
public class RabbitConfig {
	
	@Bean
    public Queue helloQueue() {
        return new Queue(MQQueueConstant.hello);
    }

}
