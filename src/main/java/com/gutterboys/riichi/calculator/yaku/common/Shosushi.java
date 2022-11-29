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
public class Shosushi implements CommonYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, CalculatorTracker tracker, PossibleHand possibleHand) {

        List<List<Integer>> windMelds = possibleHand.getMelds().stream().filter((meld) -> {
            if (CommonUtil.isChi(meld) || !RiichiCalculatorConstants.WINDS.contains(meld.get(0))) {
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toList());

        if (windMelds.size() == 4) {
            possibleHand.setHan(possibleHand.getHan() + 13);
            possibleHand.getQualifiedYaku().add("Shosushi (Four Little Dragons) / Daisushi (Four Big Dragons)");
        }

    }

}
