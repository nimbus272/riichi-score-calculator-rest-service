package com.gutterboys.riichi.calculator.helper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.model.GameContext;

import java.util.Arrays;

public class HandSortUtilTest {

    GameContext gameContext;

    HandSortUtil sortUtil = new HandSortUtil();

    @Before
    public void setUp() {
        gameContext = new GameContext();
    }

    @Test
    public void testSwapFives_1Five() {
        gameContext.getHand()
                .addAll(Arrays.asList("0", "1", "2", "3", "34", "5", "6", "7", "8", "27", "27", "27", "33", "33"));

        List<String> expected = Arrays.asList("4", "0", "1", "2", "3", "5", "6", "7", "8", "27", "27", "27", "33",
                "33");

        sortUtil.swapFives(gameContext);

        assertEquals(expected, gameContext.getHand());

    }

    @Test
    public void testSwapFives_NoFives() {
        gameContext.getHand().addAll(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "27", "27", "27", "33",
                "33"));
        List<String> expected = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "27", "27", "27", "33",
                "33");
        sortUtil.swapFives(gameContext);
        assertEquals(expected, gameContext.getHand());

    }

    @Test
    public void testSortTiles() {
        gameContext.getHand().addAll(Arrays.asList("9", "8", "7", "6", "5", "4", "3", "2", "1", "0", "0", "0", "0",
                "1"));
        List<String> expected = Arrays.asList("0", "0", "0", "0", "1", "1", "2", "3", "4", "5", "6", "7", "8",
                "9");
        sortUtil.sortTiles(gameContext);
        assertEquals(expected, gameContext.getHand());
    }
}
