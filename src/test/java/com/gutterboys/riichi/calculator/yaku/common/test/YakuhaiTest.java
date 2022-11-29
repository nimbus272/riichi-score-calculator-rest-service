package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Yakuhai;

public class YakuhaiTest {
    RiichiCalculatorRequest request;
    PossibleHand possibleHand;
    CalculatorTracker tracker;
    Yakuhai yaku = new Yakuhai();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void yakuhaiTest() {

        possibleHand.getMelds().add(Arrays.asList(10, 10, 10));
        possibleHand.getMelds().add(Arrays.asList(11, 11));
        possibleHand.getMelds().add(Arrays.asList(27, 27, 27));
        possibleHand.getMelds().add(Arrays.asList(28, 28, 28, 28));
        possibleHand.getMelds().add(Arrays.asList(30, 30, 30));
        request.setSeatWind(30);
        request.setPrevalentWind(30);

        yaku.execute(request, tracker, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() == 1);
        assertTrue(
                possibleHand.getQualifiedYaku().contains("Yakuhai (Triplet or Quad of relevant winds or dragon tiles"));
        assertTrue(possibleHand.getHan() == 4);
    }
}
