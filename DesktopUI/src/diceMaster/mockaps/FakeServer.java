package diceMaster.mockaps;

import agh.to2.dicemaster.client.api.GameEventHandler;
import agh.to2.dicemaster.client.api.Server;
import agh.to2.dicemaster.client.api.ServerGame;
import agh.to2.dicemaster.common.api.*;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FakeServer implements Server {

    private String userName;

    @Override
    public ServerGame createGame(GameConfigDTO gameConfigDTO, GameEventHandler gameEventHandler, UserType userType) {
        return new FakeServerGame(new GameDTO(new Random().nextInt(1000), gameConfigDTO, null, null), this.userName, gameEventHandler, userType);
    }

    @Override
    public ServerGame requestJoinGame(GameDTO gameDTO, GameEventHandler gameEventHandler, UserType userType) {
        if (userType == UserType.PLAYER)
            return new FakeServerGame(gameDTO, this.userName, gameEventHandler, userType);

        return new FakeServerGame(gameDTO, this.userName, gameEventHandler, userType);
    }

    @Override
    public List<GameDTO> getAvailableGames() {
        DiceNumbers[] aaa = new DiceNumbers[5];
        aaa[0] = DiceNumbers.UNKNOWN;
        aaa[1] = DiceNumbers.UNKNOWN;
        aaa[2] = DiceNumbers.UNKNOWN;
        aaa[3] = DiceNumbers.UNKNOWN;
        aaa[4] = DiceNumbers.UNKNOWN;
        GameConfigDTO gc = new GameConfigDTO("aaa", 5, GameType.NPLUS, 2, 2, 2);
        LinkedList<UserInGame> p = new LinkedList<>();
        p.add(new UserInGame("User 1", new Dices(aaa), 0, false));
        LinkedList<String> obs = new LinkedList<>();
        obs.add("bot1");
        LinkedList<GameDTO> toR = new LinkedList<>();
        toR.add(new GameDTO(1, gc, p, obs));
        return toR;
    }

    @Override
    public boolean registerClient(String username) {
        this.userName = username;
        return true;
    }
}
