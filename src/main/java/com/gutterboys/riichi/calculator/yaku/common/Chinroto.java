package com.gutterboys.riichi.calculator.yaku.common;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Chinroto implements CommonYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {

        if (possibleHand.getTiles().stream().filter(tile -> RiichiCalculatorConstants.TERMINALS.contains(tile))
                .count() == possibleHand.getTiles().size()) {
            possibleHand.setHan(possibleHand.getHan() + 13);
            possibleHand.getQualifiedYaku().add("Chinroto (All Terminals)");
        }

    }

}
