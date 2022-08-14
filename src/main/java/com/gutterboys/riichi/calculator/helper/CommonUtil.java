package com.gutterboys.riichi.calculator.helper;

import java.util.List;

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

}
