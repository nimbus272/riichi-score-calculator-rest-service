package com.gutterboys.riichi.calculator.yaku.common;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Suukantsu implements CommonYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {

        if (possibleHand.getMelds().stream().filter(meld -> meld.size() == 4).count() == 4L) {
            possibleHand.setHan(possibleHand.getHan() + 13);
            possibleHand.getQualifiedYaku().add("Suukantsu (Four Kans)");
        }

    }

}
