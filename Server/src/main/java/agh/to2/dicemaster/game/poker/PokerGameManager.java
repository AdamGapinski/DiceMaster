package agh.to2.dicemaster.game.poker;


import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.MoveDTO;
import agh.to2.dicemaster.game.model.Player;
import agh.to2.dicemaster.game.model.Timer;
import agh.to2.dicemaster.server.api.GameParticipant;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class PokerGameManager {

    private final static int TURN_DURATION = 20000;
    private final static int ROUND_COUNT = 5;
    private final static int TURNS_PER_ROUND = 3;

    private final PokerGame game;
    private int currentPlayer = -1;
    private int roundNumber = 1;
    private int turnNumber = 1;
    private Thread timerThread;
    private GameState gameState = GameState.PENDING;


    private enum GameState {PENDING, STARTED, ENDED;}

    private List<GameParticipant> participantsToRemove = new LinkedList<>();


    public PokerGameManager(PokerGame game) {
        this.game = game;
    }


    public void kickCurrentPlayer() {
        participantsToRemove.add(game.getPlayerList().get(currentPlayer));
    }

    public synchronized void onTurnEnd(boolean kickPlayer) {
        if(kickPlayer) kickCurrentPlayer();
        currentPlayer++;
        turnNumber++;
        if (currentPlayer >= game.getPlayerList().size()) {
            currentPlayer = 0;
        }
        if (turnNumber > TURNS_PER_ROUND * game.getPlayerList().size()) {
            turnNumber = 1;
            onRoundEnd();
        }
        if (roundNumber > ROUND_COUNT) {
            onGameEnd();
        }
        notifyAllGameParticipants();
    }

    private synchronized void onRoundEnd() {
        findRoundWinner();
        game.removeParticipants(participantsToRemove);
        participantsToRemove.clear();
        game.getPlayerList().forEach(Player::rollAllDices);
        roundNumber++;
        notifyAllGameParticipants();
    }

    public synchronized void onGameStart() {
        gameState = GameState.STARTED;
        currentPlayer = 0;
        timerThread = new Thread(new Timer(this, TURN_DURATION));
        timerThread.start();
    }

    public synchronized void onGameEnd() {
        gameState = GameState.ENDED;
    }

    protected void notifyAllGameParticipants() {
        GameDTO gameDTO = game.getGameDTO();
        game.getPlayers().forEach(player -> player.notifyGameStateChange(gameDTO));
        game.getObservers().forEach(observer -> observer.notifyGameStateChange(gameDTO));
    }

    public synchronized void onPlayerMove(MoveDTO moveDTO, GameParticipant gameParticipant) {
        Player player = game.getPlayerList()
                .stream()
                .filter(p -> p.getId().equals(gameParticipant.getId()))
                .findAny()
                .orElseThrow(IllegalStateException::new);

        if (!game.getPlayerList().get(currentPlayer).equals(player)) return;
        player.rollDices(moveDTO.getDicesToReRoll());
        timerThread.interrupt();
    }

    public synchronized void onPlayerLeft(GameParticipant gameParticipant) {
        participantsToRemove.add(gameParticipant);
    }

    private Player findRoundWinner() {
        Player winner = game.getPlayerList()
                .stream()
                .max(Comparator.comparingInt(Player::getRoundScore))
                .orElseThrow(IllegalStateException::new);
        winner.incereaseGameScore();
        return winner;
    }

    private Player findGameWinner() {
        return game.getPlayerList()
                .stream()
                .max(Comparator.comparingInt(Player::getGameScore))
                .orElseThrow(IllegalStateException::new);
    }

    public boolean hasStarted() {
        return gameState.equals(GameState.STARTED);
    }

    public boolean hasEnded() {
        return gameState.equals(GameState.ENDED);
    }

    public int getRoundNumber() {
        return this.roundNumber;
    }

    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    public int getTurnNumber() {
        return this.turnNumber;
    }

    public List<GameParticipant> getParticipantsToRemove() {
        return this.participantsToRemove;
    }
}
