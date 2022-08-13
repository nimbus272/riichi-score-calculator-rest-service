package com.gutterboys.riichi.calculator.helper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.gutterboys.riichi.calculator.constants.SpecialScoringType;
import com.gutterboys.riichi.calculator.helper.ScoreUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

public class ScoreUtilTest {

    GameContext gameContext;

    ScoreResponse response;

    ScoreUtil util = new ScoreUtil();

    @Before
    public void setUp() {
        gameContext = new GameContext();
        response = new ScoreResponse();
    }

    @Test
    public void determineBaseScoreTest() {
        response.setHan(2);
        response.setFu(40);

        util.determineBaseScore(response);

        assertTrue(response.getBaseScore() > 0);
        assertEquals(640, response.getBaseScore());
    }

    @Test
    public void applyScoreMultipliers_IsTsumoTest() {
        gameContext.setTsumo(true);
        response.setBaseScore(640);

        util.applyScoreMultipliers(gameContext, response);

        assertEquals(1300, response.getTsumoFromNonDealer());
        assertEquals(700, response.getTsumoFromDealer());
    }

    @Test
    public void applyScoreMultipliers_IsNotTsumoTest() {
        gameContext.setTsumo(false);
        response.setBaseScore(640);

        util.applyScoreMultipliers(gameContext, response);

        assertEquals(3900, response.getRonToDealer());
        assertEquals(2600, response.getRonToNonDealer());
    }

    @Test
    public void determineSpecialScoring_ManganTest() {
        response.setHan(5);

        util.determineSpecialScoring(response);

        assertEquals(SpecialScoringType.MANGAN, response.getSpecialScoreType());
    }

    @Test
    public void determineSpecialScoring_HanemanTest() {
        response.setHan(6);

        util.determineSpecialScoring(response);

        assertEquals(SpecialScoringType.HANEMAN, response.getSpecialScoreType());

        response.setSpecialScoreType("");
        response.setHan(7);

        util.determineSpecialScoring(response);

        assertEquals(SpecialScoringType.HANEMAN, response.getSpecialScoreType());
    }

    @Test
    public void determineSpecialScoring_BaimanTest() {
        response.setHan(8);

        util.determineSpecialScoring(response);

        assertEquals(SpecialScoringType.BAIMAN, response.getSpecialScoreType());

        response.setSpecialScoreType("");
        response.setHan(9);

        util.determineSpecialScoring(response);

        assertEquals(SpecialScoringType.BAIMAN, response.getSpecialScoreType());

        response.setSpecialScoreType("");
        response.setHan(10);

        util.determineSpecialScoring(response);

        assertEquals(SpecialScoringType.BAIMAN, response.getSpecialScoreType());
    }

    @Test
    public void determineSpecialScoring_SanbaimanTest() {
        response.setHan(11);

        util.determineSpecialScoring(response);

        assertEquals(SpecialScoringType.SANBAIMAN, response.getSpecialScoreType());

        response.setSpecialScoreType("");
        response.setHan(12);

        util.determineSpecialScoring(response);

        assertEquals(SpecialScoringType.SANBAIMAN, response.getSpecialScoreType());
    }

    @Test
    public void determineSpecialScoring_YakumanTest() {
        response.setHan(13);

        util.determineSpecialScoring(response);

        assertEquals(SpecialScoringType.YAKUMAN, response.getSpecialScoreType());

        response.setSpecialScoreType("");
        response.setHan(14);

        util.determineSpecialScoring(response);

        assertEquals(SpecialScoringType.YAKUMAN, response.getSpecialScoreType());
    }

    @Test
    public void determineSpecialScoring_FourHanTest() {
        response.setHan(4);

        util.determineSpecialScoring(response);

        assertNull(response.getSpecialScoreType());
    }

    @Test
    public void setSpecialScoring_ManganTsumoTest() {
        gameContext.setTsumo(true);
        response.setSpecialScoreType(SpecialScoringType.MANGAN);

        util.setSpecialScoring(gameContext, response);

        assertEquals(4000, response.getTsumoFromDealer());
        assertEquals(2000, response.getTsumoFromNonDealer());
    }

