package com.gutterboys.riichi.calculator.yaku;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

public interface Yaku {

    public void execute(GameContext gameContext, ScoreResponse response);
}
