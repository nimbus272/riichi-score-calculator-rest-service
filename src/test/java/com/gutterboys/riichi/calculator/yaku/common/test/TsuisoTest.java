package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Tsuiso;

public class TsuisoTest {

    GameContext gameContext;

    PossibleHand possibleHand;

    Tsuiso yaku = new Tsuiso();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsTsuisoTest() throws RiichiCalculatorException {

        possibleHand.getTiles().addAll(Arrays.asList(27, 27, 27, 29, 29, 27, 27, 27, 28, 28, 28, 28, 30, 30, 30));

        yaku.execute(gameContext, possibleHand);

        assertEquals(13, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Tsuiso (All Honors)"));
    }

}