package agh.to2.dicemaster.model.server;

import agh.to2.dicemaster.common.api.*;

public abstract class ServerGame {

    private GameDTO gameDTO;

    public ServerGame(GameDTO gameDTO) {
        this.gameDTO = gameDTO;
    }

    public abstract void makeMove(MoveDTO moveDTO);
    public abstract void leaveGame();

    public GameDTO getGameDTO() {
        return gameDTO;
    }
}
