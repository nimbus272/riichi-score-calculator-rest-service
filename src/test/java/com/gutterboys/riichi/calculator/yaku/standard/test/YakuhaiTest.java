package com.gutterboys.riichi.calculator.yaku.standard.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.standard.Yakuhai;

public class YakuhaiTest {
    GameContext gameContext;
    PossibleHand possibleHand;
    Yakuhai yaku = new Yakuhai();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void yakuhaiTest() {

        possibleHand.getMelds().add(Arrays.asList(10, 10, 10));
        possibleHand.getMelds().add(Arrays.asList(11, 11));
        possibleHand.getMelds().add(Arrays.asList(27, 27, 27));
        possibleHand.getMelds().add(Arrays.asList(28, 28, 28, 28));
        possibleHand.getMelds().add(Arrays.asList(30, 30, 30));
        gameContext.setSeatWind(30);
        gameContext.setPrevalentWind(30);

        yaku.execute(gameContext, possibleHand);

        assertTrue(possibleHand.getQualifiedYaku().size() == 1);
        assertTrue(
                possibleHand.getQualifiedYaku().contains("Yakuhai (Triplet or Quad of relevant winds or dragon tiles"));
        assertTrue(possibleHand.getHan() == 4);
    }
}
