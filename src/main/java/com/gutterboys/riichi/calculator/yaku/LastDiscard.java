package com.gutterboys.riichi.calculator.yaku;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

@Component
public class LastDiscard implements Yaku {

    @Override
    public void execute(GameContext gameContext, ScoreResponse response) {
        if (gameContext.isLastDiscard()) {
            response.setHan(response.getHan() + 1);
            response.getQualifiedYaku().add("Houtei Raoyui (Last Discarded Tile)");
        }

    }

}
