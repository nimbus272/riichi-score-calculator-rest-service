package com.gutterboys.riichi.calculator.helper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.PossibleMelds;

public class CommonUtilTest {

    @Test
    public void removeAndAddPonFromListTest() {
        List<Integer> hand = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9));

        CommonUtil.removeAndAddPonFromList(hand, 0, 3);

        List<Integer> expected = Arrays.asList(-1, -1, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9);

        assertEquals(expected, hand);
    }

    @Test
    public void removeAndAddChiFromListTest() {
        List<Integer> hand = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9));

        CommonUtil.removeAndAddChiFromList(hand, 1);

        List<Integer> expected = Arrays.asList(-1, -1, -1, 0, 0, 0, 4, 5, 6, 7, 8, 9, 9, 9);

        assertEquals(expected, hand);
    }

    @Test
    public void checkMeldTypesAndRemoveDupesTest_Chi() {
        PossibleMelds possibleMelds = new PossibleMelds();
        possibleMelds.getChis().addAll(Arrays.asList(Arrays.asList(1, 2, 3)));
        possibleMelds.getChis().addAll(Arrays.asList(Arrays.asList(1, 2, 3)));
        List<Integer> hand = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9));
        CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds, hand);
        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertEquals(expected, possibleMelds.getChis().get(0));
        assertEquals(1, possibleMelds.getChis().size());
    }

    @Test
    public void checkMeldTypesAndRemoveDupesTest_ChiNoDupes() {
        PossibleMelds possibleMelds = new PossibleMelds();
        possibleMelds.getChis().addAll(Arrays.asList(Arrays.asList(1, 2, 3)));
        possibleMelds.getChis().addAll(Arrays.asList(Arrays.asList(4, 5, 6)));
        List<Integer> hand = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9));
        CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds, hand);
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
        assertEquals(expected, possibleMelds.getChis());
        assertEquals(2, possibleMelds.getChis().size());
    }

    @Test
    public void checkMeldTypesAndRemoveDupesTest_ChiMultipleDupes() {
        PossibleMelds possibleMelds = new PossibleMelds();
        possibleMelds.getChis().addAll(Arrays.asList(Arrays.asList(1, 2, 3)));
        possibleMelds.getChis().addAll(Arrays.asList(Arrays.asList(4, 5, 6)));
        possibleMelds.getChis().addAll(Arrays.asList(Arrays.asList(1, 2, 3)));
        possibleMelds.getChis().addAll(Arrays.asList(Arrays.asList(1, 2, 3)));
        List<Integer> hand = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9));
        CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds, hand);
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
        assertEquals(expected, possibleMelds.getChis());
        assertEquals(2, possibleMelds.getChis().size());
    }

    @Test
    public void checkMeldTypesAndRemoveDupesTest_Pon() {
        PossibleMelds possibleMelds = new PossibleMelds();
        possibleMelds.getPons().addAll(Arrays.asList(Arrays.asList(1, 1, 1)));
        possibleMelds.getPons().addAll(Arrays.asList(Arrays.asList(1, 1, 1)));
        List<Integer> hand = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 1, 1, 4, 5, 6, 7, 8, 9, 9, 9));
        CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds, hand);
        List<Integer> expected = Arrays.asList(1, 1, 1);
        assertEquals(expected, possibleMelds.getPons().get(0));
        assertEquals(1, possibleMelds.getPons().size());
    }

    @Test
    public void checkMeldTypesAndRemoveDupesTest_PonNoDupes() {
        PossibleMelds possibleMelds = new PossibleMelds();

        possibleMelds.getPons().addAll(Arrays.asList(Arrays.asList(1, 1, 1)));
        possibleMelds.getPons().addAll(Arrays.asList(Arrays.asList(2, 2, 2)));
        List<Integer> hand = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 1, 1, 4, 2, 2, 2, 8, 9, 9, 9));
        CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds, hand);
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(1, 1, 1), Arrays.asList(2, 2, 2));
        assertEquals(expected, possibleMelds.getPons());
        assertEquals(2, possibleMelds.getPons().size());
    }

    @Test
    public void checkMeldTypesAndRemoveDupesTest_Kan() {
        PossibleMelds possibleMelds = new PossibleMelds();
        possibleMelds.getKans().addAll(Arrays.asList(Arrays.asList(1, 1, 1, 1)));
        possibleMelds.getKans().addAll(Arrays.asList(Arrays.asList(1, 1, 1, 1)));
        List<Integer> hand = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 1, 1, 1, 4, 5, 6, 7, 8, 9, 9));
        CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds, hand);
        List<Integer> expected = Arrays.asList(1, 1, 1, 1);
        assertEquals(expected, possibleMelds.getKans().get(0));
        assertEquals(1, possibleMelds.getKans().size());
    }

    @Test
    public void checkMeldTypesAndRemoveDupesTest_KanNoDupes() {
        PossibleMelds possibleMelds = new PossibleMelds();
        possibleMelds.getKans().addAll(Arrays.asList(Arrays.asList(1, 1, 1, 1)));
        possibleMelds.getKans().addAll(Arrays.asList(Arrays.asList(2, 2, 2, 2)));
        List<Integer> hand = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 1, 1, 1, 4, 2, 2, 2, 2, 8, 9));
        CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds, hand);
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(1, 1, 1, 1), Arrays.asList(2, 2, 2, 2));
        assertEquals(expected, possibleMelds.getKans());
        assertEquals(2, possibleMelds.getKans().size());
    }

    @Test
    public void checkMeldTypesAndRemoveDupesTest_Pairs() {
        PossibleMelds possibleMelds = new PossibleMelds();
        possibleMelds.getPairs().addAll(Arrays.asList(Arrays.asList(1, 1)));
        possibleMelds.getPairs().addAll(Arrays.asList(Arrays.asList(1, 1)));
        List<Integer> hand = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 1, 1, 4, 5, 6, 7, 8, 9, 9, 9));
        CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds, hand);
        List<Integer> expected = Arrays.asList(1, 1);
        assertEquals(expected, possibleMelds.getPairs().get(0));
        assertEquals(1, possibleMelds.getPairs().size());
    }

    @Test
    public void checkMeldTypesAndRemoveDupesTest_PairsNoDupes() {
        PossibleMelds possibleMelds = new PossibleMelds();
        possibleMelds.getPairs().addAll(Arrays.asList(Arrays.asList(1, 1)));
        possibleMelds.getPairs().addAll(Arrays.asList(Arrays.asList(2, 2)));
        List<Integer> hand = new ArrayList<>(Arrays.asList(0, 0, 0, 1, 1, 1, 4, 2, 2, 2, 8, 9, 9, 9));
        CommonUtil.checkMeldTypesAndRemoveDupes(possibleMelds, hand);
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(1, 1), Arrays.asList(2, 2));
        assertEquals(expected, possibleMelds.getPairs());
        assertEquals(2, possibleMelds.getPairs().size());
    }
}
