package com.gutterboys.riichi.calculator.yaku.standard.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.standard.Honitsu;

public class HonitsuTest {
    GameContext gameContext;
    PossibleHand possibleHand;
    Honitsu yaku = new Honitsu();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void honitsuTest() {
        gameContext.setOpened(false);
        possibleHand.getTiles().addAll(Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17, 27, 27, 27, 30, 30));

        yaku.execute(gameContext, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Honitsu (Half flush)"));
        assertTrue(possibleHand.getHan() == 3);
    }

    @Test
    public void honitsuTest2() {
        gameContext.setOpened(true);
        possibleHand.getTiles().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 30, 30));

        yaku.execute(gameContext, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Honitsu (Half flush)"));
        assertTrue(possibleHand.getHan() == 2);
    }
}
