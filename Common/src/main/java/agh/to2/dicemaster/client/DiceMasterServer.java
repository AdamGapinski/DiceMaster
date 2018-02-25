package agh.to2.dicemaster.client;

import agh.to2.dicemaster.client.api.GameEventHandler;
import agh.to2.dicemaster.client.api.Server;
import agh.to2.dicemaster.client.api.ServerGame;
import agh.to2.dicemaster.client.services.QueueService;
import agh.to2.dicemaster.client.services.SenderService;
import agh.to2.dicemaster.common.DTO.RegistrationConfirmationDTO;
import agh.to2.dicemaster.common.api.GameConfigDTO;
import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DiceMasterServer implements Server {

    private boolean registered = false;
    private final QueueService queueService;
    private final SenderService senderService;

    public DiceMasterServer(String serverAddress, String serverUsername, String password) {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(serverAddress, 5672);
        connectionFactory.setUsername(serverUsername);
        connectionFactory.setPassword(password);
        senderService = new SenderService(connectionFactory, 5000);
        queueService = new QueueService(connectionFactory);

        queueService.configureClientQueue();
    }

    @Override
    public ServerGame createGame(GameConfigDTO gameConfigDTO, GameEventHandler gameEventHandler, UserType userType) {
        validateRegistered();

        Optional<Object> response = senderService.requestGameCreation(gameConfigDTO, userType);

        if (response.isPresent()) {
            Object responseObject = response.get();
            if (responseObject instanceof GameDTO) {
                return new DiceMasterServerGame((GameDTO) responseObject, senderService, queueService, gameEventHandler);
            }
        }

        return null;
    }

    @Override
    public ServerGame requestJoinGame(GameDTO gameDTO, GameEventHandler gameEventHandler, UserType userType) {
        validateRegistered();

        Optional<Object> response = senderService.requestGameJoin(gameDTO, userType);

        if (response.isPresent()) {
            Object responseObject = response.get();
            if (responseObject instanceof GameDTO) {
                return new DiceMasterServerGame((GameDTO) responseObject, senderService, queueService, gameEventHandler);
            }
        }

        return null;
    }

    @Override
    public List<GameDTO> getAvailableGames() {
        validateRegistered();

        return senderService.requestGames().map(this::convertToGameDTOList).orElse(new ArrayList<>());
    }

    private List<GameDTO> convertToGameDTOList(Object responseObject) {
        List<GameDTO> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (responseObject instanceof Collection) {
            Collection gameDTOS = (Collection) responseObject;

            for (Object o : gameDTOS) {
                o = objectMapper.convertValue(o, GameDTO.class);
                if (o != null) {
                    result.add((GameDTO) o);
                }
            }
        }

        return result;
    }

    @Override
    public boolean registerClient(String username) {
        String clientQueueName = queueService.configureClientQueue();

        Optional<Object> response = senderService.requestRegistration(username, clientQueueName);

        if (response.isPresent()) {
            Object responseObject = response.get();

            if (responseObject instanceof RegistrationConfirmationDTO) {
                RegistrationConfirmationDTO confirmationDTO = (RegistrationConfirmationDTO) responseObject;
                senderService.setServerQueueName(confirmationDTO.getServerQueueName());
                registered = true;
                return true;
            }
        }

        return false;
    }

    private void validateRegistered() {
        if (!registered) {
            throw new IllegalStateException("User is not registered");
        }

    }
}
