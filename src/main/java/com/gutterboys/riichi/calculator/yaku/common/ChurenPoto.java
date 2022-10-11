package com.gutterboys.riichi.calculator.yaku.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class ChurenPoto implements CommonYaku {
    private static final List<Integer> GATES_MAN = Arrays.asList(0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8);
    private static final List<Integer> GATES_PIN = Arrays.asList(9, 9, 9, 10, 11, 12, 13, 14, 15, 16, 17, 17, 17);
    private static final List<Integer> GATES_SOU = Arrays.asList(18, 18, 18, 19, 20, 21, 22, 23, 24, 25, 26, 26, 26);

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        if (possibleHand.getTiles().containsAll(GATES_MAN) || possibleHand.getTiles().containsAll(GATES_PIN)
                || possibleHand.getTiles().containsAll(GATES_SOU)) {
            List<Integer> tiles = new ArrayList<Integer>(possibleHand.getTiles());
            tiles.remove(possibleHand.getTiles().indexOf(gameContext.getWinningTile()));
            if (tiles.containsAll(GATES_MAN) || tiles.containsAll(GATES_PIN) || tiles.containsAll(GATES_SOU)) {
                possibleHand.getQualifiedYaku().add("Churen Poto (Nine Gates)");
                possibleHand.setHan(possibleHand.getHan() + 26);
                return;
            }
            possibleHand.getQualifiedYaku().add("Churen Poto (Nine Gates)");
            possibleHand.setHan(possibleHand.getHan() + 13);
        }

    }
}
