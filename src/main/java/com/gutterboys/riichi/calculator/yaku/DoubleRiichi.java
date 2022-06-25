package com.gutterboys.riichi.calculator.yaku;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

@Component
public class DoubleRiichi implements Yaku {

    @Override
    public void execute(GameContext gameContext, ScoreResponse response) {
        if (gameContext.isDoubleRiichi() && !gameContext.isOpened()) {
            response.setHan(response.getHan() + 2);
            response.getQualifiedYaku().add("Double Riichi");
        }
    }
}
