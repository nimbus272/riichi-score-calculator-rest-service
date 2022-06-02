package com.gutterboys.riichi.calculator.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CalculatorServiceHelper;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.YakuEligibilityEngine;

import ch.qos.logback.classic.Logger;

@Component
public class CalculatorService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CalculatorService.class);

    @Autowired
    CalculatorServiceHelper helper;
    @Autowired
    YakuEligibilityEngine eligibilityEngine;

    public void calculateScore(GameContext gameContext, ScoreResponse response) {
        LOGGER.debug("Calculating score...");

        // maybe organize tiles into melds in hand?
        // helper.organizeHand(gameContext);

        // run yaku eligibility engine
        eligibilityEngine.execute(gameContext, response);

        helper.determineSpecialScoring(response);

        // run additional logic for scoring multipliers
        if (response.isMangan() || response.isHaneman() || response.isBaiman() || response.isSanbaiman()
                || response.isYakuman()) {
            helper.setSpecialScoring(gameContext, response);
            return;
        }
        helper.determineBaseScore(response);
        helper.applyScoreMultipliers(gameContext, response);

        LOGGER.debug("Successfully calculated score!");

    }
}
