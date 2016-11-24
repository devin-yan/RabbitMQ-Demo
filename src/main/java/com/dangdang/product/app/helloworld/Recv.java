package com.dangdang.product.app.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.dangdang.product.app.Config.*;

/**
 * The simplest thing that does something
 */
public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            /**
             * Setting up is the same as the sender
             */
            ConnectionFactory factory = new ConnectionFactory();

            factory.setHost(HAPROXY_HOST);
            factory.setPort(HAPROXY_PORT);
            factory.setUsername(USER_NAME);
            factory.setPassword(PASSWORD);

            connection = factory.newConnection();
            channel = connection.createChannel();

            /**
             * we declare the queue here, as well. Because we might start the receiver before the sender, we want to make sure the queue exists before we try to consume messages from it.
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);

            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                /**
                 * Lastly, we close the channel and the connection
                 */
                channel.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
