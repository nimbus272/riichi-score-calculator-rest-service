package com.gutterboys.riichi.calculator.yaku.common;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class ToitoiAndSuuanko implements CommonYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        if (possibleHand.getMelds().stream().filter(meld -> CommonUtil.isChi(meld)).count() > 0) {
            return;
        }

        if (gameContext.isTsumo() && !gameContext.isOpened()) {
            possibleHand.setHan(possibleHand.getHan() + 13);
            possibleHand.getQualifiedYaku().add("Suuankou (All concealed triplets)");
            return;
        }

        possibleHand.setHan(possibleHand.getHan() + 2);
        possibleHand.getQualifiedYaku().add("Toitoi (All Triplets or Kans)");

    }

}
