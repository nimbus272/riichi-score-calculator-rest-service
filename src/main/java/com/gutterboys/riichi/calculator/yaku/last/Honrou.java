package com.gutterboys.riichi.calculator.yaku.last;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Honrou implements LastYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) throws RiichiCalculatorException {
        if (possibleHand.getQualifiedYaku().contains("Toitoi (All Triplets or Kans)") || possibleHand.getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)")) {
            if (possibleHand.getTiles().stream().filter(tile -> !RiichiCalculatorConstants.TERMINAL_AND_HONOR.contains(tile)).count() == 0) {
                possibleHand.setHan(possibleHand.getHan() + 2);
                possibleHand.getQualifiedYaku().add("Honrou (All Terminals and Honors)");
            }
        }
    }

}
    

