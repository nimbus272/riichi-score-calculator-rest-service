package com.gutterboys.riichi.calculator.yaku;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

@Component
public class ChiitoitsuOrRyanpeiko implements SpecialYaku {

    @Autowired
    HandSortUtil sortUtil;

    @Override
    public void execute(GameContext gameContext, ScoreResponse response) {
        if (gameContext.isOpened()) {
            return;
        }
        PossibleHand possibleHand = new PossibleHand();

        List<Integer> tempHand = new ArrayList<Integer>(gameContext.getTiles());
        int pairCount = 0;
        for (int i = 0; i < tempHand.size(); i++) {
            Integer tile = tempHand.get(i);
            if (tile.equals(-1)) {
                continue;
            }
            if (tempHand.stream().filter(x -> x == tile).count() == 2L) {
                CommonUtil.removeAndAddPonFromList(tempHand, tile, 2);
                pairCount++;
            }
            // TODO: write a private function to generate melds for 7 pairs / Ryanpeiko
            // hands
        }

        if (pairCount == 7) {
            List<List<Integer>> possibleChis = new ArrayList<List<Integer>>();

            for (int i = 0; i < gameContext.getTiles().size(); i++) {
                int tile = gameContext.getTiles().get(i);
                sortUtil.checkChi(gameContext.getTiles(), tile, possibleChis, 2);
            }
            if (possibleChis.size() >= 12) {
                possibleHand.setHan(possibleHand.getHan() + 3);
                possibleHand.getQualifiedYaku().add("Ryanpeiko (Two sets of identical sequences)");
                response.getPossibleHands().add(possibleHand);
                return;
            }

            possibleHand.setHan(possibleHand.getHan() + 2);
            possibleHand.getQualifiedYaku().add("Chiitoitsu (Seven Pairs)");
            response.getPossibleHands().add(possibleHand);
        }

    }

}
