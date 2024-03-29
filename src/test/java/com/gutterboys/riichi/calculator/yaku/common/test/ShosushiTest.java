package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.yaku.common.Shosushi;

public class ShosushiTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;

    CalculatorTracker tracker;

    Shosushi yaku = new Shosushi();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void execute_IsShosushiTest() {

        possibleHand.getMelds().add(Arrays.asList(30, 30, 30));
        possibleHand.getMelds().add(Arrays.asList(33, 33));
        possibleHand.getMelds().add(Arrays.asList(31, 31, 31));
        possibleHand.getMelds().add(Arrays.asList(28, 28, 28, 28));
        possibleHand.getMelds().add(Arrays.asList(32, 32, 32));

        yaku.execute(request, tracker, possibleHand);

        assertEquals(13, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku()
                .contains("Shosushi (Four Little Dragons) / Daisushi (Four Big Dragons)"));
    }

    @Test
    public void execute_IsDaisushiTest() {

        possibleHand.getMelds().add(Arrays.asList(30, 30, 30));
        possibleHand.getMelds().add(Arrays.asList(28, 28));
        possibleHand.getMelds().add(Arrays.asList(31, 31, 31));
        possibleHand.getMelds().add(Arrays.asList(30, 30, 30, 30));
        possibleHand.getMelds().add(Arrays.asList(32, 32, 32));

        yaku.execute(request, tracker, possibleHand);

        assertEquals(13, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku()
                .contains("Shosushi (Four Little Dragons) / Daisushi (Four Big Dragons)"));
    }

}
