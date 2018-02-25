package agh.to2.dicemaster.common.DTO;

import agh.to2.dicemaster.common.api.GameConfigDTO;
import agh.to2.dicemaster.common.api.UserType;

public class CreateGameRequestDTO {
    private GameConfigDTO gameConfigDTO;
    private UserType userType;

    public CreateGameRequestDTO() {
    }

    public CreateGameRequestDTO(GameConfigDTO gameConfigDTO, UserType userType) {
        this.gameConfigDTO = gameConfigDTO;
        this.userType = userType;
    }

    public GameConfigDTO getGameConfigDTO() {
        return gameConfigDTO;
    }

    public void setGameConfigDTO(GameConfigDTO gameConfigDTO) {
        this.gameConfigDTO = gameConfigDTO;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
