package agh.to2.dicemaster.game.ngames;

import agh.to2.dicemaster.common.api.MoveDTO;
import agh.to2.dicemaster.game.nmodel.Dice;
import agh.to2.dicemaster.game.nmodel.Player;
import agh.to2.dicemaster.server.api.GameParticipant;

import java.util.List;
import java.util.Random;

public class NTimes implements Rules {
    private Integer aim;

    public NTimes() {
    }

    @Override
    public  boolean countPoints(Player player) {
        Integer result = 0;
        for (Dice d : player.getDices()) {
            result *= d.getValue();
        }
        if(result.equals(aim)){
            return true;
        }
        else return false;
    }

    @Override
    public void initializeDices(Player player) {
        for (int i = 0; i < 5; i++) {
            player.setDice(i, Dice.randomDice());
        }
    }


    @Override
    public void drawDices(Player player, MoveDTO move){
        boolean [] dicesToReroll = move.getDicesToReRoll();
        for(int i = 0; i < 5; i++){
            if(dicesToReroll[i]) player.setDice(i, Dice.randomDice());
        }
    }

    @Override
    public Integer getAim() {
        return aim;
    }

    @Override
    public int initializeRound(List<GameParticipant> players) {
        int[] dices = new int[5];
        this.aim=1;
        Random generator = new Random();
        for (int i = 0; i < 5; i++) {
            dices[i] = generator.nextInt(6) + 1;
            aim *= dices[i];
        }
        return generator.nextInt(players.size());
    }

}