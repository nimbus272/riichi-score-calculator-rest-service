package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.ToitoiAndSuuanko;

public class ToitoiAndSuuankoTest {
    RiichiCalculatorRequest request;
    PossibleHand possibleHand;
    ToitoiAndSuuanko yaku = new ToitoiAndSuuanko();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
    }

    @Test
    public void ToitoiTest() {

        possibleHand.getMelds().add(Arrays.asList(10, 10, 10));
        possibleHand.getMelds().add(Arrays.asList(11, 11));
        possibleHand.getMelds().add(Arrays.asList(27, 27, 27));
        possibleHand.getMelds().add(Arrays.asList(28, 28, 28, 28));
        possibleHand.getMelds().add(Arrays.asList(30, 30, 30));

        yaku.execute(request, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(
                possibleHand.getQualifiedYaku().contains("Toitoi (All Triplets or Kans)"));
        assertTrue(possibleHand.getHan() == 2);
    }

    @Test
    public void ToitoiTest2() {

        possibleHand.getMelds().add(Arrays.asList(10, 10, 10));
        possibleHand.getMelds().add(Arrays.asList(11, 11));
        possibleHand.getMelds().add(Arrays.asList(27, 27, 27));
        possibleHand.getMelds().add(Arrays.asList(28, 28, 28, 28));
        possibleHand.getMelds().add(Arrays.asList(30, 30, 30));

        request.setTsumo(true);

        yaku.execute(request, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(
                possibleHand.getQualifiedYaku().contains("Suuankou (All concealed triplets)"));
        assertTrue(possibleHand.getHan() == 13);
    }
}
