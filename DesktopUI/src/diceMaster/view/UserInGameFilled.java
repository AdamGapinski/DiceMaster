package diceMaster.view;

import agh.to2.dicemaster.common.api.UserInGame;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class UserInGameFilled extends Group {
    private Text nickText = new Text();
    private DicesField dices = new DicesField();
    private Text scoreText = new Text();

    public UserInGameFilled() {
    }

    public void init(UserInGame player) {
        this.nickText.setText(player.getUserName());
        this.nickText.setY(-10.0);
        this.scoreText.setText(player.getScore().toString());
        this.scoreText.setX(80.0);
        this.scoreText.setY(10.0);
        this.dices.setDicesFiledScale(0.15);
        this.dices.setDicesDots(player.getDices().getDicesScore());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getChildren().clear();
                getChildren().add(nickText);
                getChildren().add(scoreText);
                getChildren().add(dices);
            }});
    }
}
