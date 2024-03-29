package com.gutterboys.riichi.calculator.yaku.common;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Sankantsu implements CommonYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, CalculatorTracker tracker, PossibleHand possibleHand) {
        if (tracker.getKanCount() < 3) {
            return;
        }
        possibleHand.setHan(possibleHand.getHan() + 2);
        possibleHand.getQualifiedYaku().add("Sankantsu (Three Kans)");
    }

}
