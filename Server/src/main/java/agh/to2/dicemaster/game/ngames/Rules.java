package agh.to2.dicemaster.game.ngames;

import agh.to2.dicemaster.common.api.MoveDTO;
import agh.to2.dicemaster.game.nmodel.Player;
import agh.to2.dicemaster.server.api.GameParticipant;

import java.util.List;

public interface Rules {

    boolean countPoints(Player player);

    void initializeDices(Player player);

    void drawDices(Player player, MoveDTO move);

    Integer getAim();

    int initializeRound(List<GameParticipant> players);

}
