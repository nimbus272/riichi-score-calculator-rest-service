package com.gutterboys.riichi.calculator.helper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.model.GameContext;

import java.util.ArrayList;
import java.util.Arrays;

public class HandSortUtilTest {

        GameContext gameContext;

        List<Integer> hand;

        HandSortUtil sortUtil = new HandSortUtil();

        @Before
        public void setUp() {
                gameContext = new GameContext();
                hand = new ArrayList();
        }

        @Test
        public void testSwapFives_1Five() {
                gameContext.getHand()
                                .addAll(Arrays.asList(0, 1, 2, 3, 34, 5, 6, 7, 8, 27, 27, 27, 33, 33));

                List<Integer> expected = Arrays.asList(4, 0, 1, 2, 3, 5, 6, 7, 8, 27, 27, 27, 33,
                                33);

                sortUtil.swapFives(gameContext);

                assertEquals(expected, gameContext.getHand());

        }

        @Test
        public void testSwapFives_NoFives() {
                gameContext.getHand().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 33,
                                33));
                List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 33,
                                33);
                sortUtil.swapFives(gameContext);
                assertEquals(expected, gameContext.getHand());

        }

        @Test
        public void testCheckChi_MiddleTile() {
                hand.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 33,
                                33));
                List<List<Integer>> possibleChis = new ArrayList<List<Integer>>();

                sortUtil.checkChi(hand, 1, possibleChis);

                assertTrue(possibleChis.size() == 2);

        }

        @Test
        public void testCheckChi_FirstTile() {
                hand.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 33,
                                33));
                List<List<Integer>> possibleChis = new ArrayList<List<Integer>>();

                sortUtil.checkChi(hand, 0, possibleChis);

                assertTrue(possibleChis.size() == 1);

        }

        @Test
        public void testCheckChi_3rdTile() {
                hand.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 33,
                                33));
                List<List<Integer>> possibleChis = new ArrayList<List<Integer>>();

                sortUtil.checkChi(hand, 2, possibleChis);

                assertTrue(possibleChis.size() == 3);

        }
}
