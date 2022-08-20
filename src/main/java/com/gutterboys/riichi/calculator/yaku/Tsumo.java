package com.gutterboys.riichi.calculator.yaku;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Tsumo implements CommonYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        if (gameContext.isTsumo() && !gameContext.isOpened()) {
            possibleHand.setHan(possibleHand.getHan() + 1);
            possibleHand.getQualifiedYaku().add("Tsumo");
        }

    }

}
