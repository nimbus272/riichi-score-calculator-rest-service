package com.gutterboys.riichi.calculator.helper;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.SpecialScoringType;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.YakuEligibilityEngine;

import ch.qos.logback.classic.Logger;

@Component
public class ScoreUtil {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ScoreUtil.class);

    @Autowired
    YakuEligibilityEngine engine;

    public void countDora(GameContext gameContext) {
        LOGGER.info("Counting dora...");
        for (Integer tile : gameContext.getDoraTiles()) {
            switch ((int) gameContext.getHand().stream().filter(x -> x.equals(tile)).count()) {
                case 1:
                    gameContext.setDoraCount(gameContext.getDoraCount() + 1);
                    continue;
                case 2:
                    gameContext.setDoraCount(gameContext.getDoraCount() + 2);
                    continue;
                case 3:
                    gameContext.setDoraCount(gameContext.getDoraCount() + 3);
                    continue;
                case 4:
                    gameContext.setDoraCount(gameContext.getDoraCount() + 4);
                    continue;
                default:
                    continue;
            }
        }
    }

    public void determineBaseScore(ScoreResponse response) {

        LOGGER.debug("Determining base score... Han: {}, Fu: {}", response.getHan(), response.getFu());

        int hanPlusTwo = response.getHan() + 2;
        int multiplier = (int) Math.pow(2, hanPlusTwo);

        response.setBaseScore(response.getFu() * multiplier);

        LOGGER.debug("Base score has been set to: {}", response.getBaseScore());
    }

    public void applyScoreMultipliers(GameContext context, ScoreResponse response) {
        LOGGER.info("Applying score multipliers...");
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

    public void handleSpecialScoring(GameContext gameContext, ScoreResponse response) {
        determineSpecialScoring(response);
        setSpecialScoring(gameContext, response);
    }

    private void determineSpecialScoring(ScoreResponse response) {
        LOGGER.info("Determining special score type...");

        if (response.getHan() > 12) {
            if (response.isDoubleYakuman()) {
                response.setSpecialScoreType(SpecialScoringType.DOUBLE_YAKUMAN);
                return;
            }
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

    private void setSpecialScoring(GameContext gameContext, ScoreResponse response) {
        LOGGER.info("Setting score based on special score type...");
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
            case SpecialScoringType.DOUBLE_YAKUMAN:
                if (gameContext.isTsumo()) {
                    response.setTsumoFromDealer(32000);
                    response.setTsumoFromNonDealer(16000);
                } else {
                    response.setRonToDealer(96000);
                    response.setRonToNonDealer(64000);
                }
                break;
            default:
                break;

        }
    }
}
