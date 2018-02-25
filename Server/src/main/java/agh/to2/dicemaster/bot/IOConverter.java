package agh.to2.dicemaster.bot;


import agh.to2.dicemaster.common.api.DiceNumbers;
import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.MoveDTO;
import agh.to2.dicemaster.common.api.UserInGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IOConverter {

    //todo implement other players inputs...
    public DiceInputDTO getDiceInputDTO(GameDTO input, String id) {
        DiceInputDTO diceInputDTO = new DiceInputDTO(input.getScoreToWin());

        List<Integer> diceList = new ArrayList<>();
        for (UserInGame userInGame : input.getPlayers()) {
            if (userInGame.getUserName().equals(id)) {
                for (DiceNumbers number : userInGame.getDices().getDicesScore()) {
                    //masochiści...
                    switch (number) {
                        case ONE:
                            diceList.add(1);
                            break;
                        case TWO:
                            diceList.add(2);
                            break;
                        case THREE:
                            diceList.add(3);
                            break;
                        case FOUR:
                            diceList.add(4);
                            break;
                        case FIVE:
                            diceList.add(5);
                            break;
                        case SIX:
                            diceList.add(6);
                            break;
                        case UNKNOWN:
                            break;
                    }
                }
            }
        }

        diceInputDTO.setMyInput(diceList);

        return diceInputDTO;
    }

    public MoveDTO makeNewMoveDTO(DiceOutputDTO output) {

        boolean[] mask = {false, false, false, false, false};

        for (Object o : output.getDicesToThrow().entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            mask[(Integer) pair.getKey()] = true;
        }

        return  new MoveDTO(mask);

    }
}
