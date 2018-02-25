package agh.to2.dicemaster.game.model;

import agh.to2.dicemaster.game.poker.PokerGameManager;

import java.util.concurrent.TimeUnit;

public class Timer implements Runnable {

    private PokerGameManager gameManager;
    private int secondsToCount;
    private boolean delay = false;

    public Timer(PokerGameManager gameManager, int secondsToCount) {
        this.gameManager = gameManager;
        this.secondsToCount = secondsToCount;
    }

    public boolean getDelay() {
        return delay;
    }

    @Override
    public void run() {
        boolean kickPlayer;
        while (!gameManager.hasEnded()) {
            delay = false;
            kickPlayer = true;
            try {
                TimeUnit.SECONDS.sleep(secondsToCount);
            } catch (InterruptedException ie) {
                kickPlayer = false;
            }
            delay = true;
            gameManager.onTurnEnd(kickPlayer);
        }
    }

}

