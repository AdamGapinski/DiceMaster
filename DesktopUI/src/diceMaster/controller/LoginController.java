package diceMaster.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class LoginController {
    private DiceMasterOverviewController diceMasterOverviewController;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField userNameTextField;
    @FXML
    private Text loginText;
    @FXML
    private Button loginButton;

    public void init(DiceMasterOverviewController diceMasterOverviewController) {
        this.diceMasterOverviewController = diceMasterOverviewController;
        this.bindSizeProperties();
    }

    private void bindSizeProperties() {
        this.userNameTextField.layoutXProperty().bind(this.borderPane.widthProperty().divide(2.3));
        this.userNameTextField.layoutYProperty().bind(this.borderPane.heightProperty().divide(2.186));

        this.loginText.layoutXProperty().bind(this.userNameTextField.layoutXProperty().subtract(140));
        this.loginText.layoutYProperty().bind(this.borderPane.heightProperty().divide(2.06));

        this.loginButton.layoutXProperty().bind(this.userNameTextField.layoutXProperty().subtract(20));
        this.loginButton.layoutYProperty().bind(this.userNameTextField.layoutYProperty().add(45));
    }

    public void handleLoginEvent() {
        if (userNameTextField.getText().isEmpty()) {
            showAlert("Type your nick first!");
            return;
        }
        if (userNameTextField.getText().startsWith("bot#")) {
            showAlert("Your nick can't start with \"bot#\"!");
            return;
        }
        if (userNameTextField.getText().startsWith(" ")) {
            showAlert("Your nick can't start with white character!");
            return;
        }

        // if server will add some exceptions put here try catch
        if (!diceMasterOverviewController.getServer().registerClient(userNameTextField.getText())) {
            showAlert("Couldn't login!");
            return;
        }
        this.diceMasterOverviewController.setUserNickName(userNameTextField.getText());
        this.diceMasterOverviewController.showGamesTable();
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("DiceMaster - Login");
        alert.setHeaderText("DiceMaster - Login");
        alert.setContentText(alertMessage);
        alert.show();
    }
}

