package agh.to2.dicemaster.game.ngames;


import agh.to2.dicemaster.common.api.GameConfigDTO;
import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.GameType;
import agh.to2.dicemaster.common.api.UserInGame;
import agh.to2.dicemaster.game.nmodel.Player;
import agh.to2.dicemaster.server.api.Game;
import agh.to2.dicemaster.server.api.GameParticipant;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ComponentScan(basePackages = {"agh.to2.dicemaster"})
public class NGame extends Game {
    private GameConfigDTO gameConfigDTO;
    private Rules rules;
    private List<GameParticipant> players = new ArrayList<>();
    private List<GameParticipant> observers = new LinkedList<GameParticipant>();
    private GameDTO gameDTO;


    public NGame(int id, GameConfigDTO gameConfigDTO) {
        super(id,gameConfigDTO);
        this.gameConfigDTO=gameConfigDTO;
        if (gameConfigDTO.getGameType() == GameType.NPLUS) {
            this.rules = new NPlus();
        }
        if (gameConfigDTO.getGameType() == GameType.NTIMES) {
            this.rules = new NTimes();
        }

    }

    @Override
    public void addObserver(GameParticipant gameParticipant) {
        observers.add(gameParticipant);
    }

    @Override
    public void addPlayer(GameParticipant gameParticipant) {
        Player player = new Player(gameParticipant);
        players.add(player);

        if(gameConfigDTO.getMaxPlayers()==players.size()){
            createGameDTO();
            startGame();
        }
    }

    public void createGameDTO(){
        GameDTO tmp = new GameDTO();
        tmp.setId(this.getId());
        tmp.setGameConfig(this.gameConfigDTO);
        players.forEach(this::createUserInGame);
    }

    public UserInGame createUserInGame(GameParticipant gameParticipant){
        UserInGame tmp = new UserInGame();
        tmp.setUserName(gameParticipant.getId());
        tmp.setHisTurn(false);
        return tmp;
    }

    private void startGame(){
       new GameRunner(rules,this);
    }

    public void setGameDTO(GameDTO gameDTO){
        this.gameDTO=gameDTO;
    }
    @Override
    public List<GameParticipant> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    @Override
    public List<GameParticipant> getObservers() {
        return observers;
    }

    @Override
    public GameDTO getGameDTO() {
        return this.gameDTO;
    }


}


