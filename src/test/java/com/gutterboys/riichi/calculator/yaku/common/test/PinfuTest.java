package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Pinfu;

public class PinfuTest {

    RiichiCalculatorRequest request;
    PossibleHand possibleHand;
    CalculatorTracker tracker;

    Pinfu yaku = new Pinfu();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void execute_PinfuTest() {
        request.setWinningTile(5);
        request.setPrevalentWind(30);
        request.setSeatWind(30);
        possibleHand.getTiles().addAll(Arrays.asList(1, 2, 3, 3, 4, 5, 5, 6, 7, 6, 7, 8, 9, 9));
        possibleHand.getMelds().add(Arrays.asList(1, 2, 3));
        possibleHand.getMelds().add(Arrays.asList(3, 4, 5));
        possibleHand.getMelds().add(Arrays.asList(5, 6, 7));
        possibleHand.getMelds().add(Arrays.asList(6, 7, 8));
        possibleHand.getMelds().add(Arrays.asList(9, 9));

        yaku.execute(request, tracker, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Pinfu (No-points Hand)"));
    }
}
