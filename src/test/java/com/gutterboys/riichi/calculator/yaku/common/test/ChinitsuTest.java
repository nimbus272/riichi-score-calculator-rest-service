package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.yaku.common.Chinitsu;

public class ChinitsuTest {

    RiichiCalculatorRequest request;
    PossibleHand possibleHand;
    CalculatorTracker tracker;
    Chinitsu yaku = new Chinitsu();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void chinitsuTest() {
        request.setOpened(false);
        possibleHand.getTiles().addAll(Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17, 27, 27, 27, 30, 30));

        yaku.execute(request, tracker, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() == 0);
        assertFalse(possibleHand.getQualifiedYaku().contains("Chinitsu (Full Flush)"));
        assertTrue(possibleHand.getHan() == 0);
    }

    @Test
    public void chinitsuTest2() {
        request.setOpened(true);
        possibleHand.getTiles().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0, 8, 8));

        yaku.execute(request, tracker, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Chinitsu (Full Flush)"));
        assertTrue(possibleHand.getHan() == 5);
    }

    @Test
    public void chinitsuTest3() {

        possibleHand.getTiles().addAll(Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17, 9, 9, 9, 17, 17));

        yaku.execute(request, tracker, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Chinitsu (Full Flush)"));
        assertTrue(possibleHand.getHan() == 6);
    }

}
