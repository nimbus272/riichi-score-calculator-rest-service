package com.gutterboys.riichi.calculator.yaku.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.ChiitoitsuOrRyanpeiko;

@RunWith(MockitoJUnitRunner.class)
public class ChiitoitsuOrRyanpeikoTest {

    GameContext gameContext;

    ScoreResponse response;

    @Mock
    HandSortUtil sortUtil;

    @InjectMocks
    ChiitoitsuOrRyanpeiko yaku = new ChiitoitsuOrRyanpeiko();

    @Before
    public void setUp() {
        gameContext = new GameContext();
        response = new ScoreResponse();
    }

    @Test
    public void execute_IsChiitoitsuTest() {
        gameContext.getHand()
                .addAll(Arrays.asList(0, 0, 9, 9, 18, 18, 27, 27, 29, 29, 31, 31, 33, 33));

        yaku.execute(gameContext, response);

        Mockito.verify(sortUtil, times(14)).checkChi(anyList(), anyInt(), anyList());
        assertEquals(2, response.getHan());
        assertTrue(response.getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)"));
        assertFalse(response.getQualifiedYaku().contains("Ryanpeiko (Two sets of identical sequences)"));
    }

    @Test
    public void execute_IsNotChiitoitsuTest() {
        gameContext.getHand()
                .addAll(Arrays.asList(0, 0, 0, 18, 18, 18, 27, 27, 29, 29, 31, 31, 33, 33));

        yaku.execute(gameContext, response);

        Mockito.verify(sortUtil, times(0)).checkChi(anyList(), anyInt(), anyList());
        assertEquals(0, response.getHan());
        assertFalse(response.getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)"));
        assertFalse(response.getQualifiedYaku().contains("Ryanpeiko (Two sets of identical sequences)"));
    }

    @Test
    public void execute_IsOpenHandTest() {
        gameContext.getHand()
                .addAll(Arrays.asList(0, 0, 9, 9, 18, 18, 27, 27, 29, 29, 31, 31, 33, 33));
        gameContext.setOpened(true);

        yaku.execute(gameContext, response);

        Mockito.verify(sortUtil, times(0)).checkChi(anyList(), anyInt(), anyList());
        assertEquals(0, response.getHan());
        assertFalse(response.getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)"));
        assertFalse(response.getQualifiedYaku().contains("Ryanpeiko (Two sets of identical sequences)"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void execute_IsRyanpeikoTest() {
        gameContext.getHand()
                .addAll(Arrays.asList(1, 1, 2, 2, 3, 3, 9, 9, 10, 10, 11, 11, 33, 33));

        Mockito.doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            if (args[2] instanceof ArrayList<?>) {
                ArrayList<List<Integer>> possibleChis = (ArrayList<List<Integer>>) args[2];
                possibleChis.add(new ArrayList<Integer>());
            }
            return null;
        }).when(sortUtil).checkChi(anyList(), anyInt(), anyList());

        yaku.execute(gameContext, response);

        Mockito.verify(sortUtil, times(14)).checkChi(anyList(), anyInt(), anyList());
        assertEquals(3, response.getHan());
        assertTrue(response.getQualifiedYaku().contains("Ryanpeiko (Two sets of identical sequences)"));
    }

}
