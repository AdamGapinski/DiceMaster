package agh.to2.dicemaster.game.poker;

import agh.to2.dicemaster.game.model.Dice;
import org.junit.Test;

import static org.junit.Assert.*;

public class PokerScoreTest {

    private static final int N = 5;

    @Test
    public void pokerTest() {

        Dice[] set1 = new Dice[N];
        set1[0] = new Dice();
        set1[0].setValue(Dice.Value.ONE);
        set1[1] = new Dice();
        set1[1].setValue(Dice.Value.TWO);
        set1[2] = new Dice();
        set1[2].setValue(Dice.Value.THREE);
        set1[3] = new Dice();
        set1[3].setValue(Dice.Value.FOUR);
        set1[4] = new Dice();
        set1[4].setValue(Dice.Value.FIVE);
        assertEquals(PokerScore.getScore(set1), 130);

        set1[0].setValue(Dice.Value.ONE);
        set1[1].setValue(Dice.Value.ONE);
        set1[2].setValue(Dice.Value.THREE);
        set1[3].setValue(Dice.Value.THREE);
        set1[4].setValue(Dice.Value.ONE);
        assertEquals(PokerScore.getScore(set1), 200 + 9);

        set1[0].setValue(Dice.Value.ONE);
        set1[1].setValue(Dice.Value.TWO);
        set1[2].setValue(Dice.Value.TWO);
        set1[3].setValue(Dice.Value.THREE);
        set1[4].setValue(Dice.Value.ONE);
        assertEquals(PokerScore.getScore(set1), 50 + 6);

        set1[0].setValue(Dice.Value.ONE);
        set1[1].setValue(Dice.Value.TWO);
        set1[2].setValue(Dice.Value.THREE);
        set1[3].setValue(Dice.Value.FIVE);
        set1[4].setValue(Dice.Value.SIX);
        assertEquals(PokerScore.getScore(set1), 17);

        set1[0].setValue(Dice.Value.ONE);
        set1[1].setValue(Dice.Value.TWO);
        set1[2].setValue(Dice.Value.THREE);
        set1[3].setValue(Dice.Value.THREE);
        set1[4].setValue(Dice.Value.THREE);
        assertEquals(PokerScore.getScore(set1), 100 + 9);
    }
}
