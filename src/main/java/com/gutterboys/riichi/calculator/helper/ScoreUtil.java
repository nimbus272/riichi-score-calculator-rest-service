package com.gutterboys.riichi.calculator.helper;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.constants.SpecialScoringType;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.YakuEligibilityEngine;

import ch.qos.logback.classic.Logger;

@Component
public class ScoreUtil {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ScoreUtil.class);

    @Autowired
    YakuEligibilityEngine engine;

    public void countDora(RiichiCalculatorRequest request) {
        LOGGER.info("Counting dora...");
        for (Integer tile : request.getDoraTiles()) {
            switch ((int) request.getTiles().stream().filter(x -> x.equals(tile)).count()) {
                case 1:
                    request.setDoraCount(request.getDoraCount() + 1);
                    continue;
                case 2:
                    request.setDoraCount(request.getDoraCount() + 2);
                    continue;
                case 3:
                    request.setDoraCount(request.getDoraCount() + 3);
                    continue;
                case 4:
                    request.setDoraCount(request.getDoraCount() + 4);
                    continue;
                default:
                    continue;
            }
        }
    }

    public void determineScore(ScoreResponse response, RiichiCalculatorRequest request, PossibleHand possibleHand) {
        LOGGER.info("Determining score...");
        possibleHand.setHan(possibleHand.getHan() + request.getDoraCount());
        if (possibleHand.getHan() > 4) {
            handleSpecialScoring(request, possibleHand);
            return;
        } else {
            determineBaseScore(possibleHand);
            applyScoreMultipliers(request, possibleHand);
            return;
        }

    }

    public void determineBaseScore(PossibleHand possibleHand) {

        LOGGER.debug("Determining base score... Han: {}, Fu: {}", possibleHand.getHan(), possibleHand.getFu());

        int hanPlusTwo = possibleHand.getHan() + 2;
        int multiplier = (int) Math.pow(2, hanPlusTwo);

        possibleHand.setBaseScore(possibleHand.getFu() * multiplier);

        LOGGER.debug("Base score has been set to: {}", possibleHand.getBaseScore());
    }

    public void applyScoreMultipliers(RiichiCalculatorRequest request, PossibleHand possibleHand) {
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

        if (request.isTsumo()) {
            possibleHand.setTsumoFromNonDealer((int) nonDealerPaymentTsumo * 100);
            possibleHand.setTsumoFromDealer((int) dealerPaymentTsumo * 100);
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

    public void handleSpecialScoring(RiichiCalculatorRequest request, PossibleHand possibleHand) {
        determineSpecialScoring(possibleHand);
        setSpecialScoring(request, possibleHand);
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

    private void setSpecialScoring(RiichiCalculatorRequest request, PossibleHand possibleHand) {
        LOGGER.info("Setting score based on special score type...");
        switch (possibleHand.getSpecialScoreType()) {
            case SpecialScoringType.MANGAN:
                if (request.isTsumo()) {
                    possibleHand.setTsumoFromDealer(4000);
                    possibleHand.setTsumoFromNonDealer(2000);
                } else {
                    possibleHand.setRonToDealer(12000);
                    possibleHand.setRonToNonDealer(8000);
                }
                break;
            case SpecialScoringType.HANEMAN:
                if (request.isTsumo()) {
                    possibleHand.setTsumoFromDealer(6000);
                    possibleHand.setTsumoFromNonDealer(3000);
                } else {
                    possibleHand.setRonToDealer(18000);
                    possibleHand.setRonToNonDealer(12000);
                }
                break;
            case SpecialScoringType.BAIMAN:
                if (request.isTsumo()) {
                    possibleHand.setTsumoFromDealer(8000);
                    possibleHand.setTsumoFromNonDealer(4000);
                } else {
                    possibleHand.setRonToDealer(24000);
                    possibleHand.setRonToNonDealer(16000);
                }
                break;
            case SpecialScoringType.SANBAIMAN:
                if (request.isTsumo()) {
                    possibleHand.setTsumoFromDealer(12000);
                    possibleHand.setTsumoFromNonDealer(6000);
                } else {
                    possibleHand.setRonToDealer(36000);
                    possibleHand.setRonToNonDealer(24000);
                }
                break;
            case SpecialScoringType.YAKUMAN:
                if (request.isTsumo()) {
                    possibleHand.setTsumoFromDealer(16000);
                    possibleHand.setTsumoFromNonDealer(8000);
                } else {
                    possibleHand.setRonToDealer(48000);
                    possibleHand.setRonToNonDealer(32000);
                }
                break;
            case SpecialScoringType.DOUBLE_YAKUMAN:
                if (request.isTsumo()) {
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

    public void countFu(RiichiCalculatorRequest request, PossibleHand possibleHand) {
        if (possibleHand.getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)")) {
            return;
        }
        // any winning hand is automatically awarded 20 fu
        possibleHand.setFu(possibleHand.getFu() + 20);

        // 10 fu for ron with closed hand
        if (!request.isTsumo() && !request.isOpened()) {
            possibleHand.setFu(possibleHand.getFu() + 10);
        }

        for (int i = 0; i < possibleHand.getMelds().size(); i++) {
            List<Integer> meld = possibleHand.getMelds().get(i);

            if (!CommonUtil.isChi(meld) && meld.size() == 3 && request.getOpenMelds().contains(meld)) {
                if (RiichiCalculatorConstants.SIMPLES.contains(meld.get(0))) {
                    possibleHand.setFu(possibleHand.getFu() + 2);
                } else {
                    possibleHand.setFu(possibleHand.getFu() + 4);
                }
            } else if (!CommonUtil.isChi(meld) && meld.size() == 3 && !request.getOpenMelds().contains(meld)) {
                if (RiichiCalculatorConstants.SIMPLES.contains(meld.get(0))) {
                    possibleHand.setFu(possibleHand.getFu() + 4);
                } else {
                    possibleHand.setFu(possibleHand.getFu() + 8);
                }
            } else if (!CommonUtil.isChi(meld) && meld.size() == 4 && request.getOpenMelds().contains(meld)) {
                if (RiichiCalculatorConstants.SIMPLES.contains(meld.get(0))) {
                    possibleHand.setFu(possibleHand.getFu() + 8);
                } else {
                    possibleHand.setFu(possibleHand.getFu() + 16);
                }
            } else if (!CommonUtil.isChi(meld) && meld.size() == 4 && !request.getOpenMelds().contains(meld)) {
                if (RiichiCalculatorConstants.SIMPLES.contains(meld.get(0))) {
                    possibleHand.setFu(possibleHand.getFu() + 16);
                } else {
                    possibleHand.setFu(possibleHand.getFu() + 32);
                }
            } else if (!CommonUtil.isChi(meld) && meld.size() == 2) {
                if (request.getSeatWind() == request.getPrevalentWind()
                        && request.getSeatWind() == meld.get(0)) {
                    possibleHand.setFu(possibleHand.getFu() + 4);
                } else if (request.getSeatWind() == meld.get(0) || request.getPrevalentWind() == meld.get(0)
                        || RiichiCalculatorConstants.DRAGONS.contains(meld.get(0))) {
                    possibleHand.setFu(possibleHand.getFu() + 2);
                }
            }
            // waits that matter:
            // wait is a pair that is not a part of a meld
            // wait that is the middle of a chi
            // wait is end of a terminal chi (3 or 7)

            if (possibleHand.getMelds().stream().filter((checkmeld) -> {
                if (checkmeld.contains(request.getWinningTile())) {
                    return true;
                }
                return false;
            }).count() == 1L) {
                if (meld.contains(request.getWinningTile())) {
                    if (meld.size() == 2) {
                        possibleHand.setFu(possibleHand.getFu() + 2);
                    } else if (meld.size() == 3 && meld.indexOf(request.getWinningTile()) == 1) {
                        possibleHand.setFu(possibleHand.getFu() + 2);
                    } else if ((RiichiCalculatorConstants.TERMINALS.contains(meld.get(0))
                            && meld.indexOf(request.getWinningTile()) == 2)
                            || (RiichiCalculatorConstants.TERMINALS.contains(meld.get(2))
                                    && meld.indexOf(request.getWinningTile()) == 0)) {
                        possibleHand.setFu(possibleHand.getFu() + 2);

                    }
                }
            }
            if (request.isTsumo()) {
                possibleHand.setFu(possibleHand.getFu() + 2);
            }
            if (possibleHand.getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)")) {
                possibleHand.setFu(25);
            }

        }

        if (!possibleHand.getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)")) {
            possibleHand.setFu((int) Math.ceil(possibleHand.getFu() / 10.0) * 10);
        }

    }
}
