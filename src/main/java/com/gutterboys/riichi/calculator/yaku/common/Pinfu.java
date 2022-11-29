package com.gutterboys.riichi.calculator.yaku.common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Pinfu implements CommonYaku {
    private static final List<Integer> THREES = Arrays.asList(2, 11, 20);
    private static final List<Integer> SEVENS = Arrays.asList(6, 15, 24);

    @Override
    public void execute(RiichiCalculatorRequest request, PossibleHand possibleHand) {
        if (request.isOpened() || possibleHand.getTiles().size() != 14) {
            return;
        }
        if (possibleHand.getTiles().contains(request.getPrevalentWind())
                || possibleHand.getTiles().contains(request.getSeatWind())) {
            return;
        }
        if (possibleHand.getMelds().stream().filter(meld -> CommonUtil.isChi(meld)).count() == 4) {
            int winningTile = request.getWinningTile();
            List<List<Integer>> winningMelds = possibleHand.getMelds().stream()
                    .filter(meld -> meld.contains(request.getWinningTile())).collect(Collectors.toList());
            for (List<Integer> meld : winningMelds) {
                if (meld.contains(winningTile) && meld.contains(winningTile + 1) && meld.contains(winningTile + 2)
                        && !SEVENS.contains(winningTile)) {
                    possibleHand.setHan(possibleHand.getHan() + 1);
                    possibleHand.getQualifiedYaku().add("Pinfu (No-points Hand)");
                    if (request.isTsumo()) {
                        possibleHand.setFu(20);
                    } else {
                        possibleHand.setFu(30);
                    }

                    return;
                }
                if (meld.contains(winningTile) && meld.contains(winningTile - 1) && meld.contains(winningTile - 2)
                        && !THREES.contains(winningTile)) {
                    possibleHand.setHan(possibleHand.getHan() + 1);
                    possibleHand.getQualifiedYaku().add("Pinfu (No-points Hand)");
                    if (request.isTsumo()) {
                        possibleHand.setFu(20);
                    } else {
                        possibleHand.setFu(30);
                    }
                    return;
                }
            }

        }
    }
}
