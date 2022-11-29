package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Chinroto;

public class ChinrotoTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;

    Chinroto yaku = new Chinroto();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsChinrotoTest() throws RiichiCalculatorException {

        possibleHand.getTiles().addAll(Arrays.asList(0, 0, 0, 8, 8, 9, 9, 9, 17, 17, 17, 17, 18, 18, 18));

        yaku.execute(request, possibleHand);

        assertEquals(13, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Chinroto (All Terminals)"));
    }
}
