package com.gutterboys.riichi.calculator.yaku.universal.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.universal.RinshanKaihou;

public class RinshanKaihouTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;

    RinshanKaihou yaku = new RinshanKaihou();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsDeadWallDrawTest() {
        request.setDeadWallDraw(true);
        request.setTsumo(true);
        request.setKanCount(1);

        yaku.execute(request, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Rinshan Kaihou (Dead Wall Draw)"));
    }

    @Test
    public void execute_IsNotDeadWallDrawTest() {

        yaku.execute(request, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Rinshan Kaihou (Dead Wall Draw)"));
    }
}
