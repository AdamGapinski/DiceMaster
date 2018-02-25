package agh.to2.dicemaster.game.poker;

import agh.to2.dicemaster.common.api.MoveDTO;
import agh.to2.dicemaster.server.api.GameParticipant;
import agh.to2.dicemaster.server.api.PlayerEventHandler;

public class PokerPlayerEventHandler implements PlayerEventHandler {
    private PokerGameManager manager;
    private GameParticipant player;
    public PokerPlayerEventHandler(PokerGameManager manager, GameParticipant player) {
        this.manager = manager;
        this.player = player;
    }


    @Override
    public void onMoveRequest(MoveDTO moveDTO) {
        manager.onPlayerMove(moveDTO, player);
    }

    @Override
    public void onPlayerLeft() {
        manager.onPlayerLeft(player);
    }
}
