package agh.to2.dicemaster.bot.model;

import agh.to2.dicemaster.bot.DiceInputDTO;
import agh.to2.dicemaster.bot.DiceOutputDTO;
import agh.to2.dicemaster.bot.IllegalHandException;
import agh.to2.dicemaster.bot.helpers.Hand;
import agh.to2.dicemaster.bot.helpers.HandUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kamkalet on 04.01.2018.
 */
public class PokerBotEasy extends Bot {
    @Override
    DiceOutputDTO getDicesToThrow(DiceInputDTO input) {
        // TODO implement this method

        List<Integer> dicesToThrow = new ArrayList<>();

        List<Integer> myInput = input.getMyInput();
        if (!myHandIsTheHighestOnTable(input) && myHandUsesAllDices(input.getMyInput())) {
            // throw all dices
            dicesToThrow.addAll(input.getMyInput());
        } else {
            try {
                dicesToThrow.addAll(HandUtils.getDicesNotFromHand(input.getMyInput()));
            } catch (IllegalHandException e) {
                e.printStackTrace();
            }
        }


        HashMap <Integer, Integer> mapResult = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            if (dicesToThrow.contains(myInput.get(i))) {
                mapResult.put(i, myInput.get(i));
            }
        }

        result.setDicesToThrow(mapResult);


        return result;
    }

    private boolean myHandIsTheHighestOnTable(DiceInputDTO input) {
        for (List<Integer> someonesInput :
                input.getOtherPlayersInputs()) {
            // if someone's hand is better or equal than mine return false
            if (HandUtils.whatTheHandIsThis(someonesInput).compareTo(
                    HandUtils.whatTheHandIsThis(input.getMyInput())) >= 0) {
                return false;
            }
        }
        return true;
    }

    private boolean myHandUsesAllDices(List<Integer> inputHand) {
        Hand hand = HandUtils.whatTheHandIsThis(inputHand);
        if (hand.equals(Hand.Bust) || hand.equals(Hand.Five) ||
                hand.equals(Hand.Full) || hand.equals(Hand.Straight)) {
            return true;
        }
        return false;
    }


}
