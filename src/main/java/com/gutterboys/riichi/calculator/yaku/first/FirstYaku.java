package com.gutterboys.riichi.calculator.yaku.first;

import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;

import com.gutterboys.riichi.calculator.model.ScoreResponse;
import com.gutterboys.riichi.calculator.yaku.Yaku;

public interface FirstYaku extends Yaku {
    public void execute(GameContext gameContext, ScoreResponse response) throws RiichiCalculatorException;
}
