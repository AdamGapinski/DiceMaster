package diceMaster.model.gui;

import diceMaster.controller.GamesTableController;

import java.util.TimerTask;

public class AvailableGamesChecker extends TimerTask {
    private GamesTableController gamesTableController;

    public AvailableGamesChecker(GamesTableController gamesTableController) {
        this.gamesTableController = gamesTableController;
    }

    @Override
    public void run() {
        this.gamesTableController.refreshGamesTable();
        System.out.println("Checked!");
    }
}
