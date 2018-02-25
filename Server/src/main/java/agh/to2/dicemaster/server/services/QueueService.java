package agh.to2.dicemaster.server.services;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    private final SimpleMessageListenerContainer clientListenerContainer;
    private final RabbitAdmin rabbitAdmin;

    @Autowired
    public QueueService(@Qualifier("clientListenerContainer") SimpleMessageListenerContainer clientListenerContainer,
                        ConnectionFactory connectionFactory) {
        this.clientListenerContainer = clientListenerContainer;
        this.rabbitAdmin = new RabbitAdmin(connectionFactory);
    }

    public void addRegisteredClientQueue(String queueName) {
        Queue queue = new Queue(queueName, false, true, false);
        rabbitAdmin.declareQueue(queue);

        clientListenerContainer.addQueueNames(queueName);

        TopicExchange exchange = new TopicExchange("diceMasterExchange");
        rabbitAdmin.declareExchange(exchange);

        rabbitAdmin.declareBinding(
                BindingBuilder.bind(queue).to(exchange).with(queueName));
    }

    public void removeRegisteredClientQueue(String queueName) {
        clientListenerContainer.removeQueueNames(queueName);
        rabbitAdmin.deleteQueue(queueName);
    }
}
