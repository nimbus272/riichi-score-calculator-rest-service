package com.gutterboys.riichi.calculator.yaku.standard;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Sananko implements StandardStructureYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        List<List<Integer>> ponsOrKans = possibleHand.getMelds().stream().filter(meld -> !CommonUtil.isChi(meld))
                .collect(Collectors.toList());

        if (ponsOrKans.size() > 2) {
            for (int i = 0; i < ponsOrKans.size(); i++) {
                if (gameContext.getOpenMelds().contains(ponsOrKans.get(i))) {
                    return;
                }
            }
            possibleHand.setHan(possibleHand.getHan() + 2);
            possibleHand.getQualifiedYaku()
                    .add("Sananko (3 Concealed Triplets)");
        }

    }

}
