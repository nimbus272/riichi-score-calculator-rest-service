package com.gutterboys.riichi.calculator.yaku.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.KokushiMusou;

public class KokushiMusouTest {

    GameContext gameContext;

    ScoreResponse response;

    KokushiMusou yaku = new KokushiMusou();

    @Before
    public void setUp() {
        gameContext = new GameContext();
        response = new ScoreResponse();
    }

    @Test
    public void execute_isKokushiMusouTest_noDouble() {
        gameContext.getHand()
                .addAll(Arrays.asList(0, 8, 9, 17, 18, 26, 27, 28, 29,
                        30, 31, 32, 33, 33));
        gameContext.setWinningTile(0);
        yaku.execute(gameContext, response);

        assertEquals(13, response.getHan());
        assertTrue(response.getQualifiedYaku().contains("Kokushi Musou (Thirteen Orphans)"));
    }

    @Test
    public void execute_isKokushiMusouTest_Double() {
        gameContext.getHand()
                .addAll(Arrays.asList(0, 8, 9, 17, 18, 26, 27, 28, 29,
                        30, 31, 32, 33, 33));
        gameContext.setWinningTile(33);
        yaku.execute(gameContext, response);

        assertEquals(13, response.getHan());
        assertTrue(response.getQualifiedYaku().contains("Kokushi Musou (Thirteen Orphans)"));
        assertTrue(response.isDoubleYakuman());
    }

    @Test
    public void execute_isKokushiMusouTest_invalid() {
        gameContext.getHand()
                .addAll(Arrays.asList(0, 8, 9, 17, 18, 26, 5, 28, 29, 30,
                        31, 32, 33, 33));
        gameContext.setWinningTile(0);
        yaku.execute(gameContext, response);

        assertEquals(0, response.getHan());
        assertFalse(response.getQualifiedYaku().contains("Kokushi Musou (Thirteen Orphans)"));
    }

    @Test
    public void execute_isKokushiMusouTest_invalid2() {
        gameContext.getHand()
                .addAll(Arrays.asList(0, 8, 9, 17, 18, 26, 27, 28, 29,
                        30, 31, 33, 33, 33));
        gameContext.setWinningTile(0);
        yaku.execute(gameContext, response);

        assertEquals(0, response.getHan());
        assertFalse(response.getQualifiedYaku().contains("Kokushi Musou (Thirteen Orphans)"));
    }
}
