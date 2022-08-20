package com.gutterboys.riichi.calculator.yaku.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Ippatsu;

public class IppatsuTest {

    GameContext gameContext;

    PossibleHand possibleHand;

    Ippatsu yaku = new Ippatsu();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }

    @Test
    public void execute_IsIppatsuTest() {
        gameContext.setIppatsu(true);

        yaku.execute(gameContext, possibleHand);

        assertEquals(1, possibleHand.getHan());
        assertTrue(possibleHand.getQualifiedYaku().contains("Ippatsu (One-shot)"));
    }

    @Test
    public void execute_IsNotIppatsuTest1() {
        gameContext.setIppatsu(true);
        gameContext.setOpened(true);

        yaku.execute(gameContext, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Ippatsu (One-shot)"));
    }

    @Test
    public void execute_IsNotIppatsuTest2() {
        gameContext.setIppatsu(true);
        gameContext.setKanCount(1);

        yaku.execute(gameContext, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Ippatsu (One-shot)"));
    }

    @Test
    public void execute_IsNotIppatsuTest3() {

        yaku.execute(gameContext, possibleHand);

        assertEquals(0, possibleHand.getHan());
        assertFalse(possibleHand.getQualifiedYaku().contains("Ippatsu (One-shot)"));
    }
}
