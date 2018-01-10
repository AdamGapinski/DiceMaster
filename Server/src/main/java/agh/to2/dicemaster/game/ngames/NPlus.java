package agh.to2.dicemaster.game.ngames;

import agh.to2.dicemaster.common.api.MoveDTO;
import agh.to2.dicemaster.game.nmodel.Dice;
import agh.to2.dicemaster.game.nmodel.Player;

import java.util.List;
import java.util.Random;

public class NPlus implements Rules {
    public Integer aim;

    public NPlus() {

    }

    @Override
    public Integer getresult(Player player) {
        Integer result = 0;
        for (Dice d : player.getDices()) {
            //TODO
        //    result += d.getValue();
        }
        return result;
    }



    @Override
    public void initializeDices(Player player) {
        for (int i = 0; i < 5; i++) {
            player.setDice(i, Dice.randomDice());
        }
    }


    @Override
    public void drawDices(Player player, MoveDTO move) {
        for (int i = 0; i < 5; i++) {
           // if (move.getDicesToReRoll() player.setDice(i, Dice.randomDice());
        }
    }

    @Override
    public Integer getAim() {
        return aim;
    }

    @Override
    public void initializeRound(List<Player> players) {
        int[] dices = new int[5];
        Integer aim = 1;
        Random generator = new Random();
        for (int i = 0; i < 5; i++) {
            dices[i] = generator.nextInt(6) + 1;
            aim += dices[i];
        }
    }
}
