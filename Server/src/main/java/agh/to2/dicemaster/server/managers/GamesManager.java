package agh.to2.dicemaster.server.managers;

import agh.to2.dicemaster.common.api.GameConfigDTO;
import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.common.api.UserType;
import agh.to2.dicemaster.game.factory.GameFactory;
import agh.to2.dicemaster.server.User;
import agh.to2.dicemaster.server.api.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GamesManager {

    private final GameFactory gameFactory;
    private Map<Integer, Game> games = new HashMap<>();

    @Autowired
    public GamesManager(GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    public synchronized Game createGame(GameConfigDTO gameConfigDTO, UserType userType, User user) {
        Game game = this.gameFactory.createGame(gameConfigDTO);
        do{
            game.setId(UUID.randomUUID().hashCode());
        } while (games.containsKey(game.getId()));
        games.put(game.getId(), game);
        this.addUserToGame(userType, user, game.getId());
        return game;
    }

    public synchronized Optional<Game> getGameById(int gameId) {
        return Optional.ofNullable(games.get(gameId));
    }

    public synchronized Collection<GameDTO> getAllAsGameDTO() {
        return games.values().stream().map(Game::getGameDTO).collect(Collectors.toList());
    }

    public synchronized Game removeGame(int gameId) {
        return games.remove(gameId);
    }

    public synchronized void addUserToGame(UserType userType, User user, int gameId) {
        Optional<Game> game = this.getGameById(gameId);
        if(game.isPresent()){
            if(userType == UserType.PLAYER){
                game.get().addPlayer(user);
            } else if(userType == UserType.OBSERVER){
                game.get().addObserver(user);
            }
        }
    }

    @Scheduled(fixedRate = 20000) //every 20 sec
    public synchronized void removeIdleGames(){
        this.games = games.entrySet()
                .stream()
                .filter(entry ->
                        Stream.concat(entry.getValue().getPlayers().stream(), entry.getValue().getObservers().stream())
                        .anyMatch(player -> player instanceof User)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
