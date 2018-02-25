package agh.to2.dicemaster.game.ngames;

import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.MoveDTO;
import agh.to2.dicemaster.common.api.UserInGame;
import agh.to2.dicemaster.game.nmodel.Player;
import agh.to2.dicemaster.game.nmodel.Timer;
import agh.to2.dicemaster.server.api.GameParticipant;

import java.util.List;

/**
 * Created by werka on 10.01.18.
 */
public class GameRunner {
    private NGame nGame;
    private Rules rules;
    private Integer turn;
    private Thread timerThread;
    public GameRunner(Rules rules, NGame nGame) {
        this.nGame=nGame;
        this.rules=rules;
        this.turn=rules.initializeRound(nGame.getPlayers()); //Losuje pierwszego gracza i wynik do zdobycia
        for (GameParticipant p:nGame.getPlayers()) {
            rules.initializeDices((Player) p);//inicjalizuje kosci dla graczy
        }
        notifyAllGameParticipants();
        runRound();
    }

    private void notifyAllGameParticipants() {
        GameDTO gameDTO = nGame.getGameDTO();
        gameDTO.setScoreToWin(rules.getAim());
        for (GameParticipant p:nGame.getPlayers()) {
            p.notifyGameStateChange(gameDTO);
        }
        for (GameParticipant p:nGame.getObservers()) {
            p.notifyGameStateChange(gameDTO);
        }
    }

    private synchronized void runRound(){
        for (GameParticipant p:nGame.getPlayers()) {
            checkIfWon((Player) p);
        }
        while(true){
            Player player= (Player) nGame.getPlayers().get(turn);
            timerThread = new Thread(new Timer(this, 30));
            timerThread.start();
            notifyAllGameParticipants();
            if(rules.countPoints(player)){ //roundWon by player
                player.addRoundWon();
                if(player.getScore()==5){
                    notifyAllGameParticipants();
                    endGame();
                }

                this.turn=rules.initializeRound(nGame.getPlayers());
                for (GameParticipant p:nGame.getPlayers()) {
                    rules.initializeDices((Player) p);
                }
            }
            //aktualny gracz nie jego tura

            turn = turn + 1 % nGame.getPlayers().size();
            //aktualny gracz jego tura
        }

    }

    public void kickPlayer(){
        //players.remove(turn);
        GameDTO gameDTO = nGame.getGameDTO();
        List<UserInGame> tmp= gameDTO.getPlayers();
        tmp.remove(turn);
        gameDTO.setPlayers(tmp);
        nGame.setGameDTO(gameDTO);
        notifyAllGameParticipants();

    }

    private void checkIfWon(Player p){
        if (rules.countPoints(p)) { //roundWon by player
            p.addRoundWon();
            if (p.getScore() == 5) {
                notifyAllGameParticipants();
                endGame();
            }
        }
    }
    public synchronized void performMove(MoveDTO moveDTO) {
        for (GameParticipant p :nGame.getPlayers()){
        //    if(moveDTO.getPlayerId().equals(p.getId())&& nGame.getPlayers().get(turn) ==(p));
            rules.drawDices((Player) p,moveDTO);
        }
        timerThread.interrupt();
    }
    private void endGame(){
        notifyAllGameParticipants();
    }

}
