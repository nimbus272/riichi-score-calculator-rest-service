package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Pinfu;

public class PinfuTest {

    GameContext gameContext;
    PossibleHand possibleHand;

    Pinfu yaku = new Pinfu();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_PinfuTest() {
        gameContext.setWinningTile(5);
        gameContext.setPrevalentWind(30);
        gameContext.setSeatWind(30);
        possibleHand.getTiles().addAll(Arrays.asList(1, 2, 3, 3, 4, 5, 5, 6, 7, 6, 7, 8, 9, 9));
        possibleHand.getMelds().add(Arrays.asList(1, 2, 3));
        possibleHand.getMelds().add(Arrays.asList(3, 4, 5));
        possibleHand.getMelds().add(Arrays.asList(5, 6, 7));
        possibleHand.getMelds().add(Arrays.asList(6, 7, 8));
        possibleHand.getMelds().add(Arrays.asList(9, 9));

        yaku.execute(gameContext, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Pinfu (No-points Hand)"));
    }
}
