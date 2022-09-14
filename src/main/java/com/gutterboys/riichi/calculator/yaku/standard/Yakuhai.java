package com.gutterboys.riichi.calculator.yaku.standard;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Yakuhai implements StandardStructureYaku {

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        List<List<Integer>> honorPonsOrKans = possibleHand.getMelds().stream().filter(meld -> {
            if (!CommonUtil.isChi(meld) && meld.size() > 2 && meld.get(0) > 26) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        for (List<Integer> meld : honorPonsOrKans) {
            if (meld.get(0) < 30) {
                addYakuAndHan(possibleHand);

            } else if (meld.get(0) > 29) {
                if (gameContext.getSeatWind() == meld.get(0)) {
                    addYakuAndHan(possibleHand);

                }
                if (gameContext.getPrevalentWind() == meld.get(0)) {
                    addYakuAndHan(possibleHand);
                }

            }
        }
    }

    private void addYakuAndHan(PossibleHand possibleHand) {
        possibleHand.setHan(possibleHand.getHan() + 1);
        if (!possibleHand.getQualifiedYaku()
                .contains("Yakuhai (Triplet or Quad of relevant winds or dragon tiles")) {
            possibleHand.getQualifiedYaku()
                    .add("Yakuhai (Triplet or Quad of relevant winds or dragon tiles");
        }
    }
}