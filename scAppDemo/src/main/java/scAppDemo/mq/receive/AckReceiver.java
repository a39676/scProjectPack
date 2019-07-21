package scAppDemo.mq.receive;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import scAppDemo.config.constant.MQQueueConstant;

public class AckReceiver {

	public void consumer() throws Exception {
		boolean autoAck = false;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setVirtualHost("/");
		factory.setHost("${spring.rabbitmq.host}");
		factory.setPort(AMQP.PROTOCOL.PORT);
		factory.setUsername("${spring.rabbitmq.username}");
		factory.setPassword("${spring.rabbitmq.password}");

		Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();
		String EXCHANGE_NAME = "exchange.direct";
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		channel.queueBind(MQQueueConstant.helloAck, EXCHANGE_NAME, "key");

//        GetResponse response = channel.basicGet(QUEUE_NAME, false);
//        byte[] body = response.getBody();
//        System.out.println(new String(body).toString());

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, StandardCharsets.UTF_8);
				System.out.println(message);

				if (message.contains(":3")) {
					// requeue：重新入队列，false：直接丢弃，相当于告诉队列可以直接删除掉
					channel.basicReject(envelope.getDeliveryTag(), true);
				} else {
					channel.basicAck(envelope.getDeliveryTag(), true);
				}
			}
		};

		channel.basicConsume(MQQueueConstant.helloAck, autoAck, consumer);

	}
}
