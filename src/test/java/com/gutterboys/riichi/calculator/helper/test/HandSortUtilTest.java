package com.gutterboys.riichi.calculator.helper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
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
                hand = new ArrayList<Integer>();
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

        @Test
        public void testCheckHonors_HakuPairTest() throws RiichiCalculatorException {
                gameContext.getHand().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 27, 27, 9,
                                9));

                List<Integer> expectedHand = new ArrayList<Integer>(
                                Arrays.asList(-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9,
                                                9));

                List<Integer> expectedMeld = new ArrayList<Integer>(Arrays.asList(27, 27));

                sortUtil.checkHonors(gameContext);

                assertEquals(expectedHand, gameContext.getHand());
                assertTrue(gameContext.getMelds().size() == 1);
                assertTrue(gameContext.getPairCount() == 1);
                assertEquals(expectedMeld, gameContext.getMelds().get(0));

        }

        @Test
        public void testCheckHonors_HakuPonTest() throws RiichiCalculatorException {
                gameContext.getHand().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 9,
                                9));

                List<Integer> expectedHand = new ArrayList<Integer>(
                                Arrays.asList(-1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                                                9));

                List<Integer> expectedMeld = new ArrayList<Integer>(Arrays.asList(27, 27, 27));

                sortUtil.checkHonors(gameContext);

                assertEquals(expectedHand, gameContext.getHand());
                assertTrue(gameContext.getMelds().size() == 1);
                assertTrue(gameContext.getPonCount() == 1);
                assertEquals(expectedMeld, gameContext.getMelds().get(0));

        }

        @Test
        public void testCheckHonors_HakuPonChunPairTest() throws RiichiCalculatorException {
                gameContext.getHand().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 27, 27, 29,
                                29));

                List<Integer> expectedHand = new ArrayList<Integer>(
                                Arrays.asList(-1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8));

                List<List<Integer>> expectedMelds = new ArrayList<List<Integer>>();

                List<Integer> meld1 = new ArrayList<Integer>(Arrays.asList(27, 27, 27));
                List<Integer> meld2 = new ArrayList<Integer>(Arrays.asList(29, 29));

                expectedMelds.add(meld1);
                expectedMelds.add(meld2);

                sortUtil.checkHonors(gameContext);

                assertEquals(expectedHand, gameContext.getHand());
                assertTrue(gameContext.getMelds().size() == 2);
                assertTrue(gameContext.getPairCount() == 1);
                assertTrue(gameContext.getPonCount() == 1);
                assertEquals(expectedMelds, gameContext.getMelds());

        }

        @Test
        public void testCheckHonors_HakuPonChunPairNorthKanTest() throws RiichiCalculatorException {
                gameContext.getHand().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 33, 33, 33, 33, 27, 27, 27, 29,
                                29));

                List<Integer> expectedHand = new ArrayList<Integer>(
                                Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5));

                List<List<Integer>> expectedMelds = new ArrayList<List<Integer>>();

                List<Integer> meld1 = new ArrayList<Integer>(Arrays.asList(33, 33, 33, 33));
                List<Integer> meld2 = new ArrayList<Integer>(Arrays.asList(27, 27, 27));
                List<Integer> meld3 = new ArrayList<Integer>(Arrays.asList(29, 29));

                expectedMelds.add(meld1);
                expectedMelds.add(meld2);
                expectedMelds.add(meld3);

                sortUtil.checkHonors(gameContext);

                assertEquals(expectedHand, gameContext.getHand());
                assertTrue(gameContext.getMelds().size() == 3);
                assertTrue(gameContext.getPairCount() == 1);
                assertTrue(gameContext.getPonCount() == 1);
                assertTrue(gameContext.getKanCount() == 1);
                assertEquals(expectedMelds, gameContext.getMelds());

        }

        @Test
        public void testCheckHonors_MultiplePonsTest() throws RiichiCalculatorException {
                gameContext.getHand().addAll(Arrays.asList(0, 1, 2, 3, 4, 33, 33, 33, 27, 27, 27, 29,
                                29, 29));

                List<Integer> expectedHand = new ArrayList<Integer>(
                                Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4));

                List<List<Integer>> expectedMelds = new ArrayList<List<Integer>>();

                List<Integer> meld1 = new ArrayList<Integer>(Arrays.asList(33, 33, 33));
                List<Integer> meld2 = new ArrayList<Integer>(Arrays.asList(27, 27, 27));
                List<Integer> meld3 = new ArrayList<Integer>(Arrays.asList(29, 29, 29));

                expectedMelds.add(meld1);
                expectedMelds.add(meld2);
                expectedMelds.add(meld3);

                sortUtil.checkHonors(gameContext);

                assertEquals(expectedHand, gameContext.getHand());
                assertTrue(gameContext.getMelds().size() == 3);
                assertTrue(gameContext.getPonCount() == 3);
                assertEquals(expectedMelds, gameContext.getMelds());

        }

        @Test
        public void testCheckHonors_MultipleKansTest() throws RiichiCalculatorException {
                gameContext.getHand().addAll(Arrays.asList(0, 1, 2, 3, 4, 33, 33, 33, 33, 27, 27, 27, 27, 29,
                                29, 29, 29));

                List<Integer> expectedHand = new ArrayList<Integer>(
                                Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4));

                List<List<Integer>> expectedMelds = new ArrayList<List<Integer>>();

                List<Integer> meld1 = new ArrayList<Integer>(Arrays.asList(33, 33, 33, 33));
                List<Integer> meld2 = new ArrayList<Integer>(Arrays.asList(27, 27, 27, 27));
                List<Integer> meld3 = new ArrayList<Integer>(Arrays.asList(29, 29, 29, 29));

                expectedMelds.add(meld1);
                expectedMelds.add(meld2);
                expectedMelds.add(meld3);

                sortUtil.checkHonors(gameContext);

                assertEquals(expectedHand, gameContext.getHand());
                assertTrue(gameContext.getMelds().size() == 3);
                assertTrue(gameContext.getKanCount() == 3);
                assertEquals(expectedMelds, gameContext.getMelds());

        }

        @Test
        public void testCheckHonors_FullySortHandTest() throws RiichiCalculatorException {
                gameContext.getHand().addAll(Arrays.asList(30, 30, 30, 28, 28, 33, 33, 33, 33, 27, 27, 27, 29,
                                29, 29));

                List<Integer> expectedHand = new ArrayList<Integer>(
                                Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1));

                List<List<Integer>> expectedMelds = new ArrayList<List<Integer>>();

                List<Integer> meld1 = new ArrayList<Integer>(Arrays.asList(30, 30, 30));
                List<Integer> meld2 = new ArrayList<Integer>(Arrays.asList(28, 28));
                List<Integer> meld3 = new ArrayList<Integer>(Arrays.asList(33, 33, 33, 33));
                List<Integer> meld4 = new ArrayList<Integer>(Arrays.asList(27, 27, 27));
                List<Integer> meld5 = new ArrayList<Integer>(Arrays.asList(29, 29, 29));

                expectedMelds.add(meld1);
                expectedMelds.add(meld2);
                expectedMelds.add(meld3);
                expectedMelds.add(meld4);
                expectedMelds.add(meld5);

                sortUtil.checkHonors(gameContext);

                assertEquals(expectedHand, gameContext.getHand());
                assertTrue(gameContext.getMelds().size() == 5);
                assertTrue(gameContext.getKanCount() == 1);
                assertTrue(gameContext.getPonCount() == 3);
                assertTrue(gameContext.getPairCount() == 1);
                assertEquals(expectedMelds, gameContext.getMelds());

        }

        @Test
        public void testCheckHonors_NoHonorsTest() throws RiichiCalculatorException {
                gameContext.getHand().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13));

                List<Integer> expectedHand = new ArrayList<Integer>(
                                Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13));

                List<List<Integer>> expectedMelds = new ArrayList<List<Integer>>();

                sortUtil.checkHonors(gameContext);

                assertEquals(expectedHand, gameContext.getHand());
                assertTrue(gameContext.getMelds().size() == 0);
                assertTrue(gameContext.getKanCount() == 0);
                assertTrue(gameContext.getPonCount() == 0);
                assertTrue(gameContext.getPairCount() == 0);
                assertEquals(expectedMelds, gameContext.getMelds());

        }

        @Test(expected = RiichiCalculatorException.class)
        public void testCheckHonors_OneHonorTest() throws RiichiCalculatorException {
                gameContext.getHand().addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 33));

                sortUtil.checkHonors(gameContext);

        }

}
