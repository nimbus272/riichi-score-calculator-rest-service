package com.gutterboys.riichi.calculator.yaku;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class HouteiRaoyui implements CommonYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        if (gameContext.isLastDiscard() && !gameContext.isTsumo()) {
            possibleHand.setHan(possibleHand.getHan() + 1);
            possibleHand.getQualifiedYaku().add("Houtei Raoyui (Last Discarded Tile)");
        }

    }

}
