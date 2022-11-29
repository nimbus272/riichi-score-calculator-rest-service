package com.gutterboys.riichi.calculator.yaku.universal.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.universal.DoubleRiichi;

public class DoubleRiichiTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;
    CalculatorTracker tracker;

    DoubleRiichi yaku = new DoubleRiichi();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void execute_IsDoubleRiichiTest() {
        request.setDoubleRiichi(true);

        yaku.execute(request, tracker, possibleHand);

        assertEquals(2, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Double Riichi"));
    }

    @Test
    public void execute_IsNotDoubleRiichiTest1() {
        request.setDoubleRiichi(true);
        request.setOpened(true);

        yaku.execute(request, tracker, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Double Riichi"));
    }

    @Test
    public void execute_IsNotDoubleRiichiTest2() {

        yaku.execute(request, tracker, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Double Riichi"));
    }
}