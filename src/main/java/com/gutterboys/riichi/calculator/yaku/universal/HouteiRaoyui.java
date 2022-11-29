package com.gutterboys.riichi.calculator.yaku.universal;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class HouteiRaoyui implements UniversalYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, PossibleHand possibleHand) {
        if (request.isLastDiscard() && !request.isTsumo()) {
            possibleHand.setHan(possibleHand.getHan() + 1);
            possibleHand.getQualifiedYaku().add("Houtei Raoyui (Last Discarded Tile)");
        }

    }

}
