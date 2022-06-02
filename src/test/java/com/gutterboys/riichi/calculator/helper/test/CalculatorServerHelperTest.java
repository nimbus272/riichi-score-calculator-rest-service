package com.gutterboys.riichi.calculator.helper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.gutterboys.riichi.calculator.helper.CalculatorServiceHelper;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

public class CalculatorServerHelperTest {

    GameContext gameContext;

    ScoreResponse response;

    CalculatorServiceHelper helper = new CalculatorServiceHelper();

    @Before
    public void setUp() {
        gameContext = new GameContext();
        response = new ScoreResponse();
    }

    @Test
    public void determineBaseScoreTest() {
        response.setHan(2);
        response.setFu(40);

        helper.determineBaseScore(response);

        assertTrue(response.getBaseScore() > 0);
        assertEquals(640, response.getBaseScore());
    }

    @Test
    public void applyScoreMultipliers_IsTsumoTest() {
        gameContext.setTsumo(true);
        response.setBaseScore(640);

        helper.applyScoreMultipliers(gameContext, response);

        assertEquals(1300, response.getTsumoToNonDealer());
        assertEquals(700, response.getTsumoToDealer());
    }

    @Test
    public void applyScoreMultipliers_IsNotTsumoTest() {
        gameContext.setTsumo(false);
        response.setBaseScore(640);

        helper.applyScoreMultipliers(gameContext, response);

        assertEquals(3900, response.getRonToDealer());
        assertEquals(2600, response.getRonToNonDealer());
    }

}
