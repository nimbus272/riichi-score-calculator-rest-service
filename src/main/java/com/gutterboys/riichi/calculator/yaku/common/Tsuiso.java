package com.gutterboys.riichi.calculator.yaku.common;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;

@Component
public class Tsuiso implements CommonYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, CalculatorTracker tracker, PossibleHand possibleHand) {

        if (possibleHand.getTiles().stream().filter(tile -> RiichiCalculatorConstants.HONORS.contains(tile))
                .count() == possibleHand.getTiles().size()) {
            possibleHand.setHan(possibleHand.getHan() + 13);
            possibleHand.getQualifiedYaku().add("Tsuiso (All Honors)");
        }

    }

}
