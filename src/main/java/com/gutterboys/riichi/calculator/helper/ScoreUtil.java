package com.gutterboys.riichi.calculator.helper;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.SpecialScoringType;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
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
            switch ((int) gameContext.getTiles().stream().filter(x -> x.equals(tile)).count()) {
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

    public void determineBaseScore(PossibleHand possibleHand) {

        LOGGER.debug("Determining base score... Han: {}, Fu: {}", possibleHand.getHan(), possibleHand.getFu());

        int hanPlusTwo = possibleHand.getHan() + 2;
        int multiplier = (int) Math.pow(2, hanPlusTwo);

        possibleHand.setBaseScore(possibleHand.getFu() * multiplier);

        LOGGER.debug("Base score has been set to: {}", possibleHand.getBaseScore());
    }

    public void applyScoreMultipliers(GameContext context, PossibleHand possibleHand) {
        LOGGER.info("Applying score multipliers...");
        LOGGER.debug("Setting actual scores... Base score: {}", possibleHand.getBaseScore());

        int dealerPaymentTsumo = possibleHand.getBaseScore() * 2;
        dealerPaymentTsumo = (int) Math.ceil((double) dealerPaymentTsumo / 100);
        int nonDealerPaymentTsumo = possibleHand.getBaseScore();
        nonDealerPaymentTsumo = (int) Math.ceil((double) nonDealerPaymentTsumo / 100);
        int ronToDealer = possibleHand.getBaseScore() * 6;
        ronToDealer = (int) Math.ceil((double) ronToDealer / 100);
        int ronToNonDealer = possibleHand.getBaseScore() * 4;
        ronToNonDealer = (int) Math.ceil((double) ronToNonDealer / 100);

        if (context.isTsumo()) {
            possibleHand.setTsumoFromNonDealer((int) dealerPaymentTsumo * 100);
            possibleHand.setTsumoFromDealer((int) nonDealerPaymentTsumo * 100);
            LOGGER.debug("Tsumo to non dealer: {}, Tsumo to dealer: {}", possibleHand.getTsumoFromNonDealer(),
                    possibleHand.getTsumoFromDealer());
            return;
        } else {
            possibleHand.setRonToDealer((int) ronToDealer * 100);
            possibleHand.setRonToNonDealer((int) ronToNonDealer * 100);
            LOGGER.debug("Ron to non dealer: {}, Ron to dealer: {}", possibleHand.getRonToNonDealer(),
                    possibleHand.getRonToDealer());
            return;
        }

    }

    public void handleSpecialScoring(GameContext gameContext, PossibleHand possibleHand) {
        determineSpecialScoring(possibleHand);
        setSpecialScoring(gameContext, possibleHand);
    }

    private void determineSpecialScoring(PossibleHand possibleHand) {
        LOGGER.info("Determining special score type...");

        if (possibleHand.getHan() > 12) {
            if (possibleHand.isDoubleYakuman()) {
                possibleHand.setSpecialScoreType(SpecialScoringType.DOUBLE_YAKUMAN);
                return;
            }
            possibleHand.setSpecialScoreType(SpecialScoringType.YAKUMAN);
            return;
        } else if (possibleHand.getHan() > 10) {
            possibleHand.setSpecialScoreType(SpecialScoringType.SANBAIMAN);
            return;
        } else if (possibleHand.getHan() > 7) {
            possibleHand.setSpecialScoreType(SpecialScoringType.BAIMAN);
            return;
        } else if (possibleHand.getHan() > 5) {
            possibleHand.setSpecialScoreType(SpecialScoringType.HANEMAN);
            return;
        } else if (possibleHand.getHan() > 4) {
            possibleHand.setSpecialScoreType(SpecialScoringType.MANGAN);
            return;
        }
    }

    private void setSpecialScoring(GameContext gameContext, PossibleHand possibleHand) {
        LOGGER.info("Setting score based on special score type...");
        switch (possibleHand.getSpecialScoreType()) {
            case SpecialScoringType.MANGAN:
                if (gameContext.isTsumo()) {
                    possibleHand.setTsumoFromDealer(4000);
                    possibleHand.setTsumoFromNonDealer(2000);
                } else {
                    possibleHand.setRonToDealer(12000);
                    possibleHand.setRonToNonDealer(8000);
                }
                break;
            case SpecialScoringType.HANEMAN:
                if (gameContext.isTsumo()) {
                    possibleHand.setTsumoFromDealer(6000);
                    possibleHand.setTsumoFromNonDealer(3000);
                } else {
                    possibleHand.setRonToDealer(18000);
                    possibleHand.setRonToNonDealer(12000);
                }
                break;
            case SpecialScoringType.BAIMAN:
                if (gameContext.isTsumo()) {
                    possibleHand.setTsumoFromDealer(8000);
                    possibleHand.setTsumoFromNonDealer(4000);
                } else {
                    possibleHand.setRonToDealer(24000);
                    possibleHand.setRonToNonDealer(16000);
                }
                break;
            case SpecialScoringType.SANBAIMAN:
                if (gameContext.isTsumo()) {
                    possibleHand.setTsumoFromDealer(12000);
                    possibleHand.setTsumoFromNonDealer(6000);
                } else {
                    possibleHand.setRonToDealer(36000);
                    possibleHand.setRonToNonDealer(24000);
                }
                break;
            case SpecialScoringType.YAKUMAN:
                if (gameContext.isTsumo()) {
                    possibleHand.setTsumoFromDealer(16000);
                    possibleHand.setTsumoFromNonDealer(8000);
                } else {
                    possibleHand.setRonToDealer(48000);
                    possibleHand.setRonToNonDealer(32000);
                }
                break;
            case SpecialScoringType.DOUBLE_YAKUMAN:
                if (gameContext.isTsumo()) {
                    possibleHand.setTsumoFromDealer(32000);
                    possibleHand.setTsumoFromNonDealer(16000);
                } else {
                    possibleHand.setRonToDealer(96000);
                    possibleHand.setRonToNonDealer(64000);
                }
                break;
            default:
                break;

        }
    }
}
