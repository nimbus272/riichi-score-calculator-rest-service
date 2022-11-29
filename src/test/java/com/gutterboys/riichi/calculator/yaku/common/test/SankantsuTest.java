package com.gutterboys.riichi.calculator.yaku.common.test;
import static org.junit.jupiter.api.Assertions.assertTrue;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.common.Sankantsu;

public class SankantsuTest {
    RiichiCalculatorRequest request;
    PossibleHand possibleHand;
    Sankantsu sankantsu = new Sankantsu();
    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        possibleHand = new PossibleHand();
    }
    @Test
    public void sankantsuTest() {
        request.setOpened(false);
        request.setKanCount(3);
        sankantsu.execute(request, possibleHand);
        assertTrue(possibleHand.getQualifiedYaku().size() > 0);
        assertTrue(possibleHand.getQualifiedYaku().contains("Sankantsu (Three Kans)"));
        assertTrue(possibleHand.getHan() == 2);
    }
    
}
