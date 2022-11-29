package com.gutterboys.riichi.calculator.yaku.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;

@Component
public class Daisangen implements CommonYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, CalculatorTracker tracker, PossibleHand hand) {

        List<List<Integer>> dragonMelds = hand.getMelds().stream().filter((meld) -> {
            if (meld.size() < 3 || CommonUtil.isChi(meld) || !RiichiCalculatorConstants.DRAGONS.contains(meld.get(0))) {
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toList());

        if (dragonMelds.size() == 3) {
            hand.setHan(hand.getHan() + 13);
            hand.getQualifiedYaku().add("Daisangen (Big Three Dragons)");
        }

    }

}
