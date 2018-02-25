package agh.to2.dicemaster.game.model;


import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.game.poker.PokerScore;
import agh.to2.dicemaster.server.api.GameParticipant;
import agh.to2.dicemaster.server.api.PlayerEventHandler;

public class Player extends GameParticipant {
    private static final int NUMBER_OF_DICES = 5;
    private final GameParticipant gameParticipant;
    private final Dice[] dices = Dice.getDices(NUMBER_OF_DICES);
    private int gameScore = 0;

    public Player(GameParticipant gameParticipant) {
        super(gameParticipant.getId());
        this.gameParticipant = gameParticipant;
    }

    public int getValueOfDies() {
        return PokerScore.getScore(dices);
    }

    public void rollDices(Iterable<Integer> dicesToRoll) {
        for (Integer diceNumber : dicesToRoll) {
            dices[diceNumber].roll();
        }
    }

    public void rollDices(boolean[] dicesToRoll) {
        for (int i = 0; i < dicesToRoll.length; i++) {
            if(dicesToRoll[i]) {
                dices[i].roll();
            }
        }
    }

    public void rollAllDices() {
        for (Dice dice : dices) {
            dice.roll();
        }
    }

    public Dice[] getDices() {
        return dices.clone();
    }

    public Dice.Value[] getDicesValues() {
        Dice.Value[] values = new Dice.Value[dices.length];

        for (int i = 0; i < values.length; i++) {
            values[i] = dices[i].getValue();
        }

        return values;
    }


    @Override
    public void notifyGameStateChange(GameDTO gameDTO) {
        gameParticipant.notifyGameStateChange(gameDTO);
    }

    @Override
    public void registerPlayerEventHandler(PlayerEventHandler playerEventHandler) {
        gameParticipant.registerPlayerEventHandler(playerEventHandler);
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public void incereaseGameScore() {
        this.gameScore += 1;
    }

    public int getRoundScore() {
        return PokerScore.getScore(dices);
    }



}
