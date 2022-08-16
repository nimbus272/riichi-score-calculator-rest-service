package com.gutterboys.riichi.calculator.yaku;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                possibleHand.getTiles().addAll(response.getTiles());
                response.getPossibleHands().add(possibleHand);
                return;
            }

            possibleHand.setHan(possibleHand.getHan() + 2);
            possibleHand.getQualifiedYaku().add("Chiitoitsu (Seven Pairs)");
            possibleHand.getTiles().addAll(response.getTiles());
            generateMeldsForChiitoitsu(possibleHand, gameContext);
            response.getPossibleHands().add(possibleHand);
        }

    }

    private void generateMeldsForChiitoitsu(PossibleHand possibleHand, GameContext gameContext) {
        for (int i = 0; i < gameContext.getTiles().size(); i++) {
            int tile = gameContext.getTiles().get(i);

            if (tile == -1) {
                continue;
            }

            List<Integer> pair = gameContext.getTiles().stream().filter(x -> x == tile).collect(Collectors.toList());
            possibleHand.getMelds().add(pair);
            gameContext.getTiles().remove(gameContext.getTiles().indexOf(tile));
            gameContext.getTiles().remove(gameContext.getTiles().indexOf(tile));
            gameContext.getTiles().add(0, -1);
            gameContext.getTiles().add(0, -1);

        }

    }

    // TODO get melds for ryanpeiko

}
