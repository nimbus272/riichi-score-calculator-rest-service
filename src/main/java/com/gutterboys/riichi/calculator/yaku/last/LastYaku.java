package com.gutterboys.riichi.calculator.yaku.last;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.yaku.Yaku;

public interface LastYaku extends Yaku {

    public void execute(RiichiCalculatorRequest request, PossibleHand possibleHand) throws RiichiCalculatorException;
}
