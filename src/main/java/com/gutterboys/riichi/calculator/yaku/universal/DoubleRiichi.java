package com.gutterboys.riichi.calculator.yaku.universal;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;

@Component
public class DoubleRiichi implements UniversalYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, CalculatorTracker tracker, PossibleHand possibleHand) {
        if (request.isDoubleRiichi() && !request.isOpened()) {
            possibleHand.setHan(possibleHand.getHan() + 2);
            possibleHand.getQualifiedYaku().add("Double Riichi");
        }
    }
}
