package com.gutterboys.riichi.calculator.yaku.universal.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.universal.Tsumo;

public class TsumoTest {
    GameContext gameContext;

    PossibleHand possibleHand;

    Tsumo yaku = new Tsumo();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsTsumoTest() {
        gameContext.setTsumo(true);

        yaku.execute(gameContext, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Tsumo"));
    }

    @Test
    public void execute_IsNotTsumoTest1() {
        gameContext.setTsumo(true);
        gameContext.setOpened(true);

        yaku.execute(gameContext, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Tsumo"));
    }

    @Test
    public void execute_IsNotTsumoTest2() {

        yaku.execute(gameContext, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Tsumo"));
    }
}
