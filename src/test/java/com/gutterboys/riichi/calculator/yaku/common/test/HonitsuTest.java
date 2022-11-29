package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Honitsu;

public class HonitsuTest {
    RiichiCalculatorRequest request;
    PossibleHand possibleHand;
    Honitsu yaku = new Honitsu();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
    }

    @Test
    public void honitsuTest() {
        request.setOpened(false);
        possibleHand.getTiles().addAll(Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17, 27, 27, 27, 30, 30));

        yaku.execute(request, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Honitsu (Half flush)"));
        assertTrue(possibleHand.getHan() == 3);
    }

    @Test
    public void honitsuTest2() {
        request.setOpened(true);
        possibleHand.getTiles().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 30, 30));

        yaku.execute(request, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Honitsu (Half flush)"));
        assertTrue(possibleHand.getHan() == 2);
    }
}
