package agh.to2.dicemaster.model.server;

import agh.to2.dicemaster.common.api.*;
import agh.to2.dicemaster.model.gui.GameEventHandler;


import java.util.List;


public interface Server {

    ServerGame createGame(GameConfigDTO gameConfigDTO, GameEventHandler gameEventHandler, UserType userType);
    ServerGame requestJoinGame(GameDTO gameDTO, GameEventHandler gameEventHandler, UserType userType);

    List<GameDTO> getAvailableGames();
    boolean registerClient(String username);
}
