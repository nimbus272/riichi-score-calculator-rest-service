package com.gutterboys.riichi.calculator.yaku.first.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.helper.HandSortUtil;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorResponse;
import com.gutterboys.riichi.calculator.yaku.first.ChiitoitsuOrRyanpeiko;

@ExtendWith(MockitoExtension.class)
public class ChiitoitsuOrRyanpeikoTest {

    RiichiCalculatorRequest request;

    RiichiCalculatorResponse response;
    CalculatorTracker tracker;

    @Mock
    HandSortUtil sortUtil;

    @InjectMocks
    ChiitoitsuOrRyanpeiko yaku = new ChiitoitsuOrRyanpeiko();

    @BeforeEach
    public void setUp() {
        request = new RiichiCalculatorRequest();
        response = new RiichiCalculatorResponse();
        tracker = new CalculatorTracker();
    }

    @Test
    public void execute_IsChiitoitsuTest() throws RiichiCalculatorException {
        tracker.getTiles()
                .addAll(Arrays.asList(0, 0, 9, 9, 18, 18, 27, 27, 29, 29, 31, 31, 33, 33));

        yaku.execute(request, tracker, response);

        Mockito.verify(sortUtil, times(14)).checkChi(anyList(), anyInt(), anyList(), anyInt());
        assertTrue(response.getPossibleHands().size() != 0);
        assertEquals(2, response.getPossibleHands().get(0).getHan());
        assertTrue(response.getPossibleHands().get(0).getQualifiedYaku().contains("Chiitoitsu (Seven Pairs)"));
        assertFalse(response.getPossibleHands().get(0).getQualifiedYaku()
                .contains("Ryanpeiko (Two sets of identical sequences)"));
    }

    @Test()
    public void execute_IsNotChiitoitsuTest() throws RiichiCalculatorException {
        request.getTiles()
                .addAll(Arrays.asList(0, 0, 0, 18, 18, 18, 27, 27, 29, 29, 31, 31, 33, 33));

        yaku.execute(request, tracker, response);

        assertTrue(response.getPossibleHands().size() == 0);
    }

    @Test
    public void execute_IsOpenHandTest() throws RiichiCalculatorException {
        request.getTiles()
                .addAll(Arrays.asList(0, 0, 9, 9, 18, 18, 27, 27, 29, 29, 31, 31, 33, 33));
        request.setOpened(true);

        yaku.execute(request, tracker, response);

        Mockito.verify(sortUtil, times(0)).checkChi(anyList(), anyInt(), anyList(), anyInt());
        assertTrue(response.getPossibleHands().size() == 0);
    }

    @Test
    public void execute_IsInvalidHandTest() throws RiichiCalculatorException {
        request.getTiles()
                .addAll(Arrays.asList(0, 0, 9, 9, 18, 18, 27, 27, 29, 29, 29, 33, 33, 33));

        yaku.execute(request, tracker, response);

        Mockito.verify(sortUtil, times(0)).checkChi(anyList(), anyInt(), anyList(), anyInt());
        assertTrue(response.getPossibleHands().size() == 0);

    }

}
