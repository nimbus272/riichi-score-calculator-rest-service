package com.gutterboys.riichi.calculator.yaku;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

public class Yakuhai implements Yaku {

    @Override
    public void execute(GameContext gameContext, ScoreResponse response) {
        int redCount = 0;
        int greenCount = 0;
        int whiteCount = 0;
        int northCount = 0;
        int westCount = 0;
        int eastCount = 0;
        int southCount = 0;

        for (String tile : gameContext.getHand()) {
            switch (tile) {
                case "rd":
                    redCount++;
                    break;
                case "gd":
                    greenCount++;
                    break;
                case "wd":
                    whiteCount++;
                    break;
                case "nw":
                    northCount++;
                    break;
                case "ww":
                    westCount++;
                    break;
                case "ew":
                    eastCount++;
                    break;
                case "sw":
                    southCount++;
                    break;
                default:
                    break;
            }
        }

        if (redCount > 2) {
            response.setHan(response.getHan() + 1);
            response.getQualifiedYaku().add("Yakuhai (Red Dragons)");
        }

        if (greenCount > 2) {
            response.setHan(response.getHan() + 1);
            response.getQualifiedYaku().add("Yakuhai (Green Dragons)");
        }

        if (whiteCount > 2) {
            response.setHan(response.getHan() + 1);
            response.getQualifiedYaku().add("Yakuhai (White Dragons)");
        }

        if (northCount > 2) {
            response.setHan(response.getHan() + 1);
            response.getQualifiedYaku().add("Yakuhai (North Winds)");
        }

        if (eastCount > 2) {
            response.setHan(response.getHan() + 1);
            response.getQualifiedYaku().add("Yakuhai (East Winds)");
        }

        if (westCount > 2) {
            response.setHan(response.getHan() + 1);
            response.getQualifiedYaku().add("Yakuhai (West Winds)");
        }

        if (southCount > 2) {
            response.setHan(response.getHan() + 1);
            response.getQualifiedYaku().add("Yakuhai (South Winds)");
        }

    }

}
