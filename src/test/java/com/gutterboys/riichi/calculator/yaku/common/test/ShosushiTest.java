package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Shosushi;

public class ShosushiTest {

    GameContext gameContext;

    PossibleHand possibleHand;

    Shosushi yaku = new Shosushi();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsShosushiTest() {

        possibleHand.getMelds().add(Arrays.asList(30, 30, 30));
        possibleHand.getMelds().add(Arrays.asList(33, 33));
        possibleHand.getMelds().add(Arrays.asList(31, 31, 31));
        possibleHand.getMelds().add(Arrays.asList(28, 28, 28, 28));
        possibleHand.getMelds().add(Arrays.asList(32, 32, 32));

        yaku.execute(gameContext, possibleHand);

        assertEquals(13, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku()
                .contains("Shosushi (Four Little Dragons) / Daisushi (Four Big Dragons)"));
    }

    @Test
    public void execute_IsDaisushiTest() {

        possibleHand.getMelds().add(Arrays.asList(30, 30, 30));
        possibleHand.getMelds().add(Arrays.asList(28, 28));
        possibleHand.getMelds().add(Arrays.asList(31, 31, 31));
        possibleHand.getMelds().add(Arrays.asList(30, 30, 30, 30));
        possibleHand.getMelds().add(Arrays.asList(32, 32, 32));

        yaku.execute(gameContext, possibleHand);

        assertEquals(13, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku()
                .contains("Shosushi (Four Little Dragons) / Daisushi (Four Big Dragons)"));
    }

}
