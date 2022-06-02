package com.gutterboys.riichi.calculator.yaku;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

public class YakuEligibilityEngine {

    // autowiring a set of an interface will get every implementation of that
    // interface and place it in the set
    @Autowired
    Set<Yaku> allYaku;

    public void execute(GameContext gameContext, ScoreResponse response) {
        for (Yaku yaku : allYaku) {
            yaku.execute(gameContext, response);
        }

    }

}
