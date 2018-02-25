package agh.to2.dicemaster.game.poker;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import agh.to2.dicemaster.game.model.Dice;


public class PokerScore {

    private static class Counter {
        Map<Dice.Value, Integer> counter = new HashMap<>();

        Counter(Dice[] dices) {
            Stream.of(Dice.Value.values())
                    .forEach(value -> counter.put(value, 0));
            Stream.of(dices)
                    .forEach(dice ->
                            counter.compute(dice.getValue(), (k, v) -> v + 1));
        }

        boolean hasFiveSame() {
            return counter.values().contains(5);
        }

        boolean hasFourSame() {
            return counter.values().contains(4);
        }

        boolean hasThreeAndTwoSame() {
            return counter.values().contains(3) && counter.values().contains(2);
        }

        boolean hasBigStraight() {
            return !counter.values().contains(3) && !counter.values().contains(2)
                    && counter.get(Dice.Value.ONE).equals(0);
        }

        boolean hasSmallStraight() {
            return !counter.values().contains(3) && !counter.values().contains(2)
                    && counter.get(Dice.Value.ONE).equals(1);
        }

        Optional<Integer> sumThreeOfKind() {
            if (counter.values().contains(3)) {
                for (Dice.Value key : counter.keySet()) {
                    if (counter.get(key).equals(3)) {
                        return Optional.of(3 * (key.ordinal() + 1));
                    }
                }
            }
            return Optional.empty();

        }

        Optional<Integer> sumTwoPairs() {
            long pairCount = counter.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() == 2)
                    .count();
            if (pairCount == 2) {
                int sum = counter.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() == 2)
                        .mapToInt(entry -> 2 * (entry.getKey().ordinal() + 1))
                        .sum();
                return Optional.of(sum);
            }
            return Optional.empty();
        }

        Optional<Integer> sumPair() {
            if (counter.values().contains(2)) {
                for (Dice.Value key : counter.keySet()) {
                    if (counter.get(key).equals(2)) {
                        return Optional.of(2 * (key.ordinal() + 1));
                    }
                }
            }
            return Optional.empty();
        }
    }

    private static int sumDices(Dice[] dices) {
        int sum = 0;
        for (Dice dice : dices) {
            sum += dice.getNumberOnDice();
        }
        return sum;
    }


    public static int getScore(Dice[] dices) {
        Counter counter = new Counter(dices);
        int dicesSum = sumDices(dices);

        if (counter.hasFiveSame()) return 500 + dicesSum;
        if (counter.hasFourSame()) return 300 + dicesSum;
        if (counter.hasThreeAndTwoSame()) return 200 + dicesSum;
        if (counter.hasBigStraight()) return 150;
        if (counter.hasSmallStraight()) return 130;
        if (counter.sumThreeOfKind().isPresent()) return counter.sumThreeOfKind().get() + 100;
        if (counter.sumTwoPairs().isPresent()) return counter.sumTwoPairs().get() + 50;
        if (counter.sumPair().isPresent()) return counter.sumPair().get() + 30;
        return dicesSum;

    }
}