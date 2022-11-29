package com.gutterboys.riichi.calculator.yaku.first;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorResponse;

@Component
public class KokushiMusou implements FirstYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, CalculatorTracker tracker, RiichiCalculatorResponse response) {
        if (request.isOpened()) {
            return;
        }

        boolean isKokushi = true;
        for (Integer tile : tracker.getTiles()) {

            if (RiichiCalculatorConstants.SIMPLES.contains(tile)) {
                isKokushi = false;
                break;
            }
        }

        if (isKokushi) {
            Set<Integer> hand = new HashSet<Integer>(tracker.getTiles());
            if (hand.size() == 13) {
                PossibleHand possibleHand = new PossibleHand();

                if (tracker.getTiles().stream().filter(tile -> request.getWinningTile() == tile)
                        .count() == 2) {
                    possibleHand.setDoubleYakuman(true);
                }
                possibleHand.getQualifiedYaku().add("Kokushi Musou (Thirteen Orphans)");
                possibleHand.setHan(possibleHand.getHan() + 13);
                possibleHand.getTiles().addAll(tracker.getTiles());
                response.getPossibleHands().add(possibleHand);
            }
        }
    }
}
