package com.gutterboys.riichi.calculator.service;

import java.util.Set;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gutterboys.riichi.calculator.exception.InvalidHandException;
import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GenericResponse;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorResponse;
import com.gutterboys.riichi.calculator.yaku.universal.UniversalYaku;

import ch.qos.logback.classic.Logger;

@RestController
@Validated
@CrossOrigin
public class RiichiCalculatorRestService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RiichiCalculatorRestService.class);

    @Autowired
    CalculatorService service;

    @Autowired
    Set<UniversalYaku> allYaku;

    @PostMapping("/gutterboys/evaluateHand")
    public GenericResponse evaluateHand(@Valid @RequestBody RiichiCalculatorRequest request) {
        LOGGER.info("Request recieved!");

        RiichiCalculatorResponse response = new RiichiCalculatorResponse();
        try {
            service.evaluateHand(request, response);
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
