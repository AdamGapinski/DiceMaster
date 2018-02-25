package agh.to2.dicemaster.bot.model;

import agh.to2.dicemaster.bot.BotType;
import agh.to2.dicemaster.bot.DiceInputDTO;
import agh.to2.dicemaster.bot.DiceOutputDTO;
import agh.to2.dicemaster.bot.factory.NMultiplyBotFactory;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by kamkalet on 18.01.2018.
 */

class NMultiplyBotDifficultTest {

    private NMultiplyBotFactory factory = new NMultiplyBotFactory();

    @Test
    void getDicesToThrowTest() throws Exception {

        NMultiplyBotDifficult bot = (NMultiplyBotDifficult)
                factory.createBot(BotType.DIFFICULT);

//        bot.


        DiceOutputDTO output = new DiceOutputDTO(30);
        HashMap<Integer, Integer> dicesReRolled = new HashMap<>();

        //second values are not that essential
        dicesReRolled.put(1,6);
        output.setMyInput(Arrays.asList(6,4,4,4,4));
        output.setDicesToThrow(dicesReRolled);
        output.setScoreLeft(5);

        DiceInputDTO input = new DiceInputDTO(30);
        input.setMyInput(Arrays.asList(6,5,1,1,1));
        input.setDicesRerolled(dicesReRolled);






    }
}