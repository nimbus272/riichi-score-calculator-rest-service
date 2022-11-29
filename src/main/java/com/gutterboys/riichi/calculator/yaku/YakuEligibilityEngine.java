package com.gutterboys.riichi.calculator.yaku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorResponse;
import com.gutterboys.riichi.calculator.yaku.common.Chinitsu;
import com.gutterboys.riichi.calculator.yaku.common.CommonYaku;
import com.gutterboys.riichi.calculator.yaku.common.Honitsu;
import com.gutterboys.riichi.calculator.yaku.first.FirstYaku;
import com.gutterboys.riichi.calculator.yaku.last.LastYaku;
import com.gutterboys.riichi.calculator.yaku.universal.UniversalYaku;

@Component
public class YakuEligibilityEngine {

    // autowiring a set of an interface will get every implementation of that
    // interface and place it in the set
    @Autowired
    Set<UniversalYaku> universalYaku;

    @Autowired
    Set<FirstYaku> firstYaku;

    @Autowired
    Set<CommonYaku> commonYaku;

    @Autowired
    Set<LastYaku> lastYaku;

    public void executeUniversal(RiichiCalculatorRequest request, CalculatorTracker tracker,
            PossibleHand possibleHand) {
        for (UniversalYaku yaku : universalYaku) {
            yaku.execute(request, tracker, possibleHand);
        }

    }

    public void executeFirst(RiichiCalculatorRequest request, CalculatorTracker tracker,
            RiichiCalculatorResponse response) throws RiichiCalculatorException {
        for (FirstYaku yaku : firstYaku) {
            yaku.execute(request, tracker, response);
        }
    }

    public void executeCommon(RiichiCalculatorRequest request, CalculatorTracker tracker, PossibleHand possibleHand) {
        for (CommonYaku yaku : commonYaku) {
            yaku.execute(request, tracker, possibleHand);
        }
    }

    public void executeLast(RiichiCalculatorRequest request, PossibleHand possibleHand)
            throws RiichiCalculatorException {

        for (LastYaku yaku : lastYaku) {
            yaku.execute(request, possibleHand);
        }
    }

    public void executeSpecialSevenPairs(RiichiCalculatorRequest request, CalculatorTracker tracker,
            PossibleHand possibleHand) {
        Set<CommonYaku> sevenPairsYaku = new HashSet<CommonYaku>();
        sevenPairsYaku.addAll(Arrays.asList(new Chinitsu(), new Honitsu()));
        for (CommonYaku yaku : sevenPairsYaku) {
            yaku.execute(request, tracker, possibleHand);
        }
    }
}
