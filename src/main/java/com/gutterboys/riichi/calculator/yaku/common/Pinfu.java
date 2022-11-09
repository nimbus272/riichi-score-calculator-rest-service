package com.gutterboys.riichi.calculator.yaku.common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class Pinfu implements CommonYaku {
    private static final List<Integer> THREES = Arrays.asList(2, 11, 20);
    private static final List<Integer> SEVENS = Arrays.asList(6, 15, 24);

    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        if (gameContext.isOpened() || possibleHand.getTiles().size() != 14) {
            return;
        }
        if (possibleHand.getTiles().contains(gameContext.getPrevalentWind())
                || possibleHand.getTiles().contains(gameContext.getSeatWind())) {
            return;
        }
        if (possibleHand.getMelds().stream().filter(meld -> CommonUtil.isChi(meld)).count() == 4) {
            int winningTile = gameContext.getWinningTile();
            List<List<Integer>> winningMelds = possibleHand.getMelds().stream()
                    .filter(meld -> meld.contains(gameContext.getWinningTile())).collect(Collectors.toList());
            for (List<Integer> meld : winningMelds) {
                if (meld.contains(winningTile) && meld.contains(winningTile + 1) && meld.contains(winningTile + 2)
                        && !SEVENS.contains(winningTile)) {
                    possibleHand.setHan(possibleHand.getHan() + 1);
                    possibleHand.getQualifiedYaku().add("Pinfu (No-points Hand)");
                    if (gameContext.isTsumo()) {
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
                    if (gameContext.isTsumo()) {
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
