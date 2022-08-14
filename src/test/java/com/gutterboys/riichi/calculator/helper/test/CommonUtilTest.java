package com.gutterboys.riichi.calculator.helper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.gutterboys.riichi.calculator.helper.CommonUtil;

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
}
