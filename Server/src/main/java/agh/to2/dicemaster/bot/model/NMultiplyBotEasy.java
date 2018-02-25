package agh.to2.dicemaster.bot.model;

import agh.to2.dicemaster.bot.DiceInputDTO;
import agh.to2.dicemaster.bot.DiceOutputDTO;

import java.util.*;
import java.util.stream.IntStream;

public class NMultiplyBotEasy extends Bot{


    private boolean isPrime(int number) {
        return number > 2
                && IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(n -> (number % n == 0));
    }

    @Override
    DiceOutputDTO getDicesToThrow(DiceInputDTO input) {

        int dicesAvailabletoThrow = input.getMyInput().size();

        Random r = new Random();
        int dicesToPutAside = r.nextInt(dicesAvailabletoThrow);

        HashMap<Integer, Integer> dicesToThrow = input.getDicesRerolled();

        int to_Remove;
        for (int i = 0; i < dicesToPutAside; i++) {
            to_Remove = r.nextInt(dicesToThrow.size());
            Iterator it = dicesToThrow.entrySet().iterator();
            Map.Entry pair=(Map.Entry) it.next();
            for (int j = 0; j <  to_Remove; j++) {
                pair = (Map.Entry) it.next();
            }
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

//        last version
//        result.setDicesToThrow(dicesToThrow);
//
//
//        int dicesAvailabletoThrow = input.getMyInput().size();
//
//        Random r = new Random();
//        int dicesToPutAside = r.nextInt(dicesAvailabletoThrow);
//
//        List<Integer> dicesToThrow = new ArrayList<>(input.getMyInput());
//
//        int to_Remove;
//        for (int i = 0; i < dicesToPutAside; i++) {
//        to_Remove = r.nextInt(dicesToThrow.size());
//        if (result.getScoreLeft()%dicesToThrow.get(to_Remove)==0 &&
//
//        ((!isPrime(result.getScoreLeft()/dicesToThrow.get(to_Remove)) && result.getScoreLeft()/dicesToThrow.get(to_Remove)>7)
//        || result.getScoreLeft()/dicesToThrow.get(to_Remove)<7) &&
//
//        (Math.pow(6,dicesToThrow.size() - 1) >= result.getScoreLeft() / dicesToThrow.get(to_Remove))){
//        result.divideNumToFinishGame(dicesToThrow.get(to_Remove));
//        dicesToThrow.remove(to_Remove);
//        }
//        }
//
//        result.setDicesToThrow(dicesToThrow);
//
//
//        return result;