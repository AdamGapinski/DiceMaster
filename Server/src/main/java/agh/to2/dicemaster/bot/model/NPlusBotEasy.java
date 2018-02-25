package agh.to2.dicemaster.bot.model;


import agh.to2.dicemaster.bot.DiceInputDTO;
import agh.to2.dicemaster.bot.DiceOutputDTO;

import java.util.*;

public class NPlusBotEasy extends Bot{
    @Override
    DiceOutputDTO getDicesToThrow(DiceInputDTO input){

        int dicesAvailabletoThrow = input.getMyInput().size();

        Random r = new Random();
        int dicesToPutAside = r.nextInt(dicesAvailabletoThrow);

        HashMap<Integer, Integer> dicesToThrow = input.getDicesRerolled();

        int to_Remove;
        for (int i=0;i<dicesToPutAside;i++){
            to_Remove = r.nextInt(dicesToThrow.size());
            Iterator it = dicesToThrow.entrySet().iterator();
            Map.Entry pair=(Map.Entry) it.next();
            for (int j = 0; j <  to_Remove; j++) {
                pair = (Map.Entry) it.next();
            }
            int numToTarget = result.getScoreLeft();
            int eyes = (Integer) pair.getValue();
            if ( (dicesToThrow.size()-1)*6 >= numToTarget-eyes &&
                    (dicesToThrow.size()-1)*1 <= numToTarget-eyes ) {   //*1 to show it clearly
                result.subtractNumToFinishGame(eyes);
                it.remove();
            }
            it.remove();
        }

        result.setDicesToThrow(dicesToThrow);

        return result;
    }

}

//last version
//    DiceOutputDTO getDicesToThrow(DiceInputDTO input){
//
//        int dicesAvailabletoThrow = input.getMyInput().size();
//
//        Random r = new Random();
//        int dicesToPutAside = r.nextInt(dicesAvailabletoThrow);
//
//        HashMap<Integer, Integer> dicesToThrow = input.getDicesRerolled();
//
//        int to_Remove;
//        for (int i=0;i<dicesToPutAside;i++){
//            to_Remove = r.nextInt(dicesToThrow.size());
//            if ( (dicesToThrow.size()-1)*6 >= result.getScoreLeft()-dicesToThrow.get(to_Remove) &&
//                    (dicesToThrow.size()-1)*1 <= result.getScoreLeft()-dicesToThrow.get(to_Remove) ) {   //*1 to show it clearly
//                result.subtractNumToFinishGame(dicesToThrow.get(to_Remove));
//                dicesToThrow.remove(to_Remove);
//            }
//        }
//
//        result.setDicesToThrow(dicesToThrow);
//
//        return result;
//    }
//
//}

