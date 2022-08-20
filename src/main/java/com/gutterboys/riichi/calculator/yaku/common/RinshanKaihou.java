package com.gutterboys.riichi.calculator.yaku.common;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class RinshanKaihou implements CommonYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        if (gameContext.isDeadWallDraw() && gameContext.isTsumo() && gameContext.getKanCount() > 0) {
            possibleHand.setHan(possibleHand.getHan() + 1);
            possibleHand.getQualifiedYaku().add("Rinshan Kaihou (Dead Wall Draw)");
        }

    }

}
