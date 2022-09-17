package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Ittsuu;

public class IttsuuTest {
    GameContext gameContext;

    PossibleHand possibleHand;
    Ittsuu ittsuu = new Ittsuu();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void ittsuuTest() {
        gameContext.setOpened(false);
        possibleHand.getMelds().add(Arrays.asList(28, 28, 28));
        possibleHand.getMelds().add(Arrays.asList(0, 1, 2));
        possibleHand.getMelds().add(Arrays.asList(3, 4, 5));
        possibleHand.getMelds().add(Arrays.asList(6, 7, 8));
        possibleHand.getMelds().add(Arrays.asList(27, 27));
        possibleHand.getTiles().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 28, 28, 28));
        ittsuu.execute(gameContext, possibleHand);
        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Ittsuu (Full Straight)"));
        assertTrue(possibleHand.getHan() == 2);
    }
}
