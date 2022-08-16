package com.gutterboys.riichi.calculator.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleMelds;

public class CommonUtil {

    public static void removeAndAddPonFromList(List<Integer> hand, int tile, int numberOfTiles) {
        for (int i = 0; i < numberOfTiles; i++) {
            hand.remove(hand.indexOf(tile));
            hand.add(0, -1);
        }
    }

    public static void removeAndAddChiFromList(List<Integer> hand, int startTile) {
        for (int i = 0; i < 3; i++) {
            hand.remove(hand.indexOf(startTile + i));
            hand.add(0, -1);
        }
    }

    public static void checkMeldTypesAndRemoveDupes(PossibleMelds possibleMelds, List<Integer> hand) {
        if (possibleMelds.getChis().size() > 0) {
            removeDupes(possibleMelds, hand);
        }
        if (possibleMelds.getPons().size() > 0) {
            List<List<Integer>> newPons = possibleMelds.getPons().stream().distinct().collect(Collectors.toList());
            possibleMelds.getPons().clear();
            possibleMelds.getPons().addAll(newPons);

        }
        if (possibleMelds.getKans().size() > 0) {
            List<List<Integer>> newKans = possibleMelds.getKans().stream().distinct().collect(Collectors.toList());
            possibleMelds.getKans().clear();
            possibleMelds.getKans().addAll(newKans);
        }
        if (possibleMelds.getPairs().size() > 0) {
            List<List<Integer>> newPairs = possibleMelds.getPairs().stream().distinct().collect(Collectors.toList());
            possibleMelds.getPairs().clear();
            possibleMelds.getPairs().addAll(newPairs);
        }
    }

    private static void removeDupes(PossibleMelds possibleMelds, List<Integer> hand) {
        List<List<Integer>> meldList = possibleMelds.getChis().stream().distinct().collect(Collectors.toList());
        List<List<Integer>> newMeldList = new ArrayList<List<Integer>>();
        long maxInstances = 0;
        for (int i = 0; i < meldList.size(); i++) {
            int tile1 = meldList.get(i).get(0);
            int tile2 = meldList.get(i).get(1);
            int tile3 = meldList.get(i).get(2);
            maxInstances = hand.stream().filter(x -> x == tile1).count();
            maxInstances = Math.min(maxInstances, hand.stream().filter(x -> x == tile2).count());
            maxInstances = Math.min(maxInstances, hand.stream().filter(x -> x == tile3).count());
            for (int j = 0; j < maxInstances; j++) {
                newMeldList.add(meldList.get(i));
            }
        }
        possibleMelds.getChis().clear();
        possibleMelds.getChis().addAll(newMeldList);
    }

    public static void addTempMeldsToGameContext(GameContext tempContext, GameContext gameContext) {
        for (int i = 0; i < tempContext.getMelds().size(); i++) {
            gameContext.getMelds().add(tempContext.getMelds().get(i));
            for (int j = 0; j < tempContext.getMelds().get(i).size(); j++) {
                gameContext.getTiles().remove(tempContext.getMelds().get(i).get(j));
                gameContext.getTiles().add(0, -1);
            }
        }
    }
}
