package agh.to2.dicemaster.bot.model;

import agh.to2.dicemaster.bot.DiceInputDTO;
import agh.to2.dicemaster.bot.DiceOutputDTO;
import agh.to2.dicemaster.bot.IOConverter;
import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.MoveDTO;
import agh.to2.dicemaster.common.api.UserInGame;
import agh.to2.dicemaster.server.api.GameParticipant;
import agh.to2.dicemaster.server.api.PlayerEventHandler;


public abstract class Bot extends GameParticipant {

    protected DiceOutputDTO result = null;
    private PlayerEventHandler playerEventHandler;
    private GameDTO gDto = null;

    abstract DiceOutputDTO getDicesToThrow(DiceInputDTO input);

    @Override
    public void registerPlayerEventHandler(PlayerEventHandler playerEventHandler) {
        this.playerEventHandler = playerEventHandler;

    }

    @Override
    public void notifyGameStateChange(GameDTO gameDTO) {

        for (UserInGame userInGame : gameDTO.getPlayers()) {
            if (userInGame.getUserName().equals(this.getId()))
                if (userInGame.isHisTurn()) {
                    this.gDto = gameDTO;
                    new Thread(this::makeMove).start();
                }
        }

    }

    private void makeMove() {
        if (result == null) {
            result = new DiceOutputDTO(this.gDto.getScoreToWin());
        }

        IOConverter converter = new IOConverter();

        // id is to find user in GameDTO
        DiceInputDTO diceInput = converter.getDiceInputDTO(this.gDto, this.getId());
        diceInput.setDicesRerolled(this.result.getDicesToThrow());
        this.getDicesToThrow(diceInput);

        MoveDTO moveDTO = converter.makeNewMoveDTO(result);

        //this field is assigned to every User
        this.playerEventHandler.onMoveRequest(moveDTO);
    }

}
