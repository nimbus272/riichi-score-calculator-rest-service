package com.gutterboys.riichi.calculator.yaku.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;

@Component
public class Shousangen implements CommonYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, CalculatorTracker tracker, PossibleHand possibleHand) {
        List<List<Integer>> honorMelds = possibleHand.getMelds().stream().filter(meld -> {
            if (!CommonUtil.isChi(meld) && meld.get(0) > 26) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        int ponOrKanCount = 0;
        int pairCount = 0;
        for (List<Integer> meld : honorMelds) {
            if (meld.size() > 2 && meld.get(0) > 26 && meld.get(0) < 30) {
                ponOrKanCount++;
            } else if (meld.size() == 2 && meld.get(0) > 26 && meld.get(0) < 30) {
                pairCount++;
            }
        }

        if (ponOrKanCount == 2 && pairCount == 1) {
            possibleHand.setHan(possibleHand.getHan() + 2);
            possibleHand.getQualifiedYaku()
                    .add("Shousangen (2 pons or kans of dragons and a pair of the third dragon)");
        }

    }

}
