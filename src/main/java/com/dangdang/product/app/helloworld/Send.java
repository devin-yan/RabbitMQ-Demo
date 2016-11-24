package com.dangdang.product.app.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.dangdang.product.app.Config.*;

public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            /**
             *  then we can create a connection to the server:
             */
            ConnectionFactory factory = new ConnectionFactory();

            factory.setHost(HAPROXY_HOST);
            factory.setPort(HAPROXY_PORT);
            factory.setUsername(USER_NAME);
            factory.setPassword(PASSWORD);

            connection = factory.newConnection();
            channel = connection.createChannel();

            /**
             * To send, we must declare a queue for us to send to; then we can publish a message to the queue:
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            /**
             * Declaring a queue is idempotent - it will only be created if it doesn't exist already. The message content is a byte array, so you can encode whatever you like there.
             */
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

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
