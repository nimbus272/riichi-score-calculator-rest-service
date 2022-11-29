package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Ippeiko;

public class IppeikoTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;

    Ippeiko ippeiko = new Ippeiko();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
    }

    @Test
    public void ippeikoTest() {
        request.setOpened(false);
        possibleHand.getMelds().add(Arrays.asList(10, 10, 10));
        possibleHand.getMelds().add(Arrays.asList(11, 11));
        possibleHand.getMelds().add(Arrays.asList(12, 13, 14));
        possibleHand.getMelds().add(Arrays.asList(12, 13, 14));
        possibleHand.getMelds().add(Arrays.asList(27, 27, 27));

        ippeiko.execute(request, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Ippeiko (Two Identical Sequences)"));
        assertTrue(possibleHand.getHan() == 1);
    }
}
