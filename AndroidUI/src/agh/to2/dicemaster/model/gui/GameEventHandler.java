package agh.to2.dicemaster.model.gui;

import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.UserType;

public interface GameEventHandler {
    void onGameChange(GameDTO gameDTO);
}
