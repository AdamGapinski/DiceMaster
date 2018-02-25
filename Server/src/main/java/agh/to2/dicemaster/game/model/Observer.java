package agh.to2.dicemaster.game.model;


import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.server.api.GameParticipant;
import agh.to2.dicemaster.server.api.PlayerEventHandler;


public class Observer extends GameParticipant {

    private final GameParticipant gameParticipant;

    public GameParticipant getGameParticipant() {
        return gameParticipant;
    }

    public Observer(GameParticipant gameParticipant){
        this.gameParticipant = gameParticipant;
    }

    @Override
    public void notifyGameStateChange(GameDTO gameDTO) {
        this.gameParticipant.notifyGameStateChange(gameDTO);
    }

    @Override
    public void registerPlayerEventHandler(PlayerEventHandler playerEventHandler) {
        this.gameParticipant.registerPlayerEventHandler(playerEventHandler);
    }

}

