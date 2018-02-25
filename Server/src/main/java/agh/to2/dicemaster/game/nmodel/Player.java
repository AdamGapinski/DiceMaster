package agh.to2.dicemaster.game.nmodel;

import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.server.api.GameParticipant;
import agh.to2.dicemaster.server.api.PlayerEventHandler;

/**
 * Created by werka on 10.01.18.
 */
public class Player extends GameParticipant{

    public Dice[] dices = new Dice[5];
    private Integer score;
    private GameParticipant gameParticipant;
    public Player(GameParticipant gameParticipant) {
        this.gameParticipant = gameParticipant;
        this.score=0;
    }

    public Dice[] getDices() {
        return dices;
    }

    public void setDice(int  i, Dice dice){
        dices[i] = dice;
    }

    public Integer getScore() {
        return score;
    }

    public void addRoundWon() {
        this.score = score+1;
    }

    @Override
    public void notifyGameStateChange(GameDTO gameDTO) {
        this.gameParticipant.notifyGameStateChange(gameDTO);
    }

    @Override
    public void registerPlayerEventHandler(PlayerEventHandler playerEventHandler) {
        gameParticipant.registerPlayerEventHandler(playerEventHandler);

    }
}