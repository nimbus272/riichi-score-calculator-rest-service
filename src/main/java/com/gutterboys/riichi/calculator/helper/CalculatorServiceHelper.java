package com.gutterboys.riichi.calculator.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorResponse;

@Component
public class CalculatorServiceHelper {

    @Autowired
    HandSortUtil handSortUtil;
    @Autowired
    ScoreUtil scoreUtil;

    public void stageTrackerData(RiichiCalculatorRequest request, RiichiCalculatorResponse response,
            CalculatorTracker tracker) {

        tracker.getTiles().addAll(request.getTiles());

        if (request.getOpenMelds().size() > 0) {
            handSortUtil.addOpenMeldsToTiles(request, tracker);
        }

        List<Integer> redFives = request.getTiles().stream().filter(tile -> tile > 33).collect(Collectors.toList());
        if (redFives.size() > 0) {
            handSortUtil.swapFives(redFives, tracker);
        }

        if (request.getDoraTiles().size() > 0) {
            scoreUtil.countDora(request, tracker);
        }

        tracker.getTiles().sort((a, b) -> a - b);
        response.getTiles().addAll(tracker.getTiles());
    }
}
