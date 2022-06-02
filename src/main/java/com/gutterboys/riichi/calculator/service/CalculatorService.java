package com.gutterboys.riichi.calculator.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CalculatorServiceHelper;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

import ch.qos.logback.classic.Logger;

@Component
public class CalculatorService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CalculatorService.class);

    @Autowired
    CalculatorServiceHelper helper;

    public void calculateScore(GameContext context, ScoreResponse response) {

        // run yaku eligibility engine

        // run additional logic for scoring multipliers
        helper.determineBaseScore(response);
        helper.applyScoreMultipliers(context, response);

    }
}
