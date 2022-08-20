package com.gutterboys.riichi.calculator.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.helper.ScoreUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleMelds;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.YakuEligibilityEngine;

import ch.qos.logback.classic.Logger;

@Component
public class CalculatorService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CalculatorService.class);

    @Autowired
    ScoreUtil scoreUtil;
    @Autowired
    YakuEligibilityEngine eligibilityEngine;
    @Autowired
    HandSortUtil handSortUtil;

    public void evaluateHand(GameContext gameContext, ScoreResponse response) throws RiichiCalculatorException {
        LOGGER.info("Calculating score...");
        response.getTiles().addAll(gameContext.getTiles());
        handSortUtil.swapFives(gameContext);
        if (gameContext.getDoraTiles().size() > 0) {
            scoreUtil.countDora(gameContext);
        }
        gameContext.getTiles().sort((a, b) -> a - b);
        eligibilityEngine.executeSpecial(gameContext, response);

        if (!CollectionUtils.isEmpty(response.getPossibleHands())
                && response.getPossibleHands().get(0).getQualifiedYaku().contains("Kokushi Musou (Thirteen Orphans)")) {
            scoreUtil.handleSpecialScoring(gameContext, response.getPossibleHands().get(0));
            return;
        }
        handSortUtil.checkHonors(gameContext);

        // Loop over remaining tiles in hand to see what melds can be made
        PossibleMelds possibleMelds = new PossibleMelds();
        if (response.getPossibleHands().isEmpty()) {
            LOGGER.info("Reducing hand...");
            handSortUtil.reduceHand(gameContext, response, possibleMelds);
            CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds, gameContext.getTiles());
        }
        if (response.getPossibleHands().isEmpty()) {
            handSortUtil.reducePossibleMelds(possibleMelds, gameContext, response);
        }

        if (response.getPossibleHands().size() > 0) {
            LOGGER.info("Determining compatible general yaku...");
            for (int i = 0; i < response.getPossibleHands().size(); i++) {
                eligibilityEngine.executeAllCompatible(gameContext, response.getPossibleHands().get(i));
            }
        }
        // if (gameContext.getTiles().stream().filter(x -> x != -1).count() == 0) {
        // // check all the other yaku
        // eligibilityEngine.execute(gameContext, response);
        // return;
        // }
        // CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds);
        // iterate until all tiles are sorted or it's impossible to reduce

        // profit

    }
}
