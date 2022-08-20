package com.gutterboys.riichi.calculator.yaku.standard.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.standard.Ippeiko;

public class IppeikoTest {

    GameContext gameContext;

    PossibleHand possibleHand;

    Ippeiko ippeiko = new Ippeiko();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void ippeikoTest() {
        gameContext.setOpened(false);
        possibleHand.getMelds().add(Arrays.asList(10, 10, 10));
        possibleHand.getMelds().add(Arrays.asList(11, 11));
        possibleHand.getMelds().add(Arrays.asList(12, 13, 14));
        possibleHand.getMelds().add(Arrays.asList(12, 13, 14));
        possibleHand.getMelds().add(Arrays.asList(27, 27, 27));

        ippeiko.execute(gameContext, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Ippeiko (Two Identical Sequences)"));
        assertTrue(possibleHand.getHan() == 1);
    }
}