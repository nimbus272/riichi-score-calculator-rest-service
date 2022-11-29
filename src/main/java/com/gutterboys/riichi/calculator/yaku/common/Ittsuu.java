package com.gutterboys.riichi.calculator.yaku.common;


import java.util.List;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Ittsuu implements CommonYaku {
    @Override
    public void execute(RiichiCalculatorRequest request, PossibleHand possibleHand) {
        List<Integer> tiles = possibleHand.getTiles();
        if (tiles.containsAll(RiichiCalculatorConstants.MAN) ||
                tiles.containsAll(RiichiCalculatorConstants.SOU) ||
                tiles.containsAll(RiichiCalculatorConstants.PIN)) {
            if (request.isOpened()) {
                possibleHand.setHan(possibleHand.getHan() + 1);
            } else {
                possibleHand.setHan(possibleHand.getHan() + 2);
            }
            possibleHand.getQualifiedYaku().add("Ittsuu (Full Straight)");
            return;
        }
    }
}
