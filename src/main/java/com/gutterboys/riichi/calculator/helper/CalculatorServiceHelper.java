package com.gutterboys.riichi.calculator.helper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.SpecialScoringType;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.Meld;
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
            response.setTsumoFromNonDealer((int) dealerPaymentTsumo * 100);
            response.setTsumoFromDealer((int) nonDealerPaymentTsumo * 100);
            LOGGER.debug("Tsumo to non dealer: {}, Tsumo to dealer: {}", response.getTsumoFromNonDealer(),
                    response.getTsumoFromDealer());
            return;
        } else {
            response.setRonToDealer((int) ronToDealer * 100);
            response.setRonToNonDealer((int) ronToNonDealer * 100);
            LOGGER.debug("Ron to non dealer: {}, Ron to dealer: {}", response.getRonToNonDealer(),
                    response.getRonToDealer());
            return;
        }

    }

    public void determineSpecialScoring(ScoreResponse response) {

        if (response.getHan() > 12) {
            response.setSpecialScoreType(SpecialScoringType.YAKUMAN);
            return;
        } else if (response.getHan() > 10) {
            response.setSpecialScoreType(SpecialScoringType.SANBAIMAN);
            return;
        } else if (response.getHan() > 7) {
            response.setSpecialScoreType(SpecialScoringType.BAIMAN);
            return;
        } else if (response.getHan() > 5) {
            response.setSpecialScoreType(SpecialScoringType.HANEMAN);
            return;
        } else if (response.getHan() > 4) {
            response.setSpecialScoreType(SpecialScoringType.MANGAN);
            return;
        }
    }

    public void setSpecialScoring(GameContext gameContext, ScoreResponse response) {
        switch (response.getSpecialScoreType()) {
            case SpecialScoringType.MANGAN:
                if (gameContext.isTsumo()) {
                    response.setTsumoFromDealer(4000);
                    response.setTsumoFromNonDealer(2000);
                } else {
                    response.setRonToDealer(12000);
                    response.setRonToNonDealer(8000);
                }
                break;
            case SpecialScoringType.HANEMAN:
                if (gameContext.isTsumo()) {
                    response.setTsumoFromDealer(6000);
                    response.setTsumoFromNonDealer(3000);
                } else {
                    response.setRonToDealer(18000);
                    response.setRonToNonDealer(12000);
                }
                break;
            case SpecialScoringType.BAIMAN:
                if (gameContext.isTsumo()) {
                    response.setTsumoFromDealer(8000);
                    response.setTsumoFromNonDealer(4000);
                } else {
                    response.setRonToDealer(24000);
                    response.setRonToNonDealer(16000);
                }
                break;
            case SpecialScoringType.SANBAIMAN:
                if (gameContext.isTsumo()) {
                    response.setTsumoFromDealer(12000);
                    response.setTsumoFromNonDealer(6000);
                } else {
                    response.setRonToDealer(36000);
                    response.setRonToNonDealer(24000);
                }
                break;
            case SpecialScoringType.YAKUMAN:
                if (gameContext.isTsumo()) {
                    response.setTsumoFromDealer(16000);
                    response.setTsumoFromNonDealer(8000);
                } else {
                    response.setRonToDealer(48000);
                    response.setRonToNonDealer(32000);
                }
                break;
        }
    }

    public void breakIntoMelds(GameContext gameContext) {
        List<String> hand = new ArrayList<String>(gameContext.getHand());

        while (hand.size() != 0) {
            Meld meld = new Meld();
            String startingTile = hand.get(0);

        }
    }

}
