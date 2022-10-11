package com.gutterboys.riichi.calculator.yaku.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class ChurenPoto implements CommonYaku {
    private static final List<Integer> GATES_MAN = Arrays.asList(0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8);
    private static final List<Integer> GATES_PIN = Arrays.asList(9, 9, 9, 10, 11, 12, 13, 14, 15, 16, 17, 17, 17);
    private static final List<Integer> GATES_SOU = Arrays.asList(18, 18, 18, 19, 20, 21, 22, 23, 24, 25, 26, 26, 26);

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        String suit = CommonUtil.determineFlushSuit(possibleHand.getTiles());

        if (suit.equals("n/a")) {
            return;
        }

        List<Integer> tiles = new ArrayList<Integer>();
        tiles.addAll(possibleHand.getTiles());

        tiles.remove(possibleHand.getTiles().indexOf(gameContext.getWinningTile()));

        if (tiles.equals(GATES_MAN) || tiles.equals(GATES_PIN) || tiles.equals(GATES_SOU)) {
            possibleHand.getQualifiedYaku().add("Churen Poto (Nine Gates)");
            possibleHand.setHan(possibleHand.getHan() + 26);
            return;
        }

        tiles.add(gameContext.getWinningTile());
        tiles.sort((a, b) -> a - b);

        determineExtraTile(tiles, suit);

        if (tiles.equals(GATES_MAN) || tiles.equals(GATES_PIN) || tiles.equals(GATES_SOU)) {
            possibleHand.getQualifiedYaku().add("Churen Poto (Nine Gates)");
            possibleHand.setHan(possibleHand.getHan() + 13);
        }

    }

    private void determineExtraTile(List<Integer> tiles, String suit) {
        for (int i = 0; i < tiles.size(); i++) {
            int tile = tiles.get(i);
            switch (suit) {
                case "man":
                    if (tile != GATES_MAN.get(i)) {
                        tiles.remove(tiles.indexOf(tile));
                        return;
                    }
                    break;
                case "pin":
                    if (tile != GATES_PIN.get(i)) {
                        tiles.remove(tiles.indexOf(tile));
                        return;
                    }
                    break;
                case "sou":
                    if (tile != GATES_SOU.get(i)) {
                        tiles.remove(tiles.indexOf(tile));
                        return;
                    }
                    break;
            }
        }
        return;

    }
}
