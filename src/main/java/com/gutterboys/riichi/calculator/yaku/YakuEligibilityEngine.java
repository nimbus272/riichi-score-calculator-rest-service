package com.gutterboys.riichi.calculator.yaku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
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

    public void executeUniversal(GameContext gameContext, PossibleHand possibleHand) {
        for (UniversalYaku yaku : universalYaku) {
            yaku.execute(gameContext, possibleHand);
        }

    }

    public void executeFirst(GameContext gameContext, ScoreResponse response) throws RiichiCalculatorException {
        for (FirstYaku yaku : firstYaku) {
            yaku.execute(gameContext, response);
        }
    }

    public void executeCommon(GameContext gameContext, PossibleHand possibleHand) {
        for (CommonYaku yaku : commonYaku) {
            yaku.execute(gameContext, possibleHand);
        }
    }

    public void executeLast(GameContext gameContext, PossibleHand possibleHand) throws RiichiCalculatorException {

        for (LastYaku yaku : lastYaku) {
            yaku.execute(gameContext, possibleHand);
        }
    }

    public void executeSpecialSevenPairs(GameContext gameContext, PossibleHand possibleHand) {
        Set<CommonYaku> sevenPairsYaku = new HashSet<CommonYaku>();
        sevenPairsYaku.addAll(Arrays.asList(new Chinitsu(), new Honitsu()));
        for (CommonYaku yaku : sevenPairsYaku) {
            yaku.execute(gameContext, possibleHand);
        }
    }
}
