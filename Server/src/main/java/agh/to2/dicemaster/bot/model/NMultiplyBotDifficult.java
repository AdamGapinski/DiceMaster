package agh.to2.dicemaster.bot.model;


import agh.to2.dicemaster.bot.DiceInputDTO;
import agh.to2.dicemaster.bot.DiceOutputDTO;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class NMultiplyBotDifficult extends Bot {

    private boolean isPrime(int number) {
        return number > 2
                && IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(n -> (number % n == 0));
    }


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
            if (numToTarget % eyes == 0 &&

                    ((!isPrime(numToTarget / eyes) && numToTarget / eyes > 7)
                            || numToTarget / eyes < 7) &&

                    (Math.pow(6, dicesToThrow.size() - 1) >= numToTarget / eyes)) {

                result.divideNumToFinishGame(eyes);
                it.remove();

            }
            it.remove();
        }

        result.setDicesToThrow(dicesToThrow);

        return result;

    }
}
