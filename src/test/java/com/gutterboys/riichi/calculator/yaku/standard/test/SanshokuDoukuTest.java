package com.gutterboys.riichi.calculator.yaku.standard.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

import com.gutterboys.riichi.calculator.yaku.standard.SanshokuDouku;

public class SanshokuDoukuTest {
    GameContext gameContext;

    PossibleHand possibleHand;
    SanshokuDouku sanshokuDouku = new SanshokuDouku();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void sanshokuDoujunTest() {
        gameContext.setOpened(false);
        possibleHand.getMelds().add(Arrays.asList(10, 10, 10));
        possibleHand.getMelds().add(Arrays.asList(0, 0, 0));
        possibleHand.getMelds().add(Arrays.asList(9, 9, 9));
        possibleHand.getMelds().add(Arrays.asList(18, 18, 18));
        possibleHand.getMelds().add(Arrays.asList(27, 27));
        sanshokuDouku.execute(gameContext, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Sanshoku Douku (Same Triplet in All Suits)"));
        assertTrue(possibleHand.getHan() == 2);
    }
}
