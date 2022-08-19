package com.gutterboys.riichi.calculator.yaku.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.DeadWallDraw;

public class DeadWallDrawTest {

    GameContext gameContext;

    PossibleHand possibleHand;

    DeadWallDraw yaku = new DeadWallDraw();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsDeadWallDrawTest() {
        gameContext.setDeadWallDraw(true);

        yaku.execute(gameContext, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Rinshan Kaihou (Dead Wall Draw)"));
    }

    @Test
    public void execute_IsNotDeadWallDrawTest() {

        yaku.execute(gameContext, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Rinshan Kaihou (Dead Wall Draw)"));
    }
}
