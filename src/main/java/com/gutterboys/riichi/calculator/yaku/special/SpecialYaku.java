package com.gutterboys.riichi.calculator.yaku.special;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;

import com.gutterboys.riichi.calculator.model.ScoreResponse;

public interface SpecialYaku {
    public void execute(GameContext gameContext, ScoreResponse response) throws RiichiCalculatorException;
}
