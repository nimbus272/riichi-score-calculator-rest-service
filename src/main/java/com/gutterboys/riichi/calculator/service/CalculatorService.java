package com.gutterboys.riichi.calculator.service;

import java.util.Collections;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.exception.InvalidHandException;
import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.helper.ScoreUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
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
        if (gameContext.getOpenMelds().size() > 0) {
            for (int i = 0; i < gameContext.getOpenMelds().size(); i++) {
                gameContext.getTiles().addAll(gameContext.getOpenMelds().get(i));
            }
        }
        LOGGER.info("Calculating score...");
        response.getTiles().addAll(gameContext.getTiles());
        handSortUtil.swapFives(gameContext);
        if (gameContext.getDoraTiles().size() > 0) {
            scoreUtil.countDora(gameContext);
        }
        gameContext.getTiles().sort((a, b) -> a - b);
        eligibilityEngine.executeFirst(gameContext, response);

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

        if (response.getPossibleHands().isEmpty()) {
            LOGGER.info("No possible hands found");
            throw new InvalidHandException("No possible hands found");
        }

        LOGGER.info("Determining compatible yaku...");
        for (int i = 0; i < response.getPossibleHands().size(); i++) {

            PossibleHand hand = response.getPossibleHands().get(i);

            scoreUtil.countFu(gameContext, hand);

            eligibilityEngine.executeUniversal(gameContext, hand);

            if (!Collections.disjoint(RiichiCalculatorConstants.STANDARD_YAKU_EXCLUSION_LIST,
                    hand.getQualifiedYaku())) {
                eligibilityEngine.executeCommon(gameContext, hand);
            }

            if (hand.getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)")) {
                eligibilityEngine.executeSpecialSevenPairs(gameContext, hand);
            }

            eligibilityEngine.executeLast(gameContext, hand);

        }

        for (int i = 0; i < response.getPossibleHands().size(); i++) {
            scoreUtil.determineScore(response, gameContext, response.getPossibleHands().get(i));
        }

    }
}
