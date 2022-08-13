package com.gutterboys.riichi.calculator.helper;

import org.slf4j.LoggerFactory;

import com.gutterboys.riichi.calculator.model.GameContext;

import ch.qos.logback.classic.Logger;

public class HandSortUtil {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(HandSortUtil.class);

    public void swapFives(GameContext gameContext) {
        LOGGER.debug("Swapping fives...");
        for (String tile : gameContext.getHand()) {
            switch (tile) {
                case "34":
                    gameContext.getHand().remove(gameContext.getHand().indexOf(tile));
                    gameContext.getHand().add(0, "4");
                    gameContext.setDoraCount(gameContext.getDoraCount() + 1);
                    break;
                case "35":
                    gameContext.getHand().remove(gameContext.getHand().indexOf(tile));
                    gameContext.getHand().add(0, "13");
                    gameContext.setDoraCount(gameContext.getDoraCount() + 1);
                    break;
                case "36":
                    gameContext.getHand().remove(gameContext.getHand().indexOf(tile));
                    gameContext.getHand().add(0, "22");
                    gameContext.setDoraCount(gameContext.getDoraCount() + 1);
                    break;
                default:
                    break;
            }
        }

        LOGGER.debug("Successfully swapped fives!");
    }

}
