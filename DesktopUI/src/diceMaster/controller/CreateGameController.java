package diceMaster.controller;

import agh.to2.dicemaster.client.api.ServerGame;
import agh.to2.dicemaster.common.api.GameConfigDTO;
import agh.to2.dicemaster.common.api.GameType;
import agh.to2.dicemaster.common.api.UserType;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class CreateGameController extends Pane {
    private Stage dialogStage;
    private DiceMasterOverviewController diceMasterOverviewController;

    @FXML
    private TextField tableNameTextFiled;

    @FXML
    private CheckBox joinAsPlayerCheckBox;

    @FXML
    private CheckBox joinAsObserverCheckBox;

    @FXML
    private ComboBox<String> gameTypeComboBox;

    @FXML
    private Spinner<Integer> maxPlayersSpinner;

    @FXML
    private Spinner<Integer> easyBotsSpinner;

    @FXML
    private Spinner<Integer> hardBotsSpinner;

    @FXML
    private Spinner<Integer> roundsToWinSpinner;


    public CreateGameController() {
    }

    public void init(Stage stage, DiceMasterOverviewController diceMasterOverviewController) {
        this.dialogStage = stage;
        this.diceMasterOverviewController = diceMasterOverviewController;
        makeSpinnerEditableOnlyForNumbers(maxPlayersSpinner);
        makeSpinnerEditableOnlyForNumbers(easyBotsSpinner);
        makeSpinnerEditableOnlyForNumbers(hardBotsSpinner);
        this.dialogStage.setResizable(false);
    }

    public void handleJoinAsPlayerCheckBox() {
        joinAsPlayerCheckBox.setSelected(true);
        joinAsObserverCheckBox.setSelected(false);
    }

    public void handleJoinAsObserverCheckBox() {
        joinAsPlayerCheckBox.setSelected(false);
        joinAsObserverCheckBox.setSelected(true);
    }

    public void handleCancelClicked() {
        dialogStage.close();
    }

    public void handleCreateClicked() {
        String tableName = tableNameTextFiled.getText();
        int roundsToWin = roundsToWinSpinner.getValue();
        int maxPlayers = maxPlayersSpinner.getValue();
        GameType gameType = fromStringToGameType(gameTypeComboBox.getValue());
        int hardBotsCount = hardBotsSpinner.getValue();
        int easyBotsCount = easyBotsSpinner.getValue();

        UserType userType = UserType.OBSERVER;
        if (joinAsPlayerCheckBox.isSelected())
            userType = UserType.PLAYER;

        if (tableName.isEmpty()) {
            showAlert("Table's name field cannot be empty!");
            return;
        }
        if (roundsToWin < 1) {
            showAlert("Rounds to win must be bigger than 0!");
            return;
        }
        if (tableName.startsWith(" ")) {
            showAlert("Table's name cannot start with white char!");
            return;
        }
        if (maxPlayers + easyBotsCount + hardBotsCount <= 1) {
            showAlert("There has to be at least 2 game participants (bots/players) !");
            return;
        }

        GameConfigDTO gameConfigDTO = new GameConfigDTO(
                tableName,
                maxPlayers,
                gameType,
                hardBotsCount,
                easyBotsCount,
                roundsToWin);

        ServerGame serverGame = this.diceMasterOverviewController.getServer().createGame(
                gameConfigDTO,
                this.diceMasterOverviewController.showGame(),
                userType);

        if (serverGame == null) {
            this.showAlert("Couldn't create and connect to game!!");
            this.dialogStage.close();
            this.diceMasterOverviewController.showGamesTable();
        } else {
            this.diceMasterOverviewController.getInGameController().setServerGame(serverGame);
            this.dialogStage.close();
        }
    }

    private <T> void commitEditorText(Spinner<T> spinner) {
        if (!spinner.isEditable()) return;
        String text = spinner.getEditor().getText();
        SpinnerValueFactory<T> valueFactory = spinner.getValueFactory();
        if (valueFactory != null) {
            StringConverter<T> converter = valueFactory.getConverter();
            if (converter != null) {
                T value = converter.fromString(text);
                valueFactory.setValue(value);
            }
        }
    }

    private GameType fromStringToGameType(String gameTypeString) {
        if (Objects.equals(gameTypeString, "Poker"))
            return GameType.POKER;
        if (Objects.equals(gameTypeString, "N+"))
            return GameType.NPLUS;
        if (Objects.equals(gameTypeString, "N*"))
            return GameType.NTIMES;
        // to prevent nulls
        return GameType.POKER;
    }

    private void makeSpinnerEditableOnlyForNumbers(Spinner<Integer> spinner) {
        spinner.focusedProperty().addListener((ObservableValue<? extends Boolean> s, Boolean ov, Boolean nv) -> {
            if (nv) return;
            commitEditorText(spinner);
        });
        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 ||
                        parsePosition.getIndex() < c.getControlNewText().length()) {
                    return null;
                }
            }
            return c;
        };
        TextFormatter<Integer> priceFormatter = new TextFormatter<Integer>(
                new IntegerStringConverter(), 0, filter);
        spinner.getEditor().setTextFormatter(priceFormatter);
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("DiceMaster - Create game");
        alert.setHeaderText("DiceMaster - Create game");
        alert.setContentText(alertMessage);
        alert.show();
    }
}
