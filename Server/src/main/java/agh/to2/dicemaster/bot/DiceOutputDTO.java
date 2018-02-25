package agh.to2.dicemaster.bot;

import java.util.HashMap;
import java.util.List;

public class DiceOutputDTO {

    // number to value
    private HashMap<Integer, Integer> dicesToThrow;
    private List<Integer> myInput;
    private int scoreLeft;

    public DiceOutputDTO(int score){
        this.scoreLeft = score;
    }

    public int getScoreLeft() {
        return scoreLeft;
    }

    public void setScoreLeft(int scoreLeft) {
        this.scoreLeft = scoreLeft;
    }

    public List<Integer> getMyInput() {
        return myInput;
    }

    public void setMyInput(List<Integer> myInput) {
        this.myInput = myInput;
    }

    public void subtractNumToFinishGame(int numOnDiceToPutAside) {
        scoreLeft -= numOnDiceToPutAside;
    }

    public void divideNumToFinishGame(int numOnDiceToPutAside) {
        scoreLeft /= numOnDiceToPutAside;
    }

    public HashMap<Integer, Integer> getDicesToThrow() {
        return dicesToThrow;
    }

    public void setDicesToThrow(HashMap<Integer, Integer> dicesToThrow) {
        this.dicesToThrow = dicesToThrow;
    }
}
