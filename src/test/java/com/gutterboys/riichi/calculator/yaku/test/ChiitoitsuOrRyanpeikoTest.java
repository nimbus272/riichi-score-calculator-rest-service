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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.ChiitoitsuOrRyanpeiko;

@ExtendWith(MockitoExtension.class)
public class ChiitoitsuOrRyanpeikoTest {

    GameContext gameContext;

    ScoreResponse response;

    @Mock
    HandSortUtil sortUtil;

    @InjectMocks
    ChiitoitsuOrRyanpeiko yaku = new ChiitoitsuOrRyanpeiko();

    @BeforeEach
    public void setUp() {
        gameContext = new GameContext();
        response = new ScoreResponse();
    }

    @Test
    public void execute_IsChiitoitsuTest() {
        gameContext.getTiles()
                .addAll(Arrays.asList(0, 0, 9, 9, 18, 18, 27, 27, 29, 29, 31, 31, 33, 33));

        yaku.execute(gameContext, response);

        Mockito.verify(sortUtil, times(14)).checkChi(anyList(), anyInt(), anyList(), anyInt());
        assertTrue(response.getPossibleHands().size() != 0);
        assertEquals(2, response.getPossibleHands().get(0).getHan());
        assertTrue(response.getPossibleHands().get(0).getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)"));
        assertFalse(response.getPossibleHands().get(0).getQualifiedYaku()
                .contains("Ryanpeiko (Two sets of identical sequences)"));
    }

    @Test()
    public void execute_IsNotChiitoitsuTest() {
        gameContext.getTiles()
                .addAll(Arrays.asList(0, 0, 0, 18, 18, 18, 27, 27, 29, 29, 31, 31, 33, 33));

        yaku.execute(gameContext, response);

        assertTrue(response.getPossibleHands().size() == 0);
    }

    @Test
    public void execute_IsOpenHandTest() {
        gameContext.getTiles()
                .addAll(Arrays.asList(0, 0, 9, 9, 18, 18, 27, 27, 29, 29, 31, 31, 33, 33));
        gameContext.setOpened(true);

        yaku.execute(gameContext, response);

        Mockito.verify(sortUtil, times(0)).checkChi(anyList(), anyInt(), anyList(), anyInt());
        assertTrue(response.getPossibleHands().size() == 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void execute_IsRyanpeikoTest() {
        gameContext.getTiles()
                .addAll(Arrays.asList(1, 1, 2, 2, 3, 3, 9, 9, 10, 10, 11, 11, 33, 33));

        Mockito.doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            if (args[2] instanceof ArrayList<?>) {
                ArrayList<List<Integer>> possibleChis = (ArrayList<List<Integer>>) args[2];
                possibleChis.add(new ArrayList<Integer>());
            }
            return null;
        }).when(sortUtil).checkChi(anyList(), anyInt(), anyList(), anyInt());

        yaku.execute(gameContext, response);

        Mockito.verify(sortUtil, times(14)).checkChi(anyList(), anyInt(), anyList(), anyInt());
        assertTrue(response.getPossibleHands().size() != 0);
        assertEquals(3, response.getPossibleHands().get(0).getHan());
        assertFalse(response.getPossibleHands().get(0).getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)"));
        assertTrue(response.getPossibleHands().get(0).getQualifiedYaku()
                .contains("Ryanpeiko (Two sets of identical sequences)"));
    }

    @Test
    public void execute_IsInvalidHandTest() {
        gameContext.getTiles()
                .addAll(Arrays.asList(0, 0, 9, 9, 18, 18, 27, 27, 29, 29, 29, 33, 33, 33));

        yaku.execute(gameContext, response);

        Mockito.verify(sortUtil, times(0)).checkChi(anyList(), anyInt(), anyList(), anyInt());
        assertTrue(response.getPossibleHands().size() == 0);

    }

}
