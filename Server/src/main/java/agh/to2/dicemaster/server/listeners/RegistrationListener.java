package agh.to2.dicemaster.server.listeners;

import agh.to2.dicemaster.common.DTO.RegistrationRequestDTO;
import agh.to2.dicemaster.server.receivers.RegistrationReceiver;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements MessageListener {

    private final RegistrationReceiver registrationReceiver;
    private final MessageConverter messageConverter;

    @Autowired
    public RegistrationListener(RegistrationReceiver registrationReceiver, MessageConverter messageConverter) {
        this.registrationReceiver = registrationReceiver;
        this.messageConverter = messageConverter;
    }

    @Override
    public void onMessage(Message message) {
        Object body = messageConverter.fromMessage(message);
        if (body instanceof RegistrationRequestDTO) {
            registrationReceiver.onRegistrationRequest((RegistrationRequestDTO) body, message.getMessageProperties().getReplyToAddress().getRoutingKey());
        }
    }
}
