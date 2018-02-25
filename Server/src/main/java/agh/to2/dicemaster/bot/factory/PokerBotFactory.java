package agh.to2.dicemaster.bot.factory;


import agh.to2.dicemaster.bot.BotType;
import agh.to2.dicemaster.bot.IllegalBotTypeException;
import agh.to2.dicemaster.bot.model.Bot;
import agh.to2.dicemaster.bot.model.PokerBotDifficult;
import agh.to2.dicemaster.bot.model.PokerBotEasy;
import org.springframework.stereotype.Service;

/**
 * Created by kamkalet on 03.01.2018.
 */
@Service
public class PokerBotFactory extends BotFactory {

    @Override
    public Bot createBot(BotType type) throws IllegalBotTypeException {
        switch (type) {
            case EASY:
                return new PokerBotEasy();
            case DIFFICULT:
                return new PokerBotDifficult();
        }
        throw new IllegalBotTypeException();
    }

}
