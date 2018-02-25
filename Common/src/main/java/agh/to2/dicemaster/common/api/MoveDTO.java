package agh.to2.dicemaster.common.api;

public class MoveDTO {

    private boolean[] dicesToReRoll;

    public MoveDTO() { // Jackson2 default constructor
    }

    public MoveDTO(boolean[] dicesToReRoll) {
        this.dicesToReRoll = dicesToReRoll;
    }

    public boolean[] getDicesToReRoll() {
        return dicesToReRoll;
    }

    public void setDicesToReRoll(boolean[] dicesToReRoll) {
        this.dicesToReRoll = dicesToReRoll;
    }

}
