package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.yaku.common.Sankantsu;

public class SankantsuTest {
    RiichiCalculatorRequest request;
    PossibleHand possibleHand;
    CalculatorTracker tracker;
    Sankantsu yaku = new Sankantsu();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void sankantsuTest() {
        request.setOpened(false);
        tracker.setKanCount(3);
        yaku.execute(request, tracker, possibleHand);
        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Sankantsu (Three Kans)"));
        assertTrue(possibleHand.getHan() == 2);
    }

}
