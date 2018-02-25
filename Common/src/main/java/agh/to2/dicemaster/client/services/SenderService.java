package agh.to2.dicemaster.client.services;

import agh.to2.dicemaster.common.CommunicationConstants;
import agh.to2.dicemaster.common.DTO.CreateGameRequestDTO;
import agh.to2.dicemaster.common.DTO.JoinGameRequestDTO;
import agh.to2.dicemaster.common.DTO.RegistrationRequestDTO;
import agh.to2.dicemaster.common.RequestType;
import agh.to2.dicemaster.common.api.GameConfigDTO;
import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.MoveDTO;
import agh.to2.dicemaster.common.api.UserType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.util.Optional;

public class SenderService {

    private final RabbitTemplate rabbitTemplate;
    private String serverQueueName;

    public SenderService(ConnectionFactory connectionFactory, int timeout) {
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange("diceMasterExchange");
        rabbitTemplate.setReplyTimeout(timeout);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    public Optional<Object> requestRegistration(String username, String clientQueueName) {
        return Optional.ofNullable(rabbitTemplate.convertSendAndReceive(CommunicationConstants.REGISTRATION_QUEUE_NAME,
                new RegistrationRequestDTO(username, clientQueueName), this::setJsonContentType));
    }

    public Optional<Object> requestGames() {
        return Optional.ofNullable(rabbitTemplate.convertSendAndReceive(serverQueueName, "no-content",
                message -> {
                    setRequestType(message, RequestType.GET_AVAILABLE_GAMES);
                    return message;
                }));
    }

    public Optional<Object> requestGameCreation(GameConfigDTO gameConfigDTO, UserType userType) {
        return Optional.ofNullable(rabbitTemplate.convertSendAndReceive(serverQueueName,
                new CreateGameRequestDTO(gameConfigDTO, userType),
                message -> {
                    setJsonContentType(message);
                    setRequestType(message, RequestType.CREATE_GAME);
                    return message;
                }));
    }

    public Optional<Object> requestGameJoin(GameDTO gameDTO, UserType userType) {
        return Optional.ofNullable(rabbitTemplate.convertSendAndReceive(serverQueueName,
                new JoinGameRequestDTO(gameDTO, userType),
                message -> {
                    setJsonContentType(message);
                    setRequestType(message, RequestType.JOIN_GAME);
                    return message;
                }));
    }

    public void sendMove(MoveDTO moveDTO) {
        rabbitTemplate.convertAndSend(serverQueueName, moveDTO, message -> {
            setJsonContentType(message);
            setRequestType(message, RequestType.MAKE_MOVE);
            return message;
        });
    }

    public void sendLeaveGameRequest() {
        rabbitTemplate.convertAndSend(serverQueueName, "no-content", message -> {
            setRequestType(message, RequestType.LEAVE_GAME);
            return message;
        });
    }

    private Message setJsonContentType(Message message) {
        message.getMessageProperties().setContentType("application/json");
        return message;
    }

    private void setRequestType(Message message, RequestType requestType) {
        message.getMessageProperties().setHeader(CommunicationConstants.HEADER_REQUEST_TYPE, requestType);
    }

    public void setServerQueueName(String serverQueueName) {
        this.serverQueueName = serverQueueName;
    }
}
