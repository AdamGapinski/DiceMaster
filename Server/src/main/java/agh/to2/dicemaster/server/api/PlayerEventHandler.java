package agh.to2.dicemaster.server.api;

import agh.to2.dicemaster.common.api.MoveDTO;

public interface PlayerEventHandler {
    void onMoveRequest(MoveDTO moveDTO);

    void onPlayerLeft();
}
