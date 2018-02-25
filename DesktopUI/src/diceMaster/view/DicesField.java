package diceMaster.view;

import javafx.scene.Group;
import agh.to2.dicemaster.common.api.DiceNumbers;

import java.util.LinkedList;
import java.util.List;

public class DicesField extends Group {
    private List<DiceView> diceViews = new LinkedList<>();
    private double dicesFiledScale = 1;

    public DicesField() {
        for (int i = 0; i < 5; i++) {
            DiceView d = new DiceView();
            d.setLayoutX(0 + 100 * i * dicesFiledScale);

            d.setLayoutY(0);
            this.diceViews.add(d);
            this.getChildren().add(d);
        }
    }

    public void setCanBeSelected() {
        for (DiceView diceView : diceViews) {
            diceView.setCanBeSelected();
        }
    }

    private int convertEnumToInt(DiceNumbers number) {
        switch (number) {
            case ONE:
                return 1;
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            default:
                return -1;
        }
    }

    public void setDicesDots(DiceNumbers[] dotsToSet) {
        for (int i = 0; i < 5; i++) {
            diceViews.get(i).setNumberOfDots(convertEnumToInt(dotsToSet[i]));
        }
    }

    public List<DiceView> getDiceViews() {
        return diceViews;
    }

    public void setDicesFiledScale(double dicesFiledScale) {
        this.dicesFiledScale = dicesFiledScale;
        for (DiceView d : diceViews) {
            d.setDiceScale(dicesFiledScale);
        }
        reLocate();
    }

    private void reLocate() {
        int i = 0;
        for (DiceView d : diceViews) {
            d.setLayoutX(0 + 100 * i * dicesFiledScale);
            d.setLayoutY(0);
            i++;
        }
    }
}
