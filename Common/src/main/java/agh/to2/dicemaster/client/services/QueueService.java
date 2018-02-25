package agh.to2.dicemaster.client.services;

import agh.to2.dicemaster.client.api.GameEventHandler;
import agh.to2.dicemaster.common.api.GameDTO;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

import java.util.UUID;

public class QueueService {

    private final ConnectionFactory connectionFactory;
    private SimpleMessageListenerContainer container;
    private MessageConverter messageConverter;

    public QueueService(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.messageConverter = new Jackson2JsonMessageConverter();
    }

    public String configureClientQueue() {
        container = new SimpleMessageListenerContainer();
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        TopicExchange exchange = new TopicExchange("diceMasterExchange");
        Queue queue = new Queue("diceMaster" + UUID.randomUUID().toString(),
                false, true, false);

        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(exchange);

        container.setQueueNames(queue.getName());
        container.setRabbitAdmin(rabbitAdmin);
        container.setConnectionFactory(connectionFactory);
        container.setMessageConverter(messageConverter);
        container.setDefaultRequeueRejected(false);

        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(queue.getName()));

        return queue.getName();
    }

    public void startListeningToGameStateChange(GameEventHandler gameEventHandler) {
        container.setupMessageListener((MessageListener) message -> {
            Object body = messageConverter.fromMessage(message);
            if (body instanceof GameDTO) {
                gameEventHandler.onGameChange((GameDTO) body);
            }
        });
        container.start();
    }

    public void stopListeningToGameStateChange() {
        container.stop();
    }
}
