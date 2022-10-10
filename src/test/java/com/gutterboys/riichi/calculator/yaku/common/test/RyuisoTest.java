package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Ryuiso;

public class RyuisoTest {

    GameContext gameContext;

    PossibleHand possibleHand;

    Ryuiso yaku = new Ryuiso();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsRyuisoTest() throws RiichiCalculatorException {

        possibleHand.getTiles().addAll(Arrays.asList(19, 19, 19, 20, 20, 21, 21, 21, 23, 23, 23, 25, 25, 25));

        yaku.execute(gameContext, possibleHand);

        assertEquals(13, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Ryuiso (All Green)"));
    }

}
