package com.gutterboys.riichi.calculator.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.GenericResponse;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

import ch.qos.logback.classic.Logger;

@RestController
public class RiichiCalculatorRestService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RiichiCalculatorRestService.class);

    @Autowired
    CalculatorService service;

    @GetMapping(value = "/test")
    public GenericResponse testEndpoint() {
        LOGGER.info("hey we be loggin baby");
        return new GenericResponse();
    }

    @PostMapping("/gutterboys/calculateScore")
    public ScoreResponse calculateScore(@RequestBody GameContext gameContext) {
        ScoreResponse response = new ScoreResponse();
        service.calculateScore(gameContext, response);
        return response;
    }
}
