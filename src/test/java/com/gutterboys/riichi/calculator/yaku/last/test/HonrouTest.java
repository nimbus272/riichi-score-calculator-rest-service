package com.gutterboys.riichi.calculator.yaku.last.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.last.Honrou;

public class HonrouTest {
    GameContext gameContext;

    PossibleHand possibleHand;

    Honrou yaku = new Honrou();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsHonrouTest() throws RiichiCalculatorException {
        possibleHand.getTiles().addAll(Arrays.asList(0, 0, 0, 8, 8, 8, 27, 27, 27, 9,9,9, 31, 31));
        possibleHand.getQualifiedYaku().add("Toitoi (All Triplets or Kans)");
        yaku.execute(gameContext, possibleHand);

        assertEquals(2, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Honrou (All Terminals and Honors)"));
    }
}
