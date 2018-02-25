package agh.to2.dicemaster.common.api;

public class UserInGame {


    private String userName;
    private Dices dices;
    private Integer score;
    private boolean isHisTurn;

    public UserInGame() { // Jackson2 default constructor
    }

    public UserInGame(String userName, Dices dices, Integer score, boolean isHisTurn) {
        this.userName = userName;
        this.dices = dices;
        this.score = score;
        this.isHisTurn = isHisTurn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Dices getDices() {
        return dices;
    }

    public void setDices(Dices dices) {
        this.dices = dices;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public boolean isHisTurn() {
        return isHisTurn;
    }

    public void setHisTurn(boolean hisTurn) {
        isHisTurn = hisTurn;
    }
}
