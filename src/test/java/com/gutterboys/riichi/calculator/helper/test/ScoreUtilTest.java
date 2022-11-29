package com.gutterboys.riichi.calculator.helper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.constants.SpecialScoringType;
import com.gutterboys.riichi.calculator.helper.ScoreUtil;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;

public class ScoreUtilTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;

    CalculatorTracker tracker;

    ScoreUtil util = new ScoreUtil();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();

    }

    @Test
    public void determineBaseScoreTest() {
        possibleHand.setHan(2);
        possibleHand.setFu(40);

        util.determineBaseScore(possibleHand);

        assertTrue(possibleHand.getBaseScore() > 0);
        assertEquals(640, possibleHand.getBaseScore());
    }

    @Test
    public void applyScoreMultipliers_IsTsumoTest() {
        request.setTsumo(true);
        possibleHand.setBaseScore(640);

        util.applyScoreMultipliers(request, possibleHand);

        assertEquals(700, possibleHand.getTsumoFromNonDealer());
        assertEquals(1300, possibleHand.getTsumoFromDealer());
    }

    @Test
    public void applyScoreMultipliers_IsNotTsumoTest() {
        request.setTsumo(false);
        possibleHand.setBaseScore(640);

        util.applyScoreMultipliers(request, possibleHand);

        assertEquals(3900, possibleHand.getRonToDealer());
        assertEquals(2600, possibleHand.getRonToNonDealer());
    }

    @Test
    public void handleSpecialScoring_ManganTest() {
        possibleHand.setHan(5);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(SpecialScoringType.MANGAN, possibleHand.getSpecialScoreType());
    }

    @Test
    public void handleSpecialScoring_HanemanTest() {
        possibleHand.setHan(6);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(SpecialScoringType.HANEMAN, possibleHand.getSpecialScoreType());

        possibleHand.setSpecialScoreType("");
        possibleHand.setHan(7);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(SpecialScoringType.HANEMAN, possibleHand.getSpecialScoreType());
    }

    @Test
    public void handleSpecialScoring_BaimanTest() {
        possibleHand.setHan(8);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(SpecialScoringType.BAIMAN, possibleHand.getSpecialScoreType());

        possibleHand.setSpecialScoreType("");
        possibleHand.setHan(9);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(SpecialScoringType.BAIMAN, possibleHand.getSpecialScoreType());

        possibleHand.setSpecialScoreType("");
        possibleHand.setHan(10);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(SpecialScoringType.BAIMAN, possibleHand.getSpecialScoreType());
    }

    @Test
    public void handleSpecialScoring_SanbaimanTest() {
        possibleHand.setHan(11);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(SpecialScoringType.SANBAIMAN, possibleHand.getSpecialScoreType());

        possibleHand.setSpecialScoreType("");
        possibleHand.setHan(12);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(SpecialScoringType.SANBAIMAN, possibleHand.getSpecialScoreType());
    }

    @Test
    public void handleSpecialScoring_YakumanTest() {
        possibleHand.setHan(13);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(SpecialScoringType.YAKUMAN, possibleHand.getSpecialScoreType());

        possibleHand.setSpecialScoreType("");
        possibleHand.setHan(14);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(SpecialScoringType.YAKUMAN, possibleHand.getSpecialScoreType());
    }

    @Test()
    public void handleSpecialScoring_FourHanTest() {
        possibleHand.setHan(4);

        assertThrows(NullPointerException.class,
                () -> util.handleSpecialScoring(request, possibleHand));

    }

    @Test
    public void handleSpecialScoring_ManganTsumoTest() {
        request.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.MANGAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(4000, possibleHand.getTsumoFromDealer());
        assertEquals(2000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_ManganRonTest() {
        request.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.MANGAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(12000, possibleHand.getRonToDealer());
        assertEquals(8000, possibleHand.getRonToNonDealer());
    }

    @Test
    public void handleSpecialScoring_HanemanTsumoTest() {
        request.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.HANEMAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(6000, possibleHand.getTsumoFromDealer());
        assertEquals(3000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_HanemanRonTest() {
        request.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.HANEMAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(18000, possibleHand.getRonToDealer());
        assertEquals(12000, possibleHand.getRonToNonDealer());
    }

    @Test
    public void handleSpecialScoring_BaimanTsumoTest() {
        request.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.BAIMAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(8000, possibleHand.getTsumoFromDealer());
        assertEquals(4000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_BaimanRonTest() {
        request.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.BAIMAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(24000, possibleHand.getRonToDealer());
        assertEquals(16000, possibleHand.getRonToNonDealer());
    }

    @Test
    public void handleSpecialScoring_SanbaimanTsumoTest() {
        request.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.SANBAIMAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(12000, possibleHand.getTsumoFromDealer());
        assertEquals(6000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_SanbaimanRonTest() {
        request.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.SANBAIMAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(36000, possibleHand.getRonToDealer());
        assertEquals(24000, possibleHand.getRonToNonDealer());
    }

    @Test
    public void handleSpecialScoring_YakumanTsumoTest() {
        request.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.YAKUMAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(16000, possibleHand.getTsumoFromDealer());
        assertEquals(8000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_YakumanRonTest() {
        request.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.YAKUMAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(48000, possibleHand.getRonToDealer());
        assertEquals(32000, possibleHand.getRonToNonDealer());
    }

    @Test
    public void testCountDora_2dora() {
        request.getDoraTiles().addAll(Arrays.asList(0, 1));
        tracker.getTiles()
                .addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 33, 33));
        util.countDora(request, tracker);
        assertEquals(2, tracker.getDoraCount());
    }

    @Test
    public void testCountDora_NoDora() {
        request.getDoraTiles().addAll(Arrays.asList(0, 1));
        tracker.getTiles()
                .addAll(Arrays.asList(3, 3, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 33, 33));
        util.countDora(request, tracker);
        assertEquals(0, tracker.getDoraCount());
    }

    @Test
    public void testCountDora_MultipleTiles() {
        request.getDoraTiles().addAll(Arrays.asList(0, 1, 0));
        tracker.getTiles()
                .addAll(Arrays.asList(0, 0, 0, 3, 4, 5, 6, 7, 8, 27, 27, 27, 1, 1));
        util.countDora(request, tracker);
        assertEquals(8, tracker.getDoraCount());
    }

    @Test
    public void handleSpecialScoring_DoubleYakumanTsumoTest() {
        request.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.DOUBLE_YAKUMAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(32000, possibleHand.getTsumoFromDealer());
        assertEquals(16000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_DoubleYakumanRonTest() {
        request.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.DOUBLE_YAKUMAN);

        util.handleSpecialScoring(request, possibleHand);

        assertEquals(96000, possibleHand.getRonToDealer());
        assertEquals(64000, possibleHand.getRonToNonDealer());
    }
}
