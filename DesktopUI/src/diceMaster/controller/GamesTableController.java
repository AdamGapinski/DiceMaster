package diceMaster.controller;


import agh.to2.dicemaster.client.api.ServerGame;
import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.UserType;
import diceMaster.Main;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class GamesTableController {
    private DiceMasterOverviewController diceMasterOverviewController;
    private ObservableList<GameDTO> listOfGames;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button createGameButton;

    @FXML
    private Button joinGameAsPlayerButton;

    @FXML
    private Button joinGameAsObserverButton;

    @FXML
    private TableView<GameDTO> gamesTable;

    @FXML
    private TableColumn<GameDTO, String> tableNameColumn;

    @FXML
    private TableColumn<GameDTO, String> tableTypeColumn;

    @FXML
    private TableColumn<GameDTO, String> playersOnTableColumn;

    @FXML
    private TableColumn<GameDTO, Integer> easyBotsNumberColumn;

    @FXML
    private TableColumn<GameDTO, Integer> hardBotsNumberColumn;


    public void init(DiceMasterOverviewController diceMasterOverviewController) {
        this.diceMasterOverviewController = diceMasterOverviewController;
        this.listOfGames = FXCollections.observableArrayList();
        this.bindSizeProperties();
        this.gamesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.tableNameColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().getGameConfig().getTableName()));
        this.tableTypeColumn.setCellValueFactory(dataValue -> new SimpleStringProperty(dataValue.getValue().getGameConfig().getGameType().toString()));

        this.playersOnTableColumn.setCellValueFactory(
                dataValue -> new SimpleStringProperty(
                        String.valueOf(
                                (dataValue.getValue().getPlayers() == null) ? 0 : dataValue.getValue().getPlayers().size())
                                + "/" + String.valueOf(dataValue.getValue().getGameConfig().getMaxPlayers())));
        this.easyBotsNumberColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().getGameConfig().getEasyBotsCount()));
        this.hardBotsNumberColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().getGameConfig().getHardBotsCount()));
        this.listOfGames.addAll(this.diceMasterOverviewController.getServer().getAvailableGames());
        this.gamesTable.setItems(listOfGames);
    }

    private void bindSizeProperties() {
        this.gamesTable.layoutXProperty().bind(this.borderPane.widthProperty().divide(18.181));
        this.gamesTable.layoutYProperty().bind(this.borderPane.heightProperty().divide(9.03));
        this.gamesTable.minWidthProperty().bind(this.borderPane.widthProperty().subtract(390));

        this.gamesTable.minHeightProperty().bind(this.borderPane.heightProperty().subtract(200));
        this.gamesTable.maxHeightProperty().bind(this.borderPane.heightProperty().subtract(200));

        this.tableNameColumn.minWidthProperty().bind(this.gamesTable.widthProperty().divide(2.1));
        this.tableNameColumn.maxWidthProperty().bind(this.gamesTable.widthProperty().divide(2.1));

        this.tableTypeColumn.minWidthProperty().bind(this.gamesTable.widthProperty().divide(10));
        this.tableTypeColumn.maxWidthProperty().bind(this.gamesTable.widthProperty().divide(10));

        this.playersOnTableColumn.minWidthProperty().bind(this.gamesTable.widthProperty().divide(7));
        this.playersOnTableColumn.maxWidthProperty().bind(this.gamesTable.widthProperty().divide(7));

        this.easyBotsNumberColumn.minWidthProperty().bind(this.gamesTable.widthProperty().divide(7));
        this.easyBotsNumberColumn.maxWidthProperty().bind(this.gamesTable.widthProperty().divide(7));

        this.hardBotsNumberColumn.minWidthProperty().bind(this.gamesTable.widthProperty().divide(7));
        this.hardBotsNumberColumn.maxWidthProperty().bind(this.gamesTable.widthProperty().divide(7));

        this.createGameButton.layoutXProperty().bind(
                this.gamesTable.layoutXProperty().add(this.gamesTable.widthProperty().add(50)));
        this.createGameButton.layoutYProperty().bind(
                this.gamesTable.layoutYProperty().add(30));

        this.joinGameAsPlayerButton.layoutXProperty().bind(
                this.gamesTable.layoutXProperty().add(this.gamesTable.widthProperty().add(50)));
        this.joinGameAsPlayerButton.layoutYProperty().bind(
                this.gamesTable.layoutYProperty().add(75));

        this.joinGameAsObserverButton.layoutXProperty().bind(
                this.gamesTable.layoutXProperty().add(this.gamesTable.widthProperty().add(50)));
        this.joinGameAsObserverButton.layoutYProperty().bind(
                this.gamesTable.layoutYProperty().add(120));
    }

    private void showCreateGameDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CreateGameDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create game");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.diceMasterOverviewController.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            CreateGameController presenter = loader.getController();
            presenter.init(dialogStage, this.diceMasterOverviewController);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createGameActionHandler() {
        showCreateGameDialog();
    }

    public void refreshGamesTable() {
        int selectedGame = -1;
        if (!gamesTable.getSelectionModel().isEmpty()) {
            selectedGame = gamesTable.getSelectionModel().getSelectedItem().getId();
        }

        List<GameDTO> listFromServer = this.diceMasterOverviewController.getServer().getAvailableGames();
        if (listFromServer.size() > 0) {
            this.listOfGames.clear();
            this.listOfGames.addAll(listFromServer);
        } else {
            // double check when list is empty for lower possibility of short error connection with server
            listFromServer = this.diceMasterOverviewController.getServer().getAvailableGames();
            this.listOfGames.clear();
            this.listOfGames.addAll(listFromServer);
        }

        if (selectedGame != -1) {
            for (GameDTO gameDTO : listOfGames) {
                if (gameDTO.getId() == selectedGame) {
                    gamesTable.getSelectionModel().select(gameDTO);
                }
            }
        }
    }

    public void joinAsPlayerGameActionHandler() {
        GameDTO selectedGame = gamesTable.getSelectionModel().getSelectedItem();
        if (selectedGame != null)
            if (selectedGame.getPlayers() != null)
                if (selectedGame.getPlayers().size() < selectedGame.getGameConfig().getMaxPlayers())
                    this.joinToGame(selectedGame, UserType.PLAYER);
                else
                    this.showAlert("You cannot join the game as player when there is maximum number of player in game already!!!");
            else{
                this.joinToGame(selectedGame, UserType.PLAYER);
            }
    }

    public void joinAsObserverGameActionHandler() {
        GameDTO selectedGame = gamesTable.getSelectionModel().getSelectedItem();
        if (selectedGame != null)
            this.joinToGame(selectedGame, UserType.OBSERVER);
    }

    private void joinToGame(GameDTO gameDTO, UserType userType) {
        ServerGame serverGame = this.diceMasterOverviewController.getServer().requestJoinGame(
                gameDTO,
                this.diceMasterOverviewController.showGame(),
                userType);
        if (serverGame == null) {
            this.showAlert("Couldn't connect to game!!");
            this.diceMasterOverviewController.showGamesTable();
        } else {
            this.diceMasterOverviewController.getInGameController().setServerGame(serverGame);
        }
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("DiceMaster - Games Tables");
        alert.setHeaderText("DiceMaster - Games Tables");
        alert.setContentText(alertMessage);
        alert.show();
    }

}