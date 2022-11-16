package com.gutterboys.riichi.calculator.yaku.common;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.Yaku;

public interface CommonYaku extends Yaku {

    public void execute(GameContext gameContext, PossibleHand possibleHand);
}
