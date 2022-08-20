package com.gutterboys.riichi.calculator.yaku;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.common.CommonYaku;
import com.gutterboys.riichi.calculator.yaku.special.SpecialYaku;

@Component
public class YakuEligibilityEngine {

    // autowiring a set of an interface will get every implementation of that
    // interface and place it in the set
    @Autowired
    Set<CommonYaku> yakuAllCompatible;

    @Autowired
    Set<SpecialYaku> specialYaku;

    public void executeAllCompatible(GameContext gameContext, PossibleHand possibleHand) {
        for (CommonYaku yaku : yakuAllCompatible) {
            yaku.execute(gameContext, possibleHand);
        }

    }

    public void executeSpecial(GameContext gameContext, ScoreResponse response) throws RiichiCalculatorException {
        for (SpecialYaku yaku : specialYaku) {
            yaku.execute(gameContext, response);
        }
    }

}
