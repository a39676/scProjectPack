package scAppDemo.mq.receive;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import scAppCommon.constant.MQQueueConstant;

@Component
@RabbitListener(queues = MQQueueConstant.hello)
public class SimpleReceiver {

	@RabbitHandler
	public void process(String mq) {
		System.out.println("receiver: " + mq);
	}

}
