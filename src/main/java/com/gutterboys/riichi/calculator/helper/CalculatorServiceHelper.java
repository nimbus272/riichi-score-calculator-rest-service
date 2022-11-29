package com.gutterboys.riichi.calculator.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

@Component
public class CalculatorServiceHelper {

    @Autowired
    HandSortUtil handSortUtil;
    @Autowired
    ScoreUtil scoreUtil;

    public void stageRequestResponseData(RiichiCalculatorRequest request, ScoreResponse response) {

        if (request.getOpenMelds().size() > 0) {
            handSortUtil.addOpenMeldsToTiles(request);
        }

        handSortUtil.swapFives(request);

        if (request.getDoraTiles().size() > 0) {
            scoreUtil.countDora(request);
        }
        request.getTiles().sort((a, b) -> a - b);
        response.getTiles().addAll(request.getTiles());
    }
}
