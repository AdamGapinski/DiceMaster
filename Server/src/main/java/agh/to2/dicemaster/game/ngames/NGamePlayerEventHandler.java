package agh.to2.dicemaster.game.ngames;

import agh.to2.dicemaster.common.api.MoveDTO;
import agh.to2.dicemaster.server.api.PlayerEventHandler;

/**
 * Created by werka on 10.01.18.
 */
public class NGamePlayerEventHandler implements PlayerEventHandler {
    GameRunner gameRunner;

    @Override
    public void onMoveRequest(MoveDTO moveDTO) {
        gameRunner.performMove(moveDTO);
    }

    @Override
    public void onPlayerLeft() {

    }
}
