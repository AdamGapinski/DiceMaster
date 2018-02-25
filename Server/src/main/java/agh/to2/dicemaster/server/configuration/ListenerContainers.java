package agh.to2.dicemaster.server.configuration;

import agh.to2.dicemaster.common.CommunicationConstants;
import agh.to2.dicemaster.server.listeners.RegisteredClientListener;
import agh.to2.dicemaster.server.listeners.RegistrationListener;
import agh.to2.dicemaster.server.listeners.UnregistrationListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerContainers {
    @Bean(name = "registrationListenerContainer")
    SimpleMessageListenerContainer registrationContainer(ConnectionFactory connectionFactory,
                                                         RegistrationListener listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(CommunicationConstants.REGISTRATION_QUEUE_NAME);
        container.setMessageListener(listener);
        container.setDefaultRequeueRejected(false);
        return container;
    }

    @Bean(name = "clientListenerContainer")
    SimpleMessageListenerContainer registeredClientContainer(ConnectionFactory connectionFactory,
                                                             RegisteredClientListener listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(listener);
        container.setDefaultRequeueRejected(false);
        return container;
    }

    @Bean(name = "unregistrationListenerContainer")
    SimpleMessageListenerContainer unregistrationContainer(ConnectionFactory connectionFactory,
                                                           UnregistrationListener listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(listener);
        container.setQueueNames(CommunicationConstants.DELETION_EVENTS_QUEUE_NAME);
        container.setDefaultRequeueRejected(false);
        return container;
    }
}
