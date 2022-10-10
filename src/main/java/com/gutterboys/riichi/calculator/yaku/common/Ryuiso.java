package com.gutterboys.riichi.calculator.yaku.common;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Ryuiso implements CommonYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {

        if (possibleHand.getTiles().stream().filter(tile -> !RiichiCalculatorConstants.GREEN.contains(tile))
                .count() == 0) {
            possibleHand.setHan(possibleHand.getHan() + 13);
            possibleHand.getQualifiedYaku().add("Ryuiso (All Green)");
        }

    }

}
