package com.gutterboys.riichi.calculator.yaku.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Sananko implements CommonYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, PossibleHand possibleHand) {
        List<List<Integer>> ponsOrKans = possibleHand.getMelds().stream()
                .filter(meld -> !CommonUtil.isChi(meld) && meld.size() > 2)
                .collect(Collectors.toList());

        if (ponsOrKans.size() > 2) {
            for (int i = 0; i < ponsOrKans.size(); i++) {
                if (request.getOpenMelds().contains(ponsOrKans.get(i))) {
                    return;
                }
            }
            possibleHand.setHan(possibleHand.getHan() + 2);
            possibleHand.getQualifiedYaku()
                    .add("Sananko (3 Concealed Triplets)");
        }

    }

}
