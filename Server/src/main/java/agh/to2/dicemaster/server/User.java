package agh.to2.dicemaster.server;

import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.server.api.GameParticipant;
import agh.to2.dicemaster.server.api.PlayerEventHandler;
import agh.to2.dicemaster.server.services.SenderService;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;

public class User extends GameParticipant {
    private String serverQueueName;
    private String clientQueueName;
    private PlayerEventHandler playerEventHandler;
    private SenderService senderService;
    private boolean userInGame = false;

    public User(String username, String clientQueueName, SenderService senderService) {
        this.clientQueueName = clientQueueName;
        this.setId(username);
        this.senderService = senderService;
        this.serverQueueName = generateQueueName();
    }

    private String generateQueueName() {
//        return UUID.randomUUID().toString()
        return "diceMaster" + DatatypeConverter.printHexBinary((new SecureRandom().generateSeed(32)));
    }

    @Override
    public void notifyGameStateChange(GameDTO gameDTO) {
        if(userInGame){
            this.senderService.sendGameState(gameDTO, this.clientQueueName);
        }
    }

    @Override
    public void registerPlayerEventHandler(PlayerEventHandler playerEventHandler) {
        this.userInGame = true;
        this.playerEventHandler = playerEventHandler;
    }

    public void leaveGame() {
        if(userInGame){
            this.userInGame = false;
            this.playerEventHandler.onPlayerLeft();
            this.playerEventHandler = null;
        }
    }

    public String getServerQueueName() {
        return serverQueueName;
    }

    public void setServerQueueName(String serverQueueName) {
        this.serverQueueName = serverQueueName;
    }

    public String getClientQueueName() {
        return clientQueueName;
    }

    public void setClientQueueName(String clientQueueName) {
        this.clientQueueName = clientQueueName;
    }

    public PlayerEventHandler getPlayerEventHandler() {
        return playerEventHandler;
    }

    public boolean isUserInGame() {
        return userInGame;
    }

    public void setUserInGame(boolean userInGame) {
        this.userInGame = userInGame;
    }
}
