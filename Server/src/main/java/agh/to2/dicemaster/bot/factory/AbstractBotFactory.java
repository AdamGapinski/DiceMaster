package agh.to2.dicemaster.bot.factory;


import agh.to2.dicemaster.bot.BotType;
import agh.to2.dicemaster.bot.IllegalBotTypeException;
import agh.to2.dicemaster.bot.model.Bot;

public abstract class AbstractBotFactory {

  /*  abstract void adjustVeryEasyDifficulty();
    abstract void adjustVeryHardDifficulty();*/

    public abstract Bot createBot(BotType type) throws IllegalBotTypeException;

    /*public void determineDifficulty(BotType type) throws IllegalBotTypeException {
        switch (type) {
            case EASY:
                adjustVeryEasyDifficulty();
            case DIFFICULT:
                adjustVeryHardDifficulty();
            default:
                throw new IllegalBotTypeException();
        }

    }*/
}
