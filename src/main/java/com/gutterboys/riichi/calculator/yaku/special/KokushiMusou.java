package com.gutterboys.riichi.calculator.yaku.special;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

@Component
public class KokushiMusou implements SpecialYaku {

    @Override
    public void execute(GameContext gameContext, ScoreResponse response) {
        if (gameContext.isOpened()) {
            return;
        }

        boolean isKokushi = true;
        for (Integer tile : gameContext.getTiles()) {

            if (RiichiCalculatorConstants.SIMPLES.contains(tile)) {
                isKokushi = false;
                break;
            }
        }

        if (isKokushi) {
            Set<Integer> hand = new HashSet<Integer>(gameContext.getTiles());
            if (hand.size() == 13) {
                PossibleHand possibleHand = new PossibleHand();

                if (gameContext.getTiles().stream().filter(tile -> gameContext.getWinningTile() == tile)
                        .count() == 2) {
                    possibleHand.setDoubleYakuman(true);
                }
                possibleHand.getQualifiedYaku().add("Kokushi Musou (Thirteen Orphans)");
                possibleHand.setHan(possibleHand.getHan() + 13);
                possibleHand.getTiles().addAll(gameContext.getTiles());
                response.getPossibleHands().add(possibleHand);
            }
        }
    }
}
