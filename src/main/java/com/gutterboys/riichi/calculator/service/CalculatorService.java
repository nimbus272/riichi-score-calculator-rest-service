package com.gutterboys.riichi.calculator.service;

import org.apache.commons.logging.Log;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        LOGGER.debug("Calculating score...");

        handSortUtil.swapFives(gameContext);
        if (gameContext.getDoraTiles().size() > 0) {
            scoreUtil.countDora(gameContext);
        }
        gameContext.getHand().sort((a, b) -> a - b);
        eligibilityEngine.executeSpecial(gameContext, response);

        if (response.getQualifiedYaku().contains("Kokushi Musou (Thirteen Orphans)")) {
            scoreUtil.handleSpecialScoring(gameContext, response);
            return;
        }
        handSortUtil.checkHonors(gameContext);

        // Loop over remaining tiles in hand to see what melds can be made
        PossibleMelds possibleMelds = handSortUtil.reduceHand(gameContext);
        LOGGER.info("Tiles in hand: {}", gameContext.getHand());
        LOGGER.info("Melds: {}", gameContext.getMelds());
        // if (gameContext.getHand().stream().filter(x -> x != -1).count() == 0) {
        // // check all the other yaku
        // eligibilityEngine.execute(gameContext, response);
        // return;
        // }
        // CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds);
        // iterate until all tiles are sorted or it's impossible to reduce

        // profit

    }
}
