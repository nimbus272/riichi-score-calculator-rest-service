package com.gutterboys.riichi.calculator.service;

import java.util.Collections;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.exception.InvalidHandException;
import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.helper.CalculatorServiceHelper;
import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.helper.ScoreUtil;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.PossibleMelds;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorResponse;
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
    @Autowired
    CalculatorServiceHelper helper;

    public void evaluateHand(RiichiCalculatorRequest request, RiichiCalculatorResponse response)
            throws RiichiCalculatorException {
        LOGGER.info("Calculating score...");

        CalculatorTracker tracker = new CalculatorTracker();

        helper.stageTrackerData(request, response, tracker);

        eligibilityEngine.executeFirst(request, tracker, response);

        handSortUtil.checkHonors(tracker);

        // Loop over remaining tiles in hand to see what melds can be made
        PossibleMelds possibleMelds = new PossibleMelds();
        if (response.getPossibleHands().isEmpty()) {
            LOGGER.info("Reducing hand...");
            handSortUtil.reduceHand(tracker, response, possibleMelds);
            CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds, tracker.getTiles());
        }
        if (response.getPossibleHands().isEmpty()) {
            handSortUtil.reducePossibleMelds(possibleMelds, tracker, response);
        }

        if (response.getPossibleHands().isEmpty()) {
            LOGGER.info("No possible hands found");
            throw new InvalidHandException("No possible hands found");
        }

        LOGGER.info("Determining compatible yaku...");
        for (int i = 0; i < response.getPossibleHands().size(); i++) {

            PossibleHand hand = response.getPossibleHands().get(i);

            scoreUtil.countFu(request, hand);

            eligibilityEngine.executeUniversal(request, tracker, hand);

            if (Collections.disjoint(RiichiCalculatorConstants.STANDARD_YAKU_EXCLUSION_LIST,
                    hand.getQualifiedYaku())) {
                eligibilityEngine.executeCommon(request, tracker, hand);
            }

            if (hand.getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)")) {
                eligibilityEngine.executeSpecialSevenPairs(request, tracker, hand);
            }

            eligibilityEngine.executeLast(request, hand);

        }

        for (int i = 0; i < response.getPossibleHands().size(); i++) {
            scoreUtil.determineScore(response, request, tracker, response.getPossibleHands().get(i));
        }

    }
}
