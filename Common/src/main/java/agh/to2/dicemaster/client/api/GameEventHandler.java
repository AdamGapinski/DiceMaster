package agh.to2.dicemaster.client.api;

import agh.to2.dicemaster.common.api.GameDTO;

public interface GameEventHandler {
    void onGameChange(GameDTO gameDTO);
}
