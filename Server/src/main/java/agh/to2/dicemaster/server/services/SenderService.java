package agh.to2.dicemaster.server.services;

import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.DTO.RegistrationConfirmationDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SenderService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public SenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendGameState(GameDTO gameDTO, String queueName){
        send(gameDTO, queueName);
    }

    public void directSendGameState(GameDTO gameDTO, String queueName) {
        directSend(gameDTO, queueName);
    }

    public void sendGames(Collection<GameDTO> games, String queueName) {
        directSend(games, queueName);
    }

    public void sendRegistrationConfirmation(RegistrationConfirmationDTO registrationConfirmationDTO,
                                                      String queueName){
        directSend(registrationConfirmationDTO, queueName);
    }

    public void sendRequestErrorResponse(String message, String queueName) {
        directSend(message, queueName);
    }

    private void send(Object object, String queueName) {
        rabbitTemplate.convertAndSend("diceMasterExchange", queueName, object, this::setJsonContentType);
    }

    private void directSend(Object object, String routingKey) {
        rabbitTemplate.convertAndSend(routingKey, object, this::setJsonContentType);
    }

    private Message setJsonContentType(Message message) {
        message.getMessageProperties().setContentType("application/json");
        return message;
    }
}
