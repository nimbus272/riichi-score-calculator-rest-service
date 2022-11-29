package com.gutterboys.riichi.calculator.yaku.universal.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.universal.HaiteiRaoyue;

public class HaiteiRaoyueTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;

    HaiteiRaoyue yaku = new HaiteiRaoyue();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsHaiteiRaoyueTest() {
        request.setLastTileDraw(true);
        request.setTsumo(true);

        yaku.execute(request, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Haitei Raoyue (Last Tile Drawn From Wall)"));
    }

    @Test
    public void execute_IsNotHaiteiRaoyueTest1() {
        request.setLastTileDraw(true);

        yaku.execute(request, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Haitei Raoyue (Last Tile Drawn From Wall)"));
    }

    @Test
    public void execute_IsNotHaiteiRaoyueTest2() {

        yaku.execute(request, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Haitei Raoyue (Last Tile Drawn From Wall)"));
    }
}
