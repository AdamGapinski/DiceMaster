package agh.to2.dicemaster.server.receivers;

import agh.to2.dicemaster.common.api.GameConfigDTO;
import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.MoveDTO;
import agh.to2.dicemaster.common.api.UserType;
import agh.to2.dicemaster.server.User;
import agh.to2.dicemaster.server.api.Game;
import agh.to2.dicemaster.server.managers.GamesManager;
import agh.to2.dicemaster.server.managers.UsersManager;
import agh.to2.dicemaster.server.services.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegisteredClientReceiver {

    private final UsersManager usersManager;
    private final GamesManager gamesManager;
    private final SenderService senderService;

    @Autowired
    public RegisteredClientReceiver(UsersManager usersManager, GamesManager gamesManager, SenderService senderService) {
        this.usersManager = usersManager;
        this.gamesManager = gamesManager;
        this.senderService = senderService;
    }

    public void onMoveRequest(MoveDTO move, String inQueueName) {
        Optional<User> userOptional = usersManager.getUserByQueueName(inQueueName);
        userOptional.ifPresent(user -> {
            if (user.isUserInGame()) {
                user.getPlayerEventHandler().onMoveRequest(move);
            }
        });
    }

    public void onCreateGameRequest(GameConfigDTO gameConfigDTO, UserType userType, String inQueueName, String replyToQueueName) {
        Optional<User> userOptional = usersManager.getUserByQueueName(inQueueName);
        if(userOptional.isPresent() && !userOptional.get().isUserInGame()){
            Game game = gamesManager.createGame(gameConfigDTO, userType, userOptional.get());
            senderService.directSendGameState(game.getGameDTO(), replyToQueueName);
        }
    }

    public void onJoinGameRequest(GameDTO gameDTO, UserType userType, String inQueueName, String replyToQueueName) {
        Optional<User> userOptional = usersManager.getUserByQueueName(inQueueName);
        if(userOptional.isPresent() && !userOptional.get().isUserInGame()){
            gamesManager.addUserToGame(userType, userOptional.get(), gameDTO.getId());
            Optional<Game> game = gamesManager.getGameById(gameDTO.getId());
            if(game.isPresent()){
                senderService.directSendGameState(game.get().getGameDTO(), replyToQueueName);
            } else {
                senderService.sendRequestErrorResponse("No such game", replyToQueueName);
            }
        }
    }

    public void onUserLeftGameRequest(String inQueueName) {
        Optional<User> userOptional = usersManager.getUserByQueueName(inQueueName);
        userOptional.ifPresent(User::leaveGame);
    }

    public void onGetAvailableGamesRequest(String replyToQueueName) {
        senderService.sendGames(gamesManager.getAllAsGameDTO(), replyToQueueName);
    }
}
