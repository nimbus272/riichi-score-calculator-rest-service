package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.yaku.common.Tanyao;

public class TanyaoTest {
    RiichiCalculatorRequest request;
    PossibleHand possibleHand;
    CalculatorTracker tracker;
    Tanyao yaku = new Tanyao();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void tanyaoTest() {
        request.setOpened(false);
        possibleHand.getTiles().addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 10, 11, 12, 13, 14, 15, 20, 20));
        yaku.execute(request, tracker, possibleHand);
        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Tanyao (No Terminal or Honor Tiles)"));
        assertTrue(possibleHand.getHan() == 1);
    }
}
