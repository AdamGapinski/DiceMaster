package diceMaster.controller;

import agh.to2.dicemaster.client.api.Server;
import diceMaster.Main;
import diceMaster.model.gui.AvailableGamesChecker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;

public class DiceMasterOverviewController {
    private Stage primaryStage;
    private Server server;
    private AvailableGamesChecker availableGamesChecker;
    private InGameController inGameController;
    private Timer timer;
    private String userNickName;

    public DiceMasterOverviewController(Stage primaryStage, Server server) {
        this.primaryStage = primaryStage;
        this.server = server;
        try {
            this.primaryStage.setTitle("DiceMaster");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/LoginPane.fxml"));
            BorderPane rootLayout = loader.load();
            LoginController loginController = loader.getController();
            loginController.init(this);
            Scene scene = new Scene(rootLayout);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showGamesTable() {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(Main.class.getResource("view/GamesTablePane.fxml"));
            BorderPane rootLayout = loader.load();
            GamesTableController gamesTableController = loader.getController();
            gamesTableController.init(this);

            this.availableGamesChecker = new AvailableGamesChecker(gamesTableController);
            this.timer = new Timer(true);
            this.timer.schedule(availableGamesChecker, 0, 5000);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InGameController showGame() {
        try {
            this.timer.cancel();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/InGamePane.fxml"));
            BorderPane rootLayout = loader.load();
            this.inGameController = loader.getController();
            inGameController.setAppController(this);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.inGameController;
    }

    public Server getServer() {
        return server;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public InGameController getInGameController() {
        return this.inGameController;
    }

    public String getUserNickName() {
        return userNickName;
    }
}