    @Test
    public void setSpecialScoring_ManganRonTest() {
        gameContext.setTsumo(false);
        response.setSpecialScoreType(SpecialScoringType.MANGAN);

        util.setSpecialScoring(gameContext, response);

        assertEquals(12000, response.getRonToDealer());
        assertEquals(8000, response.getRonToNonDealer());
    }

    @Test
    public void setSpecialScoring_HanemanTsumoTest() {
        gameContext.setTsumo(true);
        response.setSpecialScoreType(SpecialScoringType.HANEMAN);

        util.setSpecialScoring(gameContext, response);

        assertEquals(6000, response.getTsumoFromDealer());
        assertEquals(3000, response.getTsumoFromNonDealer());
    }

    @Test
    public void setSpecialScoring_HanemanRonTest() {
        gameContext.setTsumo(false);
        response.setSpecialScoreType(SpecialScoringType.HANEMAN);

        util.setSpecialScoring(gameContext, response);

        assertEquals(18000, response.getRonToDealer());
        assertEquals(12000, response.getRonToNonDealer());
    }

    @Test
    public void setSpecialScoring_BaimanTsumoTest() {
        gameContext.setTsumo(true);
        response.setSpecialScoreType(SpecialScoringType.BAIMAN);

        util.setSpecialScoring(gameContext, response);

        assertEquals(8000, response.getTsumoFromDealer());
        assertEquals(4000, response.getTsumoFromNonDealer());
    }

    @Test
    public void setSpecialScoring_BaimanRonTest() {
        gameContext.setTsumo(false);
        response.setSpecialScoreType(SpecialScoringType.BAIMAN);

        util.setSpecialScoring(gameContext, response);

        assertEquals(24000, response.getRonToDealer());
        assertEquals(16000, response.getRonToNonDealer());
    }

    @Test
    public void setSpecialScoring_SanbaimanTsumoTest() {
        gameContext.setTsumo(true);
        response.setSpecialScoreType(SpecialScoringType.SANBAIMAN);

        util.setSpecialScoring(gameContext, response);

        assertEquals(12000, response.getTsumoFromDealer());
        assertEquals(6000, response.getTsumoFromNonDealer());
    }

    @Test
    public void setSpecialScoring_SanbaimanRonTest() {
        gameContext.setTsumo(false);
        response.setSpecialScoreType(SpecialScoringType.SANBAIMAN);

        util.setSpecialScoring(gameContext, response);

        assertEquals(36000, response.getRonToDealer());
        assertEquals(24000, response.getRonToNonDealer());
    }

    @Test
    public void setSpecialScoring_YakumanTsumoTest() {
        gameContext.setTsumo(true);
        response.setSpecialScoreType(SpecialScoringType.YAKUMAN);

        util.setSpecialScoring(gameContext, response);

        assertEquals(16000, response.getTsumoFromDealer());
        assertEquals(8000, response.getTsumoFromNonDealer());
    }

    @Test
    public void setSpecialScoring_YakumanRonTest() {
        gameContext.setTsumo(false);
        response.setSpecialScoreType(SpecialScoringType.YAKUMAN);

        util.setSpecialScoring(gameContext, response);

        assertEquals(48000, response.getRonToDealer());
        assertEquals(32000, response.getRonToNonDealer());
    }

    @Test
    public void testCountDora_2dora() {
        gameContext.getDoraTiles().addAll(Arrays.asList(0, 1));
        gameContext.getHand()
                .addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 33, 33));
        util.countDora(gameContext);
        assertEquals(2, gameContext.getDoraCount());
    }

    @Test
    public void testCountDora_NoDora() {
        gameContext.getDoraTiles().addAll(Arrays.asList(0, 1));
        gameContext.getHand()
                .addAll(Arrays.asList(3, 3, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 33, 33));
        util.countDora(gameContext);
        assertEquals(0, gameContext.getDoraCount());
    }

    @Test
    public void testCountDora_MultipleTiles() {
        gameContext.getDoraTiles().addAll(Arrays.asList(0, 1, 0));
        gameContext.getHand()
                .addAll(Arrays.asList(0, 0, 0, 3, 4, 5, 6, 7, 8, 27, 27, 27, 1, 1));
        util.countDora(gameContext);
        assertEquals(8, gameContext.getDoraCount());
    }
}
