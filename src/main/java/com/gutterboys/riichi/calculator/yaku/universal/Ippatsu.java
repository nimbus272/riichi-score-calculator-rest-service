package com.gutterboys.riichi.calculator.yaku.universal;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;

@Component
public class Ippatsu implements UniversalYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, CalculatorTracker tracker, PossibleHand possibleHand) {
        if (request.isIppatsu() && !request.isOpened() && tracker.getKanCount() == 0) {
            possibleHand.setHan(possibleHand.getHan() + 1);
            possibleHand.getQualifiedYaku().add("Ippatsu (One-shot)");
        }
    }
}
