package com.gutterboys.riichi.calculator.yaku.universal.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.universal.Riichi;

public class RiichiTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;
    CalculatorTracker tracker;

    Riichi yaku = new Riichi();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void execute_IsRiichiTest() {
        request.setRiichi(true);

        yaku.execute(request, tracker, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Riichi"));
    }

    @Test
    public void execute_IsNotRiichiTest1() {
        request.setRiichi(true);
        request.setOpened(true);

        yaku.execute(request, tracker, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Riichi"));
    }

    @Test
    public void execute_IsNotRiichiTest2() {

        yaku.execute(request, tracker, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Riichi"));
    }
}
