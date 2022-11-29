package com.gutterboys.riichi.calculator.yaku.universal.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.universal.Chankan;

public class ChankanTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;

    Chankan yaku = new Chankan();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsChankanTest() {
        request.setRobbedKan(true);

        yaku.execute(request, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Chankan (Robbed Kan)"));
    }

    @Test
    public void execute_IsNotChankanTest1() {
        request.setRobbedKan(true);
        request.setTsumo(true);

        yaku.execute(request, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Chankan (Robbed Kan)"));
    }

    @Test
    public void execute_IsNotChankanTest2() {

        yaku.execute(request, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Chankan (Robbed Kan)"));
    }
}
