package agh.to2.dicemaster.bot.model;


import agh.to2.dicemaster.bot.DiceInputDTO;
import agh.to2.dicemaster.bot.DiceOutputDTO;

import java.util.*;

public class NPlusBotDifficult extends Bot {

    @Override
    DiceOutputDTO getDicesToThrow(DiceInputDTO input) {

        List<Integer> allDices = input.getMyInput();
        result.setMyInput(allDices);
        HashMap<Integer, Integer> dicesToThrow = input.getDicesRerolled();

        Iterator it = dicesToThrow.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry) it.next();
            int numToTarget = result.getScoreLeft();
            int eyes = (Integer) pair.getValue();

            if ((dicesToThrow.size() - 1) * 6 >= numToTarget - eyes &&
                    (dicesToThrow.size() - 1) * 1 <=numToTarget - eyes) {   //*1 to show it clearly
                result.subtractNumToFinishGame(eyes);
                it.remove();
            }


        }

        result.setDicesToThrow(dicesToThrow);

        return result;
    }

}

