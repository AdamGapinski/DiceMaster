package agh.to2.dicemaster.bot;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DiceInputDTO {

    private int scoreToWin;

    private List<Integer> myInput;
    private HashMap<Integer, Integer> dicesRerolled;
    private List<List<Integer>> otherPlayersInputs;

    public void setMyInput(List<Integer> myInput) {
        this.myInput = myInput;
    }

    public void setDicesRerolled(HashMap<Integer, Integer> dicesBeforeRerolling) {
        dicesRerolled = new HashMap<>();
        Set<Integer> indexes = dicesBeforeRerolling.keySet();
        for(Integer index : indexes){
            dicesRerolled.put(index, myInput.get(index));
        }
    }

    public HashMap<Integer, Integer> getDicesRerolled() {
        return dicesRerolled;
    }

    public void setOtherPlayersInputs(List<List<Integer>> otherPlayersInputs) {
        this.otherPlayersInputs = otherPlayersInputs;
    }

    public int getScoreToWin() {
        return scoreToWin;
    }

    public List<Integer> getMyInput() {
        return myInput;
    }

    public List<List<Integer>> getOtherPlayersInputs() {
        return otherPlayersInputs;
    }

    public DiceInputDTO(int scoreToWin) {
        this.scoreToWin = scoreToWin;
    }
}
