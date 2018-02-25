package agh.to2.dicemaster.bot.factory;


import agh.to2.dicemaster.bot.BotType;
import agh.to2.dicemaster.bot.IllegalBotTypeException;
import agh.to2.dicemaster.bot.model.Bot;

public abstract class BotFactory {

    public abstract Bot createBot(BotType type) throws IllegalBotTypeException;

}
