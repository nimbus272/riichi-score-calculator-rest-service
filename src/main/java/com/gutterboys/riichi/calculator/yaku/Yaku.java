package com.gutterboys.riichi.calculator.yaku;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

public interface Yaku {

    public void execute(GameContext gameContext, PossibleHand possibleHand);
}
