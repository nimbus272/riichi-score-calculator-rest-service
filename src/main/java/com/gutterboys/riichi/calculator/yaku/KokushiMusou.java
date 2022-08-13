package com.gutterboys.riichi.calculator.yaku;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

@Component
public class KokushiMusou implements SpecialYaku {

    @Override
    public void execute(GameContext gameContext, ScoreResponse response) {
        if (gameContext.isOpened()) {
            return;
        }

        boolean isKokushi = true;
        for (Integer tile : gameContext.getHand()) {

            if (RiichiCalculatorConstants.SIMPLES.contains(tile)) {
                isKokushi = false;
                break;
            }
        }

        if (isKokushi) {
            Set<Integer> hand = new HashSet<Integer>(gameContext.getHand());
            if (hand.size() == 13) {
                if (gameContext.getHand().stream().filter(tile -> gameContext.getWinningTile() == tile)
                        .count() == 2) {
                    response.setDoubleYakuman(true);
                }
                response.getQualifiedYaku().add("Kokushi Musou (Thirteen Orphans)");
                response.setHan(response.getHan() + 13);
            }
        }
    }
}
