package scAppDemo.mq.receive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import scAppDemo.config.constant.MQQueueConstant;

@Component
@RabbitListener(queues = MQQueueConstant.hello)
public class Receiver {

	@RabbitHandler
	public void process(String hello) {
		System.out.println("receiver:" + hello);
	}

}
