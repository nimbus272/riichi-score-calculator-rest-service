package com.gutterboys.riichi.calculator.yaku.first;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;

import com.gutterboys.riichi.calculator.model.RiichiCalculatorResponse;
import com.gutterboys.riichi.calculator.yaku.Yaku;

public interface FirstYaku extends Yaku {
    public void execute(RiichiCalculatorRequest request, RiichiCalculatorResponse response) throws RiichiCalculatorException;
}
