package com.gutterboys.riichi.calculator.yaku.universal.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.universal.Tsumo;

public class TsumoTest {
    RiichiCalculatorRequest request;

    PossibleHand possibleHand;
    CalculatorTracker tracker;

    Tsumo yaku = new Tsumo();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void execute_IsTsumoTest() {
        request.setTsumo(true);

        yaku.execute(request, tracker, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Tsumo"));
    }

    @Test
    public void execute_IsNotTsumoTest1() {
        request.setTsumo(true);
        request.setOpened(true);

        yaku.execute(request, tracker, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Tsumo"));
    }

    @Test
    public void execute_IsNotTsumoTest2() {

        yaku.execute(request, tracker, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Tsumo"));
    }
}
