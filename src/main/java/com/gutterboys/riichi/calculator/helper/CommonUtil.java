package com.gutterboys.riichi.calculator.helper;

import java.util.List;

public class CommonUtil {

    public static void removeAndAddFromList(List<Integer> hand, Integer tile, int numberOfTiles) {
        for (int i = 0; i < numberOfTiles; i++) {
            hand.remove(hand.indexOf(tile));
            hand.add(0, -1);
        }
    }

}
