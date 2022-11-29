package com.gutterboys.riichi.calculator.yaku.first;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.PossibleMelds;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorResponse;

@Component
public class ChiitoitsuOrRyanpeiko implements FirstYaku {

    @Autowired
    HandSortUtil sortUtil;

    @Override
    public void execute(RiichiCalculatorRequest request, CalculatorTracker tracker, RiichiCalculatorResponse response)
            throws RiichiCalculatorException {
        if (request.isOpened()) {
            return;
        }
        PossibleHand possibleHand = new PossibleHand();

        List<Integer> tempHand = new ArrayList<Integer>(tracker.getTiles());
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

            for (int i = 0; i < tracker.getTiles().size(); i++) {
                int tile = tracker.getTiles().get(i);
                sortUtil.checkChi(tracker.getTiles(), tile, possibleChis, 2);
            }
            if (possibleChis.size() >= 12) {
                generateMeldsForRyanpeiko(possibleHand, tracker, response);
                for (int i = 0; i < response.getPossibleHands().size(); i++) {
                    PossibleHand ryanpeiko = response.getPossibleHands().get(i);
                    ryanpeiko.setHan(ryanpeiko.getHan() + 3);
                    ryanpeiko.getQualifiedYaku().add("Ryanpeiko (Two sets of identical sequences)");
                    ryanpeiko.getTiles().addAll(response.getTiles());
                    ryanpeiko.getMelds().sort((a, b) -> a.get(0) - b.get(0));
                }
                return;
            }

            possibleHand.setHan(possibleHand.getHan() + 2);
            possibleHand.getQualifiedYaku().add("Chiitoitsu (Seven Pairs)");
            possibleHand.setFu(25);
            possibleHand.getTiles().addAll(response.getTiles());
            generateMeldsForChiitoitsu(possibleHand, tracker);
            possibleHand.getMelds().sort((a, b) -> a.get(0) - b.get(0));
            response.getPossibleHands().add(possibleHand);
        }

    }

    private void generateMeldsForChiitoitsu(PossibleHand possibleHand, CalculatorTracker tracker) {
        for (int i = 0; i < tracker.getTiles().size(); i++) {
            int tile = tracker.getTiles().get(i);

            if (tile == -1) {
                continue;
            }

            List<Integer> pair = tracker.getTiles().stream().filter(x -> x == tile).collect(Collectors.toList());
            possibleHand.getMelds().add(pair);
            tracker.getTiles().remove(tracker.getTiles().indexOf(tile));
            tracker.getTiles().remove(tracker.getTiles().indexOf(tile));
            tracker.getTiles().add(0, -1);
            tracker.getTiles().add(0, -1);

        }

    }

    private void generateMeldsForRyanpeiko(PossibleHand possibleHand, CalculatorTracker tracker,
            RiichiCalculatorResponse response)
            throws RiichiCalculatorException {

        PossibleMelds possibleMelds = new PossibleMelds();

        sortUtil.determineConfirmedMelds(tracker, response, possibleMelds);

        if (response.getPossibleHands().size() == 0) {
            sortUtil.guessAndCheckPossibleMelds(possibleMelds, tracker, response);
        }

    }

}
