package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.yaku.common.Daisangen;

public class DaisangenTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;

    CalculatorTracker tracker;

    Daisangen yaku = new Daisangen();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void execute_IsDaisangenTest() throws RiichiCalculatorException {

        possibleHand.getMelds().add(Arrays.asList(27, 27, 27));
        possibleHand.getMelds().add(Arrays.asList(11, 11));
        possibleHand.getMelds().add(Arrays.asList(27, 27, 27));
        possibleHand.getMelds().add(Arrays.asList(28, 28, 28, 28));
        possibleHand.getMelds().add(Arrays.asList(30, 30, 30));

        yaku.execute(request, tracker, possibleHand);

        assertEquals(13, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Daisangen (Big Three Dragons)"));
    }

}
