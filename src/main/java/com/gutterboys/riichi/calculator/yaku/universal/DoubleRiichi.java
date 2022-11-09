package com.gutterboys.riichi.calculator.yaku.universal;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class DoubleRiichi implements UniversalYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        if (gameContext.isDoubleRiichi() && !gameContext.isOpened()) {
            possibleHand.setHan(possibleHand.getHan() + 2);
            possibleHand.getQualifiedYaku().add("Double Riichi");
        }
    }
}
