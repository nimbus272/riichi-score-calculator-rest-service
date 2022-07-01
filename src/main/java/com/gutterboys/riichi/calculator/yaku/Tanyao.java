package com.gutterboys.riichi.calculator.yaku;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

public class Tanyao implements Yaku {

    @Override
    public void execute(GameContext gameContext, ScoreResponse response) {
        boolean isTanyao = true;
        for (String tile : gameContext.getHand()) {
            if (tile.startsWith("1") || tile.startsWith("9") || tile.matches("[a-zA-Z]+")) {
                isTanyao = false;
                return;
            }
        }
        if (isTanyao) {
            response.setHan(response.getHan() + 1);
            response.getQualifiedYaku().add("Tanyao (All Simples)");
            return;
        }
    }
}
