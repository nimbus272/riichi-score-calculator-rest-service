package com.gutterboys.riichi.calculator.yaku;

import java.util.HashSet;
import java.util.Set;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

public class KokushiMusou implements SpecialYaku {
    public void execute(GameContext gameContext, ScoreResponse response) {
        boolean isKokushi = true;
        for (String tile : gameContext.getHand()) {

            if (RiichiCalculatorConstants.SIMPLES.contains(tile)) {
                isKokushi = false;
                break;
            }
        }

        if (isKokushi) {
            Set<String> hand = new HashSet<String>(gameContext.getHand());
            if (hand.size() == 13) {
                if (gameContext.getHand().stream().filter(tile -> gameContext.getWinningTile().equals(tile))
                        .count() == 2) {
                    response.setDoubleYakuman(true);
                }
                response.getQualifiedYaku().add("Kokushi Musou (Thirteen Orphans)");
                response.setHan(13);
            }
        }
    }
}
