package com.gutterboys.riichi.calculator.yaku.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.RinshanKaihou;

public class RinshanKaihouTest {

    GameContext gameContext;

    PossibleHand possibleHand;

    RinshanKaihou yaku = new RinshanKaihou();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsDeadWallDrawTest() {
        gameContext.setDeadWallDraw(true);
        gameContext.setTsumo(true);
        gameContext.setKanCount(1);

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
