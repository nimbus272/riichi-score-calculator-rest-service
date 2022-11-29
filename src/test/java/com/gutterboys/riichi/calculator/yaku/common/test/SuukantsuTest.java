package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Suukantsu;

public class SuukantsuTest {

    RiichiCalculatorRequest request;

    PossibleHand possibleHand;

    Suukantsu yaku = new Suukantsu();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsSuukantsuTest() throws RiichiCalculatorException {

        possibleHand.getMelds().add(Arrays.asList(27, 27, 27, 27));
        possibleHand.getMelds().add(Arrays.asList(29, 29));
        possibleHand.getMelds().add(Arrays.asList(31, 31, 31, 31));
        possibleHand.getMelds().add(Arrays.asList(28, 28, 28, 28));
        possibleHand.getMelds().add(Arrays.asList(30, 30, 30, 30));

        yaku.execute(request, possibleHand);

        assertEquals(13, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Suukantsu (Four Kans)"));
    }

}
