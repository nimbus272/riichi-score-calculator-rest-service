package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.ChurenPoto;

public class ChurenPotoTest {

    RiichiCalculatorRequest request;
    PossibleHand possibleHand;
    CalculatorTracker tracker;

    ChurenPoto yaku = new ChurenPoto();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void execute_ChurenPotoTest() {
        // possibleHand.getTiles().addAll(Arrays.asList(0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8,
        // 8, 8, 2));
        // possibleHand.getTiles().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 2, 2,
        // 2, 5, 5));
        possibleHand.getTiles().addAll(Arrays.asList(0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8,
                8, 8, 0));
        request.setWinningTile(2);

        yaku.execute(request, tracker, possibleHand);

        assertEquals(13, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Churen Poto (Nine Gates)"));
    }
}
