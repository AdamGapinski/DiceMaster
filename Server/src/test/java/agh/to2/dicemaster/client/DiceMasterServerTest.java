package agh.to2.dicemaster.client;

import agh.to2.dicemaster.client.api.Server;
import agh.to2.dicemaster.client.api.ServerGame;
import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.UserType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

// FIXME: 08.01.18 Make the test pass
public class DiceMasterServerTest {
    private Server server;
    private String username;

    @Before
    public void setUp() throws Exception {
        server = Server.createDiceMasterServer("localhost",
                "guest", "guest");
        username = String.valueOf(UUID.randomUUID());
    }

    @Test
    public void createGame() throws Exception {
        try {
            server.createGame(null, null, UserType.OBSERVER);
            fail("Expected IllegalStateException with 'User is not registered' message");
        } catch (IllegalStateException e) {
            assertEquals("User is not registered", e.getMessage());
        }

        server.registerClient(username);
        ServerGame serverGame = server.createGame(null, null, UserType.OBSERVER);
        assertNotNull(serverGame);
    }

    @Test
    public void requestJoinGame() throws Exception {
        try {
            server.requestJoinGame(null, null, UserType.OBSERVER);
            fail("Expected IllegalStateException with 'User is not registered' message");
        } catch (IllegalStateException e) {
            assertEquals("User is not registered", e.getMessage());
        }

        server.registerClient(username);
        ServerGame serverGame = server.requestJoinGame(null, null, UserType.OBSERVER);
        assertNotNull(serverGame);
    }

    @Test
    public void getAvailableGames() throws Exception {
        try {
            server.requestJoinGame(null, null, UserType.OBSERVER);
            fail("Expected IllegalStateException with 'User is not registered' message");
        } catch (IllegalStateException e) {
            assertEquals("User is not registered", e.getMessage());
        }

        server.registerClient(username);

        ServerGame serverGame = server.createGame(null, null, UserType.OBSERVER);
        serverGame.leaveGame();

        serverGame = server.createGame(null, null, UserType.OBSERVER);
        serverGame.leaveGame();

        serverGame = server.createGame(null, null, UserType.OBSERVER);
        serverGame.leaveGame();

        List<GameDTO> gameDTOList = server.getAvailableGames();
        assertEquals(gameDTOList.size(), 3);
    }

    @Test
    public void registerClient() throws Exception {
        assertTrue(server.registerClient(username));

        assertFalse(server.registerClient(username));
        assertFalse(server.registerClient(username));
    }
}