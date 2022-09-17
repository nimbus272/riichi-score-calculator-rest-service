package com.gutterboys.riichi.calculator.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.PossibleMelds;

public class CommonUtil {

    public static boolean isChi(List<Integer> meld) {
        int tile = meld.get(0);
        return (meld.containsAll((Arrays.asList(tile, tile + 1, tile + 2)))
                || meld.containsAll(Arrays.asList(tile - 1, tile, tile + 1))
                || meld.containsAll(Arrays.asList(tile - 2, tile - 1, tile)));

    }

    public static boolean isTerminalChi(List<Integer> meld) {
        int tile = meld.get(0);
        return (meld.containsAll((Arrays.asList(tile, tile + 1, tile + 2)))
                || meld.containsAll(Arrays.asList(tile - 1, tile, tile + 1))
                || meld.containsAll(Arrays.asList(tile - 2, tile - 1, tile)))
                && (RiichiCalculatorConstants.TERMINALS.contains(tile)
                        || RiichiCalculatorConstants.TERMINALS.contains(tile + 1)
                        || RiichiCalculatorConstants.TERMINALS.contains(tile + 2));
    }

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

    public static void checkAndRemoveDuplicatePossibleHands(List<PossibleHand> possibleHands) {
        Gson gson = new Gson();
        for (int i = 0; i < possibleHands.size(); i++) {
            PossibleHand possibleHand1 = possibleHands.get(i);
            for (int j = i + 1; j < possibleHands.size(); j++) {
                PossibleHand possibleHand2 = possibleHands.get(j);
                String hand1 = gson.toJson(possibleHand1.getMelds());
                String hand2 = gson.toJson(possibleHand2.getMelds());
                if (hand1.equals(hand2)) {
                    possibleHands.remove(j);
                    j--;
                }
            }

        }
    }

    public static String getSuit(List<Integer> meld) {
        if (RiichiCalculatorConstants.MAN.contains(meld.get(0))) {
            return "MAN";
        } else if (RiichiCalculatorConstants.PIN.contains(meld.get(0))) {
            return "PIN";
        } else if (RiichiCalculatorConstants.SOU.contains(meld.get(0))) {
            return "SOU";
        } else if (RiichiCalculatorConstants.HONORS.contains(meld.get(0))) {
            return "HONOR";
        }
        return null;
    }

    public static int getIndexFromTile(int tile) {
        if (tile < 9) {
            return tile;
        } else if (tile > 8 && tile < 18) {
            return tile - 9;
        } else if (tile > 17 && tile < 27) {
            return tile - 18;
        } else {
            return -1;
        }
    }
}
