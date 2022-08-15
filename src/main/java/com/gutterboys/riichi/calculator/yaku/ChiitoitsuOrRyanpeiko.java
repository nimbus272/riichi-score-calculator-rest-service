package com.gutterboys.riichi.calculator.yaku;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
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

        List<Integer> tempHand = new ArrayList<Integer>(gameContext.getHand());
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
        }

        if (pairCount == 7) {
            List<List<Integer>> possibleChis = new ArrayList<List<Integer>>();

            for (int i = 0; i < gameContext.getHand().size(); i++) {
                int tile = gameContext.getHand().get(i);
                sortUtil.checkChi(gameContext.getHand(), tile, possibleChis, 2);
            }
            if (possibleChis.size() >= 12) {
                response.setHan(response.getHan() + 3);
                response.getQualifiedYaku().add("Ryanpeiko (Two sets of identical sequences)");
                return;
            }

            response.setHan(response.getHan() + 2);
            response.getQualifiedYaku().add("Chiitoitsu (Seven Pairs)");
        }

    }

}
