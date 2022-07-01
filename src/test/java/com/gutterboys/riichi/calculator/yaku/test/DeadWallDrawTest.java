package com.gutterboys.riichi.calculator.yaku.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.DeadWallDraw;

public class DeadWallDrawTest {

    GameContext gameContext;

    ScoreResponse response;

    DeadWallDraw yaku = new DeadWallDraw();

    @Before
    public void setUp() {
        gameContext = new GameContext();
        response = new ScoreResponse();
    }

    @Test
    public void execute_IsDeadWallDrawTest() {
        gameContext.setDeadWallDraw(true);

        yaku.execute(gameContext, response);

        assertEquals(1, response.getHan());
        assertTrue(response.getQualifiedYaku().contains("Rinshan Kaihou (Dead Wall Draw)"));
    }

    @Test
    public void execute_IsNotDeadWallDrawTest() {

        yaku.execute(gameContext, response);

        assertEquals(0, response.getHan());
        assertFalse(response.getQualifiedYaku().contains("Rinshan Kaihou (Dead Wall Draw)"));
    }
}
