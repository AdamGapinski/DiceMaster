package agh.to2.dicemaster.server.api;

import agh.to2.dicemaster.common.api.GameConfigDTO;
import agh.to2.dicemaster.common.api.GameDTO;

import java.util.List;

public abstract class Game {
    private int id;
    private GameConfigDTO gameConfigDTO;

    private List<GameParticipant> players;

    public Game(int id, GameConfigDTO gameConfigDTO) {
        this.id = id;
        this.gameConfigDTO = gameConfigDTO;

        for(int i=0;i<gameConfigDTO.getEasyBotsCount();i++){
            this.addPlayer(new GameParticipant("Bot" + i));
        }

        for(int i=0;i<gameConfigDTO.getHardBotsCount();i++){
            this.addPlayer(new GameParticipant("BotHard + i"));
        }
    }

    public abstract void addObserver(GameParticipant gameParticipant);

    public abstract void addPlayer(GameParticipant gameParticipant);

    public abstract List<GameParticipant> getPlayers();

    public abstract List<GameParticipant> getObservers();

    public abstract GameDTO getGameDTO();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameConfigDTO getGameConfigDTO() {
        return gameConfigDTO;
    }
}
