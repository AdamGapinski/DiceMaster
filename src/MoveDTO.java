public class MoveDTO implements DTO{
    public final boolean [] dicesToReroll = new boolean[6];

    public MoveDTO(boolean d1, boolean d2, boolean d3, boolean d4, boolean d5, boolean d6){
        this.dicesToReroll[0] = d1;
        this.dicesToReroll[1] = d2;
        this.dicesToReroll[2] = d3;
        this.dicesToReroll[3] = d4;
        this.dicesToReroll[4] = d5;
        this.dicesToReroll[5] = d6;
    }

    @Override
    public void fromJSON(String JSON) {

    }

    @Override
    public String toJSON() {
        return null;
    }
}
