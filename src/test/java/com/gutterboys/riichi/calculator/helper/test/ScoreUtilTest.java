package com.gutterboys.riichi.calculator.helper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.gutterboys.riichi.calculator.constants.SpecialScoringType;
import com.gutterboys.riichi.calculator.helper.ScoreUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

public class ScoreUtilTest {

    GameContext gameContext;

    PossibleHand possibleHand;

    ScoreUtil util = new ScoreUtil();

    @Before
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
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
        gameContext.setTsumo(true);
        possibleHand.setBaseScore(640);

        util.applyScoreMultipliers(gameContext, possibleHand);

        assertEquals(1300, possibleHand.getTsumoFromNonDealer());
        assertEquals(700, possibleHand.getTsumoFromDealer());
    }

    @Test
    public void applyScoreMultipliers_IsNotTsumoTest() {
        gameContext.setTsumo(false);
        possibleHand.setBaseScore(640);

        util.applyScoreMultipliers(gameContext, possibleHand);

        assertEquals(3900, possibleHand.getRonToDealer());
        assertEquals(2600, possibleHand.getRonToNonDealer());
    }

    @Test
    public void handleSpecialScoring_ManganTest() {
        possibleHand.setHan(5);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(SpecialScoringType.MANGAN, possibleHand.getSpecialScoreType());
    }

    @Test
    public void handleSpecialScoring_HanemanTest() {
        possibleHand.setHan(6);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(SpecialScoringType.HANEMAN, possibleHand.getSpecialScoreType());

        possibleHand.setSpecialScoreType("");
        possibleHand.setHan(7);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(SpecialScoringType.HANEMAN, possibleHand.getSpecialScoreType());
    }

    @Test
    public void handleSpecialScoring_BaimanTest() {
        possibleHand.setHan(8);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(SpecialScoringType.BAIMAN, possibleHand.getSpecialScoreType());

        possibleHand.setSpecialScoreType("");
        possibleHand.setHan(9);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(SpecialScoringType.BAIMAN, possibleHand.getSpecialScoreType());

        possibleHand.setSpecialScoreType("");
        possibleHand.setHan(10);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(SpecialScoringType.BAIMAN, possibleHand.getSpecialScoreType());
    }

    @Test
    public void handleSpecialScoring_SanbaimanTest() {
        possibleHand.setHan(11);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(SpecialScoringType.SANBAIMAN, possibleHand.getSpecialScoreType());

        possibleHand.setSpecialScoreType("");
        possibleHand.setHan(12);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(SpecialScoringType.SANBAIMAN, possibleHand.getSpecialScoreType());
    }

    @Test
    public void handleSpecialScoring_YakumanTest() {
        possibleHand.setHan(13);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(SpecialScoringType.YAKUMAN, possibleHand.getSpecialScoreType());

        possibleHand.setSpecialScoreType("");
        possibleHand.setHan(14);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(SpecialScoringType.YAKUMAN, possibleHand.getSpecialScoreType());
    }

    @Test(expected = NullPointerException.class)
    public void handleSpecialScoring_FourHanTest() {
        possibleHand.setHan(4);

        util.handleSpecialScoring(gameContext, possibleHand);
    }

    @Test
    public void handleSpecialScoring_ManganTsumoTest() {
        gameContext.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.MANGAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(4000, possibleHand.getTsumoFromDealer());
        assertEquals(2000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_ManganRonTest() {
        gameContext.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.MANGAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(12000, possibleHand.getRonToDealer());
        assertEquals(8000, possibleHand.getRonToNonDealer());
    }

    @Test
    public void handleSpecialScoring_HanemanTsumoTest() {
        gameContext.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.HANEMAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(6000, possibleHand.getTsumoFromDealer());
        assertEquals(3000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_HanemanRonTest() {
        gameContext.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.HANEMAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(18000, possibleHand.getRonToDealer());
        assertEquals(12000, possibleHand.getRonToNonDealer());
    }

    @Test
    public void handleSpecialScoring_BaimanTsumoTest() {
        gameContext.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.BAIMAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(8000, possibleHand.getTsumoFromDealer());
        assertEquals(4000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_BaimanRonTest() {
        gameContext.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.BAIMAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(24000, possibleHand.getRonToDealer());
        assertEquals(16000, possibleHand.getRonToNonDealer());
    }

    @Test
    public void handleSpecialScoring_SanbaimanTsumoTest() {
        gameContext.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.SANBAIMAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(12000, possibleHand.getTsumoFromDealer());
        assertEquals(6000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_SanbaimanRonTest() {
        gameContext.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.SANBAIMAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(36000, possibleHand.getRonToDealer());
        assertEquals(24000, possibleHand.getRonToNonDealer());
    }

    @Test
    public void handleSpecialScoring_YakumanTsumoTest() {
        gameContext.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.YAKUMAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(16000, possibleHand.getTsumoFromDealer());
        assertEquals(8000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_YakumanRonTest() {
        gameContext.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.YAKUMAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(48000, possibleHand.getRonToDealer());
        assertEquals(32000, possibleHand.getRonToNonDealer());
    }

    @Test
    public void testCountDora_2dora() {
        gameContext.getDoraTiles().addAll(Arrays.asList(0, 1));
        gameContext.getTiles()
                .addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 33, 33));
        util.countDora(gameContext);
        assertEquals(2, gameContext.getDoraCount());
    }

    @Test
    public void testCountDora_NoDora() {
        gameContext.getDoraTiles().addAll(Arrays.asList(0, 1));
        gameContext.getTiles()
                .addAll(Arrays.asList(3, 3, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 33, 33));
        util.countDora(gameContext);
        assertEquals(0, gameContext.getDoraCount());
    }

    @Test
    public void testCountDora_MultipleTiles() {
        gameContext.getDoraTiles().addAll(Arrays.asList(0, 1, 0));
        gameContext.getTiles()
                .addAll(Arrays.asList(0, 0, 0, 3, 4, 5, 6, 7, 8, 27, 27, 27, 1, 1));
        util.countDora(gameContext);
        assertEquals(8, gameContext.getDoraCount());
    }

    @Test
    public void handleSpecialScoring_DoubleYakumanTsumoTest() {
        gameContext.setTsumo(true);
        possibleHand.setSpecialScoreType(SpecialScoringType.DOUBLE_YAKUMAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(32000, possibleHand.getTsumoFromDealer());
        assertEquals(16000, possibleHand.getTsumoFromNonDealer());
    }

    @Test
    public void handleSpecialScoring_DoubleYakumanRonTest() {
        gameContext.setTsumo(false);
        possibleHand.setSpecialScoreType(SpecialScoringType.DOUBLE_YAKUMAN);

        util.handleSpecialScoring(gameContext, possibleHand);

        assertEquals(96000, possibleHand.getRonToDealer());
        assertEquals(64000, possibleHand.getRonToNonDealer());
    }
}
