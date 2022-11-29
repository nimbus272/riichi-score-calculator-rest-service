package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.SanshokuDouku;

public class SanshokuDoukuTest {
    RiichiCalculatorRequest request;

    PossibleHand possibleHand;
    SanshokuDouku sanshokuDouku = new SanshokuDouku();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
    }

    @Test
    public void sanshokuDoujunTest() {
        request.setOpened(false);
        possibleHand.getMelds().add(Arrays.asList(10, 10, 10));
        possibleHand.getMelds().add(Arrays.asList(0, 0, 0));
        possibleHand.getMelds().add(Arrays.asList(9, 9, 9));
        possibleHand.getMelds().add(Arrays.asList(18, 18, 18));
        possibleHand.getMelds().add(Arrays.asList(27, 27));
        sanshokuDouku.execute(request, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Sanshoku Douku (Same Triplet in All Suits)"));
        assertTrue(possibleHand.getHan() == 2);
    }
}
