package com.gutterboys.riichi.calculator.yaku.standard;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.Yaku;

public interface StandardStructureYaku extends Yaku {

    public void execute(GameContext gameContext, PossibleHand possibleHand);
}
