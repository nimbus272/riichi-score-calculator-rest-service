package com.gutterboys.riichi.calculator.yaku.standard.test;
import static org.junit.jupiter.api.Assertions.assertTrue;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

import com.gutterboys.riichi.calculator.yaku.standard.Sankantsu;

public class SankantsuTest {
    GameContext gameContext;
    PossibleHand possibleHand;
    Sankantsu sankantsu = new Sankantsu();
    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        possibleHand = new PossibleHand();
    }
    @Test
    public void sankantsuTest() {
        gameContext.setOpened(false);
        gameContext.setKanCount(3);
        sankantsu.execute(gameContext, possibleHand);
        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Sankantsu (Three Kans)"));
        assertTrue(possibleHand.getHan() == 2);
    }
    
}
