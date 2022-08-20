package com.gutterboys.riichi.calculator.yaku.standard;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Ippeiko implements StandardStructureYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        if (gameContext.isOpened()) {
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
