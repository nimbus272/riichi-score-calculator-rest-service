package com.gutterboys.riichi.calculator.yaku.common;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;

@Component
public class SanshokuDouku implements CommonYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, CalculatorTracker tracker, PossibleHand possibleHand) {
        for (int i = 0; i < possibleHand.getMelds().size(); i++) {
            List<Integer> meld = possibleHand.getMelds().get(i);
            if (!CommonUtil.isChi(meld) && meld.size() > 2) {
                int index1 = CommonUtil.getIndexFromTile(meld.get(0));
                if (possibleHand.getMelds().stream().filter(x -> {
                    if (!CommonUtil.isChi(x)) {
                        int index2 = CommonUtil.getIndexFromTile(x.get(0));
                        return index1 == index2;
                    }
                    return false;
                }).count() > 2L) {
                    possibleHand.setHan(possibleHand.getHan() + 2);
                    possibleHand.getQualifiedYaku().add("Sanshoku Douku (Same Triplet in All Suits)");
                    return;
                }
            }
        }
    }
}
