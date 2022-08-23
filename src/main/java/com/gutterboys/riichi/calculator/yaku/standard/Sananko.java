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
            possibleHand.setOptHan(possibleHand.getHan() + 2);
            possibleHand.getOptQualifiedYaku()
                    .add("Sananko (3 Concealed Triplets -- Does Not Qualify if 3 Triplets Are Not Closed)");
        }

    }

}
