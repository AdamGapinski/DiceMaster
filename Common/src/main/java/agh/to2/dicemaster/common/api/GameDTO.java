package agh.to2.dicemaster.common.api;

import java.util.List;

public class GameDTO {

    private int id;
    private GameConfigDTO gameConfig;
    private List<UserInGame> players;
    private List<String> observers;
    private Integer scoreToWinInRound;

    public GameDTO() { // Jackson2 default constructor
    }

    public GameDTO(int id, GameConfigDTO gameConfig, List<UserInGame> players, List<String> observers) {
        this.id = id;
        this.gameConfig = gameConfig;
        this.players = players;
        this.observers = observers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameConfigDTO getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(GameConfigDTO gameConfig) {
        this.gameConfig = gameConfig;
    }

    public List<UserInGame> getPlayers() {
        return players;
    }

    public void setPlayers(List<UserInGame> players) {
        this.players = players;
    }

    public List<String> getObservers() {
        return observers;
    }

    public void setObservers(List<String> observers) {
        this.observers = observers;
    }

    public Integer getScoreToWin() {
        return scoreToWinInRound;
    }

    public void setScoreToWin(Integer scoreToWinInRound) {
        this.scoreToWinInRound = scoreToWinInRound;
    }
}
