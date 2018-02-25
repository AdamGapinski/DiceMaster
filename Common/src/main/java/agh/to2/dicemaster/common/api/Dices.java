package agh.to2.dicemaster.common.api;

public class Dices {
    private DiceNumbers dicesScore[];

    public Dices() {
        dicesScore = new DiceNumbers[5];
        dicesScore[0] = DiceNumbers.UNKNOWN;
        dicesScore[1] = DiceNumbers.UNKNOWN;
        dicesScore[2] = DiceNumbers.UNKNOWN;
        dicesScore[3] = DiceNumbers.UNKNOWN;
        dicesScore[4] = DiceNumbers.UNKNOWN;

    }

    public Dices(DiceNumbers[] dicesNumbers) {
        this.dicesScore = dicesNumbers;
    }

    public DiceNumbers[] getDicesScore() {
        return dicesScore;
    }
}
