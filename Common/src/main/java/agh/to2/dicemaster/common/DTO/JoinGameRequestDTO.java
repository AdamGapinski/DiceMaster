package agh.to2.dicemaster.common.DTO;

import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.UserType;

public class JoinGameRequestDTO {
    private GameDTO gameDTO;
    private UserType userType;

    public JoinGameRequestDTO() {
    }

    public JoinGameRequestDTO(GameDTO gameDTO, UserType userType) {
        this.gameDTO = gameDTO;
        this.userType = userType;
    }

    public GameDTO getGameDTO() {
        return gameDTO;
    }

    public void setGameDTO(GameDTO gameDTO) {
        this.gameDTO = gameDTO;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
