package com.gutterboys.riichi.calculator.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;

import com.gutterboys.riichi.calculator.model.GameContext;

import ch.qos.logback.classic.Logger;

public class HandSortUtil {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(HandSortUtil.class);

    public void swapFives(GameContext gameContext) {
        LOGGER.debug("Swapping fives...");
        List<String> newTiles = new ArrayList<String>();
        for (int i = 0; i < gameContext.getHand().size(); i++) {
            String tile = gameContext.getHand().get(i);
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
        gameContext.getHand().addAll(newTiles);

        LOGGER.debug("Successfully swapped fives!");
    }

    public void sortTiles(GameContext gameContext) {
        LOGGER.debug("Organizing tiles by index from low to high...");
        gameContext.getHand().sort((o1, o2) -> {
            int i1 = Integer.parseInt(o1);
            int i2 = Integer.parseInt(o2);
            return i1 - i2;
        } );  
    }
  
}