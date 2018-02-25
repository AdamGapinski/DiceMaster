package agh.to2.dicemaster.bot.helpers;

import agh.to2.dicemaster.bot.IllegalHandException;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class HandUtils {

    public static List<Integer> getDicesNotFromHand(List<Integer> inputHand) throws IllegalHandException {
        Collections.sort(inputHand);

        if (sortedContainsFour(inputHand)) {
            if (inputHand.get(0).equals(inputHand.get(3))) {
                return inputHand.subList(4, 5);
            } else {
                return inputHand.subList(0, 1);
            }
        }

        if (sortedContainsThree(inputHand)) {
            if (inputHand.get(0).equals(inputHand.get(2)))
                return inputHand.subList(3, 5);
            if (inputHand.get(1).equals(inputHand.get(3))){
                List<Integer> result = new ArrayList<>();
                result.add(inputHand.get(0));
                result.add(inputHand.get(4));
                return result;
            }

            if (inputHand.get(2).equals(inputHand.get(4)))
                return inputHand.subList(0, 2);
        }

        if (sortedContainsTwoPairs(inputHand)) {
            if (inputHand.get(0).equals(inputHand.get(1))
                    && inputHand.get(2).equals(inputHand.get(3))) {
                return inputHand.subList(4, 5);
            }
            if (inputHand.get(0).equals(inputHand.get(1))
                    && inputHand.get(3).equals(inputHand.get(4))) {
                return inputHand.subList(2, 3);
            }
            if (inputHand.get(1).equals(inputHand.get(2))
                    && inputHand.get(3).equals(inputHand.get(4))) {
                return inputHand.subList(0,1);
            }
        }

        if (sortedContainsOnePair(inputHand)) {
            List<Integer> result = new ArrayList<>();
            if (inputHand.get(0).equals(inputHand.get(1))) {
                return inputHand.subList(2, 5);
            }
            if (inputHand.get(1).equals(inputHand.get(2))) {
                result.add(inputHand.get(0));
                result.addAll(inputHand.subList(3, 5));
                return result;
            }
            if (inputHand.get(2).equals(inputHand.get(3))) {
                result.addAll(inputHand.subList(0, 2));
                result.add(inputHand.get(4));
                return result;
            }
            if (inputHand.get(3).equals(inputHand.get(4))) {
                return inputHand.subList(0, 3);
            }
        }
        throw new IllegalHandException();
    }


    public static Hand whatTheHandIsThis(List<Integer> inputHand) {
        Collections.sort(inputHand);

        if (sortedContainsFive(inputHand)) {
            return Hand.Five;
        }
        if (sortedContainsFour(inputHand)) {
            return Hand.Four;
        }
        if (sortedContainsFull(inputHand)) {
            return Hand.Full;
        }
        if (sortedContainsStraight(inputHand)) {
            return Hand.Straight;
        }
        if (sortedContainsThree(inputHand)) {
            return Hand.Three;
        }
        if (sortedContainsTwoPairs(inputHand)) {
            return Hand.TwoPairs;
        }
        if (sortedContainsOnePair(inputHand)) {
            return Hand.OnePair;
        }
        return Hand.Bust;

    }

    public static String getFileName(List<Integer> inputHand) {
        StringBuilder result = new StringBuilder("agh/to2/dicemaster/bot/helpers/data");

        for (Integer i :
                inputHand) {
            result.append(i);
        }

        result.append(".json");

        return result.toString();
    }

    public static Double getHandValue(Long hand) {
        switch (hand.intValue()) {
            case 1:
                return 0.0;
            case 2:
                return 30.0;
            case 3:
                return 50.0;
            case 4:
                return 100.0;
            case 5:
                return 140.0;
            case 6:
                return 200.0;
            case 7:
                return 300.0;
            case 8:
                return 500.0;
        }
        return 0.0;
    }

    public static Integer getHandNumber(Hand hand){
        switch (hand) {
            case Bust:
                return 1;
            case OnePair:
                return 2;
            case TwoPairs:
                return 3;
            case Three:
                return 4;
            case Straight:
                return 5;
            case Full:
                return 6;
            case Four:
                return 7;
            case Five:
                return 8;
        }
        return 0;
    }

    public static List<Integer> integerListFromLongList(JSONArray mask) {
        List<Long> longs = new LinkedList<>();
        List<Integer> results = new LinkedList<>();

        for (Object val :
                mask) {
            longs.add((Long) val);
        }

        for (Long l :
                longs) {
            results.add(l.intValue());
        }
        return results;
    }

    public static Integer getMaxIndex(ArrayList<Double> input) {
        Integer result = 0;
        Double tmp = input.get(0);
        int len = input.size();
        for (int i = 1; i < len; i++) {
            if (input.get(i) >= tmp) {
                tmp = input.get(i);
                result = i;
            }
        }
        return result;
    }

    private static boolean sortedContainsFive(List<Integer> inputHand){
        return inputHand.get(0).equals(inputHand.get(4));
    }
    private static boolean sortedContainsFour(List<Integer> inputHand){
        return (inputHand.get(0).equals(inputHand.get(3))) &&
                inputHand.get(1).equals(inputHand.get(4));
    }
    private static boolean sortedContainsFull(List<Integer> inputHand){
        return ((inputHand.get(0).equals(inputHand.get(2))) &&
                (inputHand.get(3).equals(inputHand.get(4)))) ||
                ((inputHand.get(0).equals(inputHand.get(1))) &&
                        (inputHand.get(2).equals(inputHand.get(4))));
    }
    private static boolean sortedContainsStraight(List<Integer> inputHand){
        return inputHand.get(0).equals(inputHand.get(1) - 1) &&
                inputHand.get(0).equals(inputHand.get(2) - 2) &&
                inputHand.get(0).equals(inputHand.get(3) - 3) &&
                inputHand.get(0).equals(inputHand.get(4) - 4);
    }
    private static boolean sortedContainsThree(List<Integer> inputHand){
        return inputHand.get(0).equals(inputHand.get(2)) ||
                inputHand.get(1).equals(inputHand.get(3)) ||
                inputHand.get(2).equals(inputHand.get(4));
    }
    private static boolean sortedContainsTwoPairs(List<Integer> inputHand){
        return ((inputHand.get(0).equals(inputHand.get(1)))
                && (inputHand.get(2).equals(inputHand.get(3)))) ||
                ((inputHand.get(1).equals(inputHand.get(2))) &&
                        (inputHand.get(3).equals(inputHand.get(4)))) ||
                ((inputHand.get(0).equals(inputHand.get(1))) &&
                        (inputHand.get(3).equals(inputHand.get(4))));
    }
    private static boolean sortedContainsOnePair(List<Integer> inputHand){
        for (int i = 0; i < 4; i++) {
            if (inputHand.get(i).equals(inputHand.get(i+1)))
                return true;
        }
        return false;
    }
    private static boolean sortedContainsBust(List<Integer> inputHand){
        for (int i = 0; i < 4; i++) {
            if (inputHand.get(i).equals(inputHand.get(i+1)))
                return false;
        }
        return inputHand.get(0).equals(inputHand.get(4) - 5);
    }
}
