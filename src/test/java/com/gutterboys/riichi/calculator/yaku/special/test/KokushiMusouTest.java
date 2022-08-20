package com.gutterboys.riichi.calculator.yaku.special.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.special.KokushiMusou;

public class KokushiMusouTest {

    GameContext gameContext;

    ScoreResponse response;

    KokushiMusou yaku = new KokushiMusou();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        response = new ScoreResponse();
    }

    @Test
    public void execute_isKokushiMusouTest_noDouble() {
        gameContext.getTiles()
                .addAll(Arrays.asList(0, 8, 9, 17, 18, 26, 27, 28, 29,
                        30, 31, 32, 33, 33));
        gameContext.setWinningTile(0);
        yaku.execute(gameContext, response);

        assertTrue(response.getPossibleHands().size() == 1);
        assertEquals(13, response.getPossibleHands().get(0).getHan());
        assertTrue(response.getPossibleHands().get(0).getQualifiedYaku().contains("Kokushi Musou (Thirteen Orphans)"));
    }

    @Test
    public void execute_isKokushiMusouTest_Double() {
        gameContext.getTiles()
                .addAll(Arrays.asList(0, 8, 9, 17, 18, 26, 27, 28, 29,
                        30, 31, 32, 33, 33));
        gameContext.setWinningTile(33);
        yaku.execute(gameContext, response);

        assertTrue(response.getPossibleHands().size() == 1);
        assertEquals(13, response.getPossibleHands().get(0).getHan());
        assertTrue(response.getPossibleHands().get(0).getQualifiedYaku().contains("Kokushi Musou (Thirteen Orphans)"));
        assertTrue(response.getPossibleHands().get(0).isDoubleYakuman());
    }

    @Test
    public void execute_isKokushiMusouTest_invalid() {
        gameContext.getTiles()
                .addAll(Arrays.asList(0, 8, 9, 17, 18, 26, 5, 28, 29, 30,
                        31, 32, 33, 33));
        gameContext.setWinningTile(0);
        yaku.execute(gameContext, response);

        assertTrue(response.getPossibleHands().size() == 0);

    }

    @Test
    public void execute_isKokushiMusouTest_invalid2() {
        gameContext.getTiles()
                .addAll(Arrays.asList(0, 8, 9, 17, 18, 26, 27, 28, 29,
                        30, 31, 33, 33, 33));
        gameContext.setWinningTile(0);
        yaku.execute(gameContext, response);

        assertTrue(response.getPossibleHands().size() == 0);
    }
}
