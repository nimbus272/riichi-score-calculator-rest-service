package com.gutterboys.riichi.calculator.yaku.last;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.Yaku;

public interface LastYaku extends Yaku {

    public void execute(GameContext gameContext, PossibleHand possibleHand) throws RiichiCalculatorException;
}
