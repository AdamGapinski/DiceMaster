package agh.to2.dicemaster.client.game;


import agh.to2.dicemaster.client.api.Server;
import agh.to2.dicemaster.client.api.ServerGame;
import agh.to2.dicemaster.common.api.UserType;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

// FIXME: 08.01.18 Make the test pass
public class DiceMasterServerGameTest {
    private ServerGame serverGame;

    @Before
    public void setUp() throws Exception {
        Server server = Server.createDiceMasterServer("localhost",
                "guest", "guest");
        String username = String.valueOf(UUID.randomUUID());
        server.registerClient(username);

        serverGame = server.createGame(null, null, UserType.PLAYER);
    }

    @Test
    public void makeMove() throws Exception {
        serverGame.makeMove(null);
    }

    @Test
    public void leaveGame() throws Exception {
        serverGame.leaveGame();
    }
}
