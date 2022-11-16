package com.gutterboys.riichi.calculator.yaku.common;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Chinitsu implements CommonYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        boolean man = true;
        boolean pin = true;
        boolean sou = true;
        for (int i = 0; i < possibleHand.getTiles().size(); i++) {
            int tile = possibleHand.getTiles().get(i);
            if (tile > 26) {
                return;
            } else if (RiichiCalculatorConstants.MAN.contains(tile)) {
                pin = false;
                sou = false;
            } else if (RiichiCalculatorConstants.PIN.contains(tile)) {
                man = false;
                sou = false;
            } else if (RiichiCalculatorConstants.SOU.contains(tile)) {
                man = false;
                pin = false;
            }
        }
        if (man || pin || sou)

        {
            possibleHand.setHan(gameContext.isOpened() ? possibleHand.getHan() + 5 : possibleHand.getHan() + 6);
            possibleHand.getQualifiedYaku().add("Chinitsu (Full Flush)");
        }

    }

}
