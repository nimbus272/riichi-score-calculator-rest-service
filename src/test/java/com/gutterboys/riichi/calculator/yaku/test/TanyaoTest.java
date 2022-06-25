package com.gutterboys.riichi.calculator.yaku.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.Tanyao;

public class TanyaoTest {

    GameContext gameContext;

    ScoreResponse response;

    Tanyao tanyao = new Tanyao();

    @Before
    public void setUp() {
        gameContext = new GameContext();
        response = new ScoreResponse();
    }

    @Test
    public void execute_IsTanyaoTest() {
        List<String> hand = new ArrayList<String>();
        hand.addAll(Arrays.asList("2p", "2p", "2p", "3m", "4m", "5m", "6m", "7m", "8m", "6s", "6s", "6s", "7s", "8s"));

        gameContext.getHand().addAll(hand);

        tanyao.execute(gameContext, response);

        assertEquals(1, response.getHan());
        assertTrue(response.getQualifiedYaku().contains("Tanyao"));
    }

    @Test
    public void execute_HasHonorTileTest() {
        List<String> hand = new ArrayList<String>();
        hand.addAll(Arrays.asList("2p", "2p", "2p", "3m", "4m", "5m", "6m", "7m", "8m", "rd", "rd", "6s", "7s", "8s"));

        gameContext.getHand().addAll(hand);

        tanyao.execute(gameContext, response);

        assertEquals(0, response.getHan());
        assertFalse(response.getQualifiedYaku().contains("Tanyao"));
    }

    @Test
    public void execute_HasOneTest() {
        List<String> hand = new ArrayList<String>();
        hand.addAll(Arrays.asList("1p", "2p", "3p", "3m", "4m", "5m", "6m", "7m", "8m", "rd", "rd", "6s", "7s", "8s"));

        gameContext.getHand().addAll(hand);

        tanyao.execute(gameContext, response);

        assertEquals(0, response.getHan());
        assertFalse(response.getQualifiedYaku().contains("Tanyao"));
    }

    @Test
    public void execute_HasNineTest() {
        List<String> hand = new ArrayList<String>();
        hand.addAll(Arrays.asList("1p", "2p", "3p", "3m", "4m", "5m", "6m", "7m", "8m", "rd", "rd", "7s", "8s", "9s"));

        gameContext.getHand().addAll(hand);

        tanyao.execute(gameContext, response);

        assertEquals(0, response.getHan());
        assertFalse(response.getQualifiedYaku().contains("Tanyao"));
    }

}
