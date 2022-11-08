package com.gutterboys.riichi.calculator.yaku.common;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Sankantsu implements CommonYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        if (gameContext.getKanCount() < 3) {
            return;
        }
        possibleHand.setHan(possibleHand.getHan() + 2);
        possibleHand.getQualifiedYaku().add("Sankantsu (Three Kans)");
    }

}