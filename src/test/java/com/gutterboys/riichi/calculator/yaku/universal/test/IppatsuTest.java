package com.gutterboys.riichi.calculator.yaku.universal.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.universal.Ippatsu;

public class IppatsuTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;
    CalculatorTracker tracker;

    Ippatsu yaku = new Ippatsu();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
        tracker = new CalculatorTracker();
    }

    @Test
    public void execute_IsIppatsuTest() {
        request.setIppatsu(true);

        yaku.execute(request, tracker, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Ippatsu (One-shot)"));
    }

    @Test
    public void execute_IsNotIppatsuTest1() {
        request.setIppatsu(true);
        request.setOpened(true);

        yaku.execute(request, tracker, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Ippatsu (One-shot)"));
    }

    @Test
    public void execute_IsNotIppatsuTest2() {
        request.setIppatsu(true);
        tracker.setKanCount(1);

        yaku.execute(request, tracker, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Ippatsu (One-shot)"));
    }

    @Test
    public void execute_IsNotIppatsuTest3() {

        yaku.execute(request, tracker, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Ippatsu (One-shot)"));
    }
}
