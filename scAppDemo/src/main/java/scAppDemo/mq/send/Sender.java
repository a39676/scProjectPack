package scAppDemo.mq.send;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import scAppDemo.config.constant.MQQueueConstant;

@Component
public class Sender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send(String message) {
		String context = "hello: " + message + new Date();
		System.out.println("sender:" + context);
		this.rabbitTemplate.convertAndSend(MQQueueConstant.hello, context);
	}
	
}
