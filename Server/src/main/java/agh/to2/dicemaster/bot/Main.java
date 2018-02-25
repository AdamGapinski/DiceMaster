package agh.to2.dicemaster.bot;


import agh.to2.dicemaster.bot.factory.BotFactory;
import agh.to2.dicemaster.bot.factory.NMultiplyBotFactory;
import agh.to2.dicemaster.bot.model.Bot;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        System.out.println("Hello World!");
        BotFactory factory = new NMultiplyBotFactory();

        Bot bot = null;
//        Main main = new Main();
//        System.out.println(main.getS());

//        try {
//            bot = factory.createBot(BotType.EASY);
//        } catch (IllegalBotTypeException e) {
//            e.printStackTrace();
//        }

       // bot.makeMove(gameDTO);

    }
/*
    public String getS(){
        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("file.txt").getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }*/
}