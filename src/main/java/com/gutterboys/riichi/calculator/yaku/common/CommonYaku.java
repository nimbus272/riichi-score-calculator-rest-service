package com.gutterboys.riichi.calculator.yaku.common;

import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.yaku.Yaku;

public interface CommonYaku extends Yaku {

    public void execute(RiichiCalculatorRequest request, CalculatorTracker tracker, PossibleHand possibleHand);
}
