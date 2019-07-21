package scAppDemo.mq.send;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import scAppDemo.config.constant.MQQueueConstant;

public class AckProducer {

	public void testBasicPublish() throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setVirtualHost("/");
		factory.setHost("${spring.rabbitmq.host}");
		factory.setPort(AMQP.PROTOCOL.PORT);
		factory.setUsername("${spring.rabbitmq.username}");
		factory.setPassword("${spring.rabbitmq.password}");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		String EXCHANGE_NAME = "exchange.direct";
		String ROUTING_KEY = "";
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		channel.queueDeclare(MQQueueConstant.helloAck, true, false, false, null);
		channel.queueBind(MQQueueConstant.helloAck, EXCHANGE_NAME, ROUTING_KEY);

		String message = "Hello Ack:";
		for (int i = 0; i < 5; i++) {
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, (message + i).getBytes(StandardCharsets.UTF_8));
		}

		channel.close();
		connection.close();
	}

}
