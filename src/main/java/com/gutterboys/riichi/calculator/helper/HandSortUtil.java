package com.gutterboys.riichi.calculator.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.model.GameContext;

import ch.qos.logback.classic.Logger;

public class HandSortUtil {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(HandSortUtil.class);

    public void swapFives(GameContext gameContext) {
        LOGGER.debug("Swapping fives...");
        List<Integer> newTiles = new ArrayList<Integer>();
        for (int i = 0; i < gameContext.getHand().size(); i++) {
            Integer tile = gameContext.getHand().get(i);
            switch (tile) {
                case 34:
                    gameContext.getHand().remove(gameContext.getHand().indexOf(tile));
                    gameContext.getHand().add(0, 4);
                    gameContext.setDoraCount(gameContext.getDoraCount() + 1);
                    break;
                case 35:
                    gameContext.getHand().remove(gameContext.getHand().indexOf(tile));
                    gameContext.getHand().add(0, 13);
                    gameContext.setDoraCount(gameContext.getDoraCount() + 1);
                    break;
                case 36:
                    gameContext.getHand().remove(gameContext.getHand().indexOf(tile));
                    gameContext.getHand().add(0, 22);
                    gameContext.setDoraCount(gameContext.getDoraCount() + 1);
                    break;
                default:
                    break;
            }
        }
        gameContext.getHand().addAll(newTiles);

        LOGGER.debug("Successfully swapped fives!");
    }

    public void checkChi(List<Integer> hand, int tile, List<List<Integer>> possibleChis) {
        if (tile > 26) {
            return;
        }

        if (!RiichiCalculatorConstants.NINES.contains(tile) && !RiichiCalculatorConstants.EIGHTS.contains(tile)
                && hand.contains(tile + 1) && hand.contains(tile + 2)) {
            List<Integer> chi = new ArrayList<Integer>(Arrays.asList(tile, tile + 1, tile + 2));
            possibleChis.add(chi);
        }
        if (!RiichiCalculatorConstants.ONES.contains(tile) && !RiichiCalculatorConstants.TWOS.contains(tile)
                && hand.contains(tile - 1) && hand.contains(tile - 2)) {
            List<Integer> chi = new ArrayList<Integer>(Arrays.asList(tile - 2, tile - 1, tile));
            possibleChis.add(chi);
        }
        if (!RiichiCalculatorConstants.ONES.contains(tile) && !RiichiCalculatorConstants.NINES.contains(tile)
                && hand.contains(tile - 1) && hand.contains(tile + 1)) {
            List<Integer> chi = new ArrayList<Integer>(Arrays.asList(tile - 1, tile, tile + 1));
            possibleChis.add(chi);
        }
    }

}