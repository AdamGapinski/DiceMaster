package agh.to2.dicemaster.bot;

public class IllegalHandException extends Exception{
    @Override
    public String getMessage() {
            return "Improperly checked or provided hand methods in bot module.";
        }
}
