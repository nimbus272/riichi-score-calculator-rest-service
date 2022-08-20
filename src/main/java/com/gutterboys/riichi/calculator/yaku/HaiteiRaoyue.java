package com.gutterboys.riichi.calculator.yaku;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class HaiteiRaoyue implements CommonYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        if (gameContext.isTsumo() && gameContext.isLastTileDraw()) {
            possibleHand.setHan(possibleHand.getHan() + 1);
            possibleHand.getQualifiedYaku().add("Haitei Raoyue (Last Tile Drawn From Wall)");
        }

    }

}
