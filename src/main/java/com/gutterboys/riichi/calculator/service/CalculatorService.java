package com.gutterboys.riichi.calculator.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.exception.InvalidHandException;
import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
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

    public void evaluateHand(RiichiCalculatorRequest request, RiichiCalculatorResponse response)
            throws RiichiCalculatorException {
        LOGGER.info("Calculating score...");

        CalculatorTracker tracker = new CalculatorTracker();

        stageTrackerData(request, tracker, response);

        eligibilityEngine.executeFirst(request, tracker, response);

        sortHand(tracker, response);

        determineYakuEligibility(request, tracker, response);

        determineScore(request, tracker, response);

    }

    private void stageTrackerData(RiichiCalculatorRequest request,
            CalculatorTracker tracker, RiichiCalculatorResponse response) {

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

    private void sortHand(CalculatorTracker tracker, RiichiCalculatorResponse response)
            throws RiichiCalculatorException {

        handSortUtil.checkHonors(tracker);

        // Loop over remaining tiles in hand to see what melds can be made
        PossibleMelds possibleMelds = new PossibleMelds();
        if (response.getPossibleHands().isEmpty()) {
            LOGGER.info("Reducing hand...");
            handSortUtil.determineConfirmedMelds(tracker, response, possibleMelds);
        }

        if (response.getPossibleHands().isEmpty()) {
            handSortUtil.guessAndCheckPossibleMelds(possibleMelds, tracker, response);
        }

        if (response.getPossibleHands().isEmpty()) {
            LOGGER.info("No possible hands found");
            throw new InvalidHandException("No possible hands found");
        }

    }

    private void determineYakuEligibility(RiichiCalculatorRequest request, CalculatorTracker tracker,
            RiichiCalculatorResponse response) throws RiichiCalculatorException {

        LOGGER.info("Determining compatible yaku...");
        for (int i = 0; i < response.getPossibleHands().size(); i++) {

            PossibleHand hand = response.getPossibleHands().get(i);

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
    }

    private void determineScore(RiichiCalculatorRequest request, CalculatorTracker tracker,
            RiichiCalculatorResponse response) {
        for (int i = 0; i < response.getPossibleHands().size(); i++) {
            PossibleHand hand = response.getPossibleHands().get(i);
            scoreUtil.countFu(request, hand);
            scoreUtil.determineScore(response, request, tracker, hand);
        }
    }
}
