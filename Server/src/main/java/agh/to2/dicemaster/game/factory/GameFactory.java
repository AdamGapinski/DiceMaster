package agh.to2.dicemaster.game.factory;

import agh.to2.dicemaster.bot.BotType;
import agh.to2.dicemaster.bot.IllegalBotTypeException;
import agh.to2.dicemaster.bot.factory.BotFactory;
import agh.to2.dicemaster.bot.factory.NMultiplyBotFactory;
import agh.to2.dicemaster.bot.factory.NPlusBotFactory;
import agh.to2.dicemaster.bot.factory.PokerBotFactory;
import agh.to2.dicemaster.common.api.GameConfigDTO;
import agh.to2.dicemaster.common.api.GameType;
import agh.to2.dicemaster.game.ngames.NGame;
import agh.to2.dicemaster.game.poker.PokerGame;
import agh.to2.dicemaster.server.api.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class GameFactory {

    private int id = 0;
    @Autowired
    PokerBotFactory pokerBotFactory;
    @Autowired
    NMultiplyBotFactory nMultiplyBotFactory;
    @Autowired
    NPlusBotFactory nPlusBotFactory;

    public Game createGame(GameConfigDTO gameConfigDTO) {
        this.id += 1;
        if(gameConfigDTO.getGameType().equals(GameType.POKER)){
            final PokerGame pokerGame = new PokerGame(this.id, gameConfigDTO);
            addPokerBots(gameConfigDTO, pokerGame);
            return pokerGame;
        } else {
            final NGame nGame = new NGame(this.id, gameConfigDTO);
            addNGameBots(gameConfigDTO, nGame);
            return nGame;
        }
    }

    private void addPokerBots(GameConfigDTO gameConfigDTO, PokerGame pokerGame) {
        for(int i = 0 ; i < gameConfigDTO.getEasyBotsCount(); i++){
            try {
                pokerGame.addPlayer(pokerBotFactory.createBot(BotType.EASY));
            } catch (IllegalBotTypeException e) {
                e.printStackTrace();
            }
        }

        for(int i  = 0 ; i < gameConfigDTO.getHardBotsCount() ; i++){
            try {
                pokerGame.addPlayer(pokerBotFactory.createBot(BotType.DIFFICULT));
            } catch (IllegalBotTypeException e) {
                e.printStackTrace();
            }
        }
    }

    private void addNGameBots(GameConfigDTO gameConfigDTO, NGame nGame){
        try {
            addNBots(gameConfigDTO.getEasyBotsCount(), gameConfigDTO.getGameType(), BotType.EASY, nGame);
            addNBots(gameConfigDTO.getHardBotsCount(), gameConfigDTO.getGameType(), BotType.DIFFICULT, nGame);
        } catch (IllegalBotTypeException e) {
            e.printStackTrace();
        }
    }

    private void addNBots(int botCount, GameType gameType, BotType botType, NGame nGame) throws IllegalBotTypeException {
        for (int i=0; i< botCount;i++) {
            if(gameType == GameType.NPLUS){
                nGame.addPlayer(nPlusBotFactory.createBot(botType));
            } else if(gameType == GameType.NTIMES){
                nGame.addPlayer(nMultiplyBotFactory.createBot(botType));
            }
        }
    }
}
