package diceMaster.controller;

import agh.to2.dicemaster.client.api.GameEventHandler;
import agh.to2.dicemaster.client.api.ServerGame;
import agh.to2.dicemaster.common.api.*;
import diceMaster.view.DicesField;
import diceMaster.view.UserInGameFilled;
import diceMaster.view.UserInGameListView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;


public class InGameController implements GameEventHandler {
    private DiceMasterOverviewController appController;
    private ServerGame serverGame;
    private Text timerText;
    private Thread timerThread;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button reRollButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button skipTurnButton;

    @FXML
    private DicesField dicesField;

    @FXML
    private Line splitGameWindowLine;

    @FXML
    private UserInGameListView playersWaitingForMove;

    @FXML
    private UserInGameListView playersMoved;

    @FXML
    private UserInGameFilled currentUser;

    @FXML
    private Text tablesTypeText;

    @FXML
    private Text roundsToWin;

    @FXML
    private Text scoreInRound;

    @FXML
    private Group mainGroup;

    public void setAppController(DiceMasterOverviewController appController) {
        this.appController = appController;
        this.bindSizeProperties();
        this.reRollButton.setDisable(true);
        this.dicesField.setDicesFiledScale(1);
    }

    private void bindSizeProperties() {
        this.splitGameWindowLine.endXProperty().bind(borderPane.widthProperty());
    }

    public void onGameChange(GameDTO game) {
        if (!isStillInGame(game.getPlayers(), game.getObservers())) {
            this.showAlert("You were kicked from game!");
            this.appController.showGamesTable();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playersWaitingForMove.getChildren().clear();
                playersMoved.getChildren().clear();
                currentUser.getChildren().clear();
            }
        });


        for (UserInGame u : game.getPlayers()) {
            if (u.getUserName().equals(this.appController.getUserNickName())) {
                this.dicesField.setDicesDots(u.getDices().getDicesScore());
                if (u.isHisTurn()) {
                    this.reRollButton.setDisable(false);
                    this.skipTurnButton.setDisable(false);
                    this.dicesField.setCanBeSelected();
                }
            }
        }

        this.checkIfWin(game.getPlayers(), game);
        this.setPlayersListToView(game.getPlayers());

        if (serverGame.getGameDTO().getGameConfig().getGameType() != GameType.POKER)
            scoreInRound.setText("Score to win round: " + String.valueOf(serverGame.getGameDTO().getScoreToWin()));
    }

    private void startTimer() {
        if (this.timerText == null) {
            this.timerText = new Text();
            this.mainGroup.getChildren().add(timerText);
        }
        timerText.setText("30");
        timerText.setFont(Font.font(30));
        timerText.setLayoutX(870);
        timerText.setLayoutY(490);
        if (this.timerThread != null) {
            // maybe change it
            this.timerThread.stop();
        }
        this.timerThread = new Thread(() -> {
            while (Integer.valueOf(timerText.getText()) > 0) {
                try {
                    Thread.sleep(1000);
                    timerText.setText(String.valueOf(Integer.valueOf(timerText.getText()) - 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    showAlert("Something went wrong with timer!!!");
                }
            }
        });
        this.timerThread.start();
    }

    public void setServerGame(ServerGame serverGame) {
        this.serverGame = serverGame;
        this.tablesTypeText.setText("Table's type: " + serverGame.getGameDTO().getGameConfig().getGameType().toString());
        this.roundsToWin.setText("Rounds to win: " + String.valueOf(serverGame.getGameDTO().getGameConfig().getRoundsToWin()));
        this.onGameChange(serverGame.getGameDTO());
    }

    private void setPlayersListToView(List<UserInGame> players) {
        List<UserInGame> beforeMove = new LinkedList<>();
        List<UserInGame> afterMove = new LinkedList<>();
        boolean foundCurrentPlayer = false;

        for (UserInGame player : players) {
            if (player.isHisTurn()) {
                foundCurrentPlayer = true;
                this.currentUser.init(player);
                this.startTimer();
                System.out.println(player);
                continue;
            }
            if (foundCurrentPlayer) {
                beforeMove.add(player);
            } else {
                afterMove.add(player);
            }
        }
        this.playersMoved.init(afterMove);
        this.playersWaitingForMove.init(beforeMove);
    }

    public void handleExit() {
        this.serverGame.leaveGame();
        this.appController.showGamesTable();
    }

    public void handleReRoll() {
        boolean[] dicesToReroll = new boolean[5];
        for (int i = 0; i < 5; i++)
            dicesToReroll[i] = this.dicesField.getDiceViews().get(i).isSelected();
        MoveDTO moveDTO = new MoveDTO(dicesToReroll);

        for (int i = 0; i < 5; i++)
            if (dicesField.getDiceViews().get(i).isSelected())
                dicesField.getDiceViews().get(i).setSelected(false);
        this.reRollButton.setDisable(true);
        this.skipTurnButton.setDisable(true);

        this.serverGame.makeMove(moveDTO);
    }

    public void handleSkipTurn() {
        boolean[] dicesToReroll = new boolean[5];
        for (int i = 0; i < 5; i++)
            dicesToReroll[i] = false;

        MoveDTO moveDTO = new MoveDTO(dicesToReroll);

        for (int i = 0; i < 5; i++)
            if (dicesField.getDiceViews().get(i).isSelected())
                dicesField.getDiceViews().get(i).setSelected(false);

        this.reRollButton.setDisable(true);
        this.skipTurnButton.setDisable(true);

        this.serverGame.makeMove(moveDTO);
    }


    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("DiceMaster - Game in progress");
        alert.setHeaderText("DiceMaster - Game in progress");
        alert.setContentText(alertMessage);
        alert.show();
    }

    private boolean isStillInGame(List<UserInGame> players, List<String> observers) {
        if (players != null)
            for (UserInGame userInGame : players) {
                if (userInGame.getUserName().equals(this.appController.getUserNickName()))
                    return true;
            }

        if (observers != null)
            for (String observer : observers) {
                if (observer.equals(this.appController.getUserNickName()))
                    return true;
            }
        return false;
    }

    private boolean isGameStarted(List<UserInGame> players){
        for(UserInGame userInGame : players)
            if(userInGame.isHisTurn())
                return true;
        return false;
    }

    private void checkIfWin(List<UserInGame> players, GameDTO gameDTO){
        for(UserInGame userInGame : players)
            if(userInGame.getScore() == gameDTO.getGameConfig().getRoundsToWin())
                if( userInGame.getUserName() == this.appController.getUserNickName()) {
                    showAlert("You won the game!!!!!!!!!!!!");
                    this.appController.showGamesTable();
                }else{
                    showAlert("You lost the game!!!!!!!!!!!!");
                    this.appController.showGamesTable();
                }
    }
}
