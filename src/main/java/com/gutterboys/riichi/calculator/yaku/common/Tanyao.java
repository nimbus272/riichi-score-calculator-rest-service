package com.gutterboys.riichi.calculator.yaku.common;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Tanyao implements CommonYaku {
    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        if (Collections.disjoint(possibleHand.getTiles(), RiichiCalculatorConstants.TERMINAL_AND_HONOR)) {
            possibleHand.setHan(possibleHand.getHan() + 1);
            possibleHand.getQualifiedYaku().add("Tanyao (No Terminal or Honor Tiles)");

        }

    }

}
