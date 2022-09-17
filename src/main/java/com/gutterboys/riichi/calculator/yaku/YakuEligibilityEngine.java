package com.gutterboys.riichi.calculator.yaku;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.common.CommonYaku;
import com.gutterboys.riichi.calculator.yaku.first.FirstYaku;
import com.gutterboys.riichi.calculator.yaku.universal.UniversalYaku;

@Component
public class YakuEligibilityEngine {

    // autowiring a set of an interface will get every implementation of that
    // interface and place it in the set
    @Autowired
    Set<UniversalYaku> yakuAllCompatible;

    @Autowired
    Set<FirstYaku> specialYaku;

    @Autowired
    Set<CommonYaku> standardYaku;

    public void executeAllCompatible(GameContext gameContext, PossibleHand possibleHand) {
        for (UniversalYaku yaku : yakuAllCompatible) {
            yaku.execute(gameContext, possibleHand);
        }

    }

    public void executeSpecial(GameContext gameContext, ScoreResponse response) throws RiichiCalculatorException {
        for (FirstYaku yaku : specialYaku) {
            yaku.execute(gameContext, response);
        }
    }

    public void executeStandard(GameContext gameContext, PossibleHand possibleHand) {
        for (CommonYaku yaku : standardYaku) {
            yaku.execute(gameContext, possibleHand);
        }
    }

}
