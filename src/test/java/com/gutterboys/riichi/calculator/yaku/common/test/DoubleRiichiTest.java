package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.DoubleRiichi;

public class DoubleRiichiTest {

    GameContext gameContext;

    PossibleHand possibleHand;

    DoubleRiichi yaku = new DoubleRiichi();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsDoubleRiichiTest() {
        gameContext.setDoubleRiichi(true);

        yaku.execute(gameContext, possibleHand);

        assertEquals(2, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Double Riichi"));
    }

    @Test
    public void execute_IsNotDoubleRiichiTest1() {
        gameContext.setDoubleRiichi(true);
        gameContext.setOpened(true);

        yaku.execute(gameContext, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Double Riichi"));
    }

    @Test
    public void execute_IsNotDoubleRiichiTest2() {

        yaku.execute(gameContext, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Double Riichi"));
    }
}