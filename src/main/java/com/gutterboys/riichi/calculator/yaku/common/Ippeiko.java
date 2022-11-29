package com.gutterboys.riichi.calculator.yaku.common;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Ippeiko implements CommonYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, PossibleHand possibleHand) {
        if (request.isOpened()
                || possibleHand.getQualifiedYaku().contains("Ryanpeiko (Two sets of identical sequences)")) {
            return;
        }
        for (int i = 0; i < possibleHand.getMelds().size(); i++) {
            List<Integer> meld = possibleHand.getMelds().get(i);
            if (possibleHand.getMelds().stream().filter(x -> {
                if (CommonUtil.isChi(x)) {
                    return meld.containsAll(x);
                }
                return false;
            }).count() > 1) {
                possibleHand.setHan(possibleHand.getHan() + 1);
                possibleHand.getQualifiedYaku().add("Ippeiko (Two Identical Sequences)");
                return;
            }
        }

    }

}
