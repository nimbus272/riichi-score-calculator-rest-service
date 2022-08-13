package com.gutterboys.riichi.calculator.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.helper.ScoreUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
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

    public void evaluateHand(GameContext gameContext, ScoreResponse response) {
        LOGGER.debug("Calculating score...");

        //if red fives
            //swap out, increment dora
        handSortUtil.swapFives(gameContext);
        //if dora list
            //increment dora for each in hand
        //if dora int
            //add to dora on gamecontext

        //Sort tiles by index

        //Evaluate special hand types

        //Sort Honors into melds

        //Check if we have more than one pair, if so error out

        //Loop over remaining tiles in hand to see what melds can be made

        //isolate only possible melds

        //iterate until all tiles are sorted or it's impossible to reduce

        //profit

        LOGGER.debug("Successfully calculated score!");

    }
}
