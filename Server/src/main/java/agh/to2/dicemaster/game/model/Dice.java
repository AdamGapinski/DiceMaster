package agh.to2.dicemaster.game.model;


import java.util.Random;

public class Dice {
    private Value value = Value.randomValue();

    public enum Value {

        ONE, TWO, THREE, FOUR, FIVE, SIX;

        private static final Random RANDOM = new Random();

        private static Value randomValue() {
            return values()[RANDOM.nextInt(values().length)];
        }
    }

    public void roll() {
        this.value = Value.randomValue();
    }

    public int getNumberOnDice() {
        return value.ordinal() + 1;
    }

    public Value getValue(){return this.value;}

    public void setValue(Value value){
        this.value = value;
    }

    public static Dice[] getDices(int numberOfDices) {
        Dice[] dices = new Dice[numberOfDices];

        for (int i = 0; i < dices.length; i++) {
            dices[i] = new Dice();
        }
        return dices;
    }
}