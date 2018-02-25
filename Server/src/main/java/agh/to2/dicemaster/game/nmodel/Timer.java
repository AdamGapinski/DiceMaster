package agh.to2.dicemaster.game.nmodel;

import agh.to2.dicemaster.game.ngames.GameRunner;

import java.util.concurrent.TimeUnit;

public class Timer implements Runnable {

    private GameRunner gameRunner;
    private int secondsToCount;

    public Timer(GameRunner gameRunner, int secondsToCount) {
        this.gameRunner=gameRunner;
        this.secondsToCount = secondsToCount;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(secondsToCount);
        } catch (InterruptedException ie) {

        }
        gameRunner.kickPlayer();

    }
}
