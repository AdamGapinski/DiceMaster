package diceMaster.view;

import agh.to2.dicemaster.common.api.UserInGame;
import javafx.application.Platform;
import javafx.scene.Group;

import java.util.List;

public class UserInGameListView extends Group {

    public UserInGameListView() {
    }

    public void init(List<UserInGame> usersInGame) {
        int i = 0;
        for (UserInGame userInGame : usersInGame) {
            UserInGameFilled u = new UserInGameFilled();
            u.init(userInGame);
            u.setLayoutY(40 * i);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    getChildren().add(u);
                }});
            i++;
        }
    }
}
