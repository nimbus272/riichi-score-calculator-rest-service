package com.gutterboys.riichi.calculator.service;

import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gutterboys.riichi.calculator.exception.InvalidHandException;
import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.universal.UniversalYaku;

import ch.qos.logback.classic.Logger;

@RestController
public class RiichiCalculatorRestService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RiichiCalculatorRestService.class);

    @Autowired
    CalculatorService service;

    @Autowired
    Set<UniversalYaku> allYaku;

    @PostMapping("/gutterboys/evaluateHand")
    public ScoreResponse evaluateHand(@RequestBody GameContext gameContext) {
        LOGGER.info("Request recieved!");

        ScoreResponse response = new ScoreResponse();
        try {
            service.evaluateHand(gameContext, response);
        } catch (RiichiCalculatorException e) {
            if (e instanceof InvalidHandException) {
                LOGGER.info("Updating response code...");
                response.setStatus("400");
                response.setMessage(e.getMessage());
                return response;

            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.setStatus("500");
            response.setMessage("Server Error: " + e);
            return response;
        }

        return response;
    }
}
