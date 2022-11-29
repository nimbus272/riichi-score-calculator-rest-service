package com.gutterboys.riichi.calculator.yaku.last.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.last.ChantaOrJunchan;


public class ChantaOrJunchanTest {
    RiichiCalculatorRequest request;

    PossibleHand possibleHand;

    ChantaOrJunchan junchan = new ChantaOrJunchan();
    ChantaOrJunchan chanta = new ChantaOrJunchan();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
    }

    @Test
    public void junchanTest() throws RiichiCalculatorException {
        possibleHand.getTiles().addAll(Arrays.asList(0, 1, 2, 8, 8, 8, 17, 17, 18, 19, 20, 24, 25, 26));
        possibleHand.getMelds().add(Arrays.asList(0, 1, 2));
        possibleHand.getMelds().add(Arrays.asList(8, 8, 8));
        possibleHand.getMelds().add(Arrays.asList(17, 17));
        possibleHand.getMelds().add(Arrays.asList(18, 19, 20));
        possibleHand.getMelds().add(Arrays.asList(24, 25, 26));

        junchan.execute(request, possibleHand);

        assertEquals(3, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Junchan (Terminal in Each Set)"));

    }

    @Test
    public void chantaTest() throws RiichiCalculatorException {
        possibleHand.getTiles().addAll(Arrays.asList(6, 7, 8, 9, 9, 9, 15, 16, 17, 26, 26, 26, 27, 27));
        possibleHand.getMelds().add(Arrays.asList(6, 7, 8));
        possibleHand.getMelds().add(Arrays.asList(9, 9, 9));
        possibleHand.getMelds().add(Arrays.asList(15, 16, 17));
        possibleHand.getMelds().add(Arrays.asList(26, 26, 26));
        possibleHand.getMelds().add(Arrays.asList(27, 27));

        chanta.execute(request, possibleHand);

        assertEquals(2, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Chanta (Terminal or Honor in Each Set)"));
    }
}
