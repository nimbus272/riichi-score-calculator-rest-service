package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.HouteiRaoyui;

public class HouteiRaoyuiTest {

    GameContext gameContext;

    PossibleHand possibleHand;

    HouteiRaoyui yaku = new HouteiRaoyui();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsHouteiRaoyuiTest() {
        gameContext.setLastDiscard(true);

        yaku.execute(gameContext, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Houtei Raoyui (Last Discarded Tile)"));
    }

    @Test
    public void execute_IsNotHouteiRaoyuiTest1() {
        gameContext.setLastTileDraw(true);
        gameContext.setTsumo(true);

        yaku.execute(gameContext, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Houtei Raoyui (Last Discarded Tile)"));
    }

    @Test
    public void execute_IsNotHouteiRaoyuiTest2() {

        yaku.execute(gameContext, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Houtei Raoyui (Last Discarded Tile)"));
    }
}
