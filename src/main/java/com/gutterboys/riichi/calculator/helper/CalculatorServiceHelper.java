package com.gutterboys.riichi.calculator.helper;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

import ch.qos.logback.classic.Logger;

@Component
public class CalculatorServiceHelper {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CalculatorServiceHelper.class);

    public void determineBaseScore(ScoreResponse response) {

        LOGGER.debug("Determining base score... Han: {}, Fu: {}", response.getHan(), response.getFu());

        int hanPlusTwo = response.getHan() + 2;
        int multiplier = (int) Math.pow(2, hanPlusTwo);

        response.setBaseScore(response.getFu() * multiplier);

        LOGGER.debug("Base score has been set to: {}", response.getBaseScore());
    }

    public void applyScoreMultipliers(GameContext context, ScoreResponse response) {

        LOGGER.debug("Setting actual scores... Base score: {}", response.getBaseScore());

        int dealerPaymentTsumo = response.getBaseScore() * 2;
        dealerPaymentTsumo = (int) Math.ceil((double) dealerPaymentTsumo / 100);
        int nonDealerPaymentTsumo = response.getBaseScore();
        nonDealerPaymentTsumo = (int) Math.ceil((double) nonDealerPaymentTsumo / 100);
        int ronToDealer = response.getBaseScore() * 6;
        ronToDealer = (int) Math.ceil((double) ronToDealer / 100);
        int ronToNonDealer = response.getBaseScore() * 4;
        ronToNonDealer = (int) Math.ceil((double) ronToNonDealer / 100);

        if (context.isTsumo()) {
            response.setTsumoToNonDealer((int) dealerPaymentTsumo * 100);
            response.setTsumoToDealer((int) nonDealerPaymentTsumo * 100);
            LOGGER.debug("Tsumo to non dealer: {}, Tsumo to dealer: {}", response.getTsumoToNonDealer(),
                    response.getTsumoToDealer());
            return;
        } else {
            response.setRonToDealer((int) ronToDealer * 100);
            response.setRonToNonDealer((int) ronToNonDealer * 100);
            LOGGER.debug("Ron to non dealer: {}, Ron to dealer: {}", response.getRonToNonDealer(),
                    response.getRonToDealer());
            return;
        }

    }

}
