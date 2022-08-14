package com.gutterboys.riichi.calculator.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.exception.InvalidHandException;
import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleMelds;

import ch.qos.logback.classic.Logger;

@Component
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
                    continue;
                case 35:
                    gameContext.getHand().remove(gameContext.getHand().indexOf(tile));
                    gameContext.getHand().add(0, 13);
                    gameContext.setDoraCount(gameContext.getDoraCount() + 1);
                    continue;
                case 36:
                    gameContext.getHand().remove(gameContext.getHand().indexOf(tile));
                    gameContext.getHand().add(0, 22);
                    gameContext.setDoraCount(gameContext.getDoraCount() + 1);
                    continue;
                default:
                    continue;
            }
        }
        gameContext.getHand().addAll(newTiles);
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

    public void checkHonors(GameContext gameContext) throws RiichiCalculatorException {
        for (int i = 0; i < gameContext.getHand().size(); i++) {
            int tile = gameContext.getHand().get(i);
            if (RiichiCalculatorConstants.HONORS.contains(tile)) {
                switch ((int) gameContext.getHand().stream().filter(x -> x == tile).count()) {
                    case 1:
                        LOGGER.error("Invalid hand detected in checkHonors(): {}", gameContext.getHand());
                        throw new RiichiCalculatorException("Invalid hand");
                    case 2:
                        gameContext.getMelds().add(gameContext.getCurrentMeld(),
                                new ArrayList<Integer>(Arrays.asList(tile, tile)));
                        CommonUtil.removeAndAddPonFromList(gameContext.getHand(), tile, 2);
                        gameContext.setPairCount(gameContext.getPairCount() + 1);
                        break;
                    case 3:
                        gameContext.getMelds().add(gameContext.getCurrentMeld(),
                                new ArrayList<Integer>(Arrays.asList(tile, tile, tile)));
                        CommonUtil.removeAndAddPonFromList(gameContext.getHand(), tile, 3);
                        gameContext.setPonCount(gameContext.getPonCount() + 1);
                        break;
                    case 4:
                        gameContext.getMelds().add(gameContext.getCurrentMeld(),
                                new ArrayList<Integer>(Arrays.asList(tile, tile, tile, tile)));
                        CommonUtil.removeAndAddPonFromList(gameContext.getHand(), tile, 4);
                        gameContext.setKanCount(gameContext.getKanCount() + 1);
                        break;
                    default:
                        break;
                }
                gameContext.setCurrentMeld(gameContext.getCurrentMeld() + 1);
            }
        }
    }

    public PossibleMelds reduceHand(GameContext gameContext) throws InvalidHandException {
        PossibleMelds possibleMelds = new PossibleMelds();
        int unsortedCount = (int) gameContext.getHand().stream().filter(x -> x != -1).count();

        for (int i = 0; i < gameContext.getHand().size(); i++) {
            int tile = gameContext.getHand().get(i);
            List<List<Integer>> possibleChis = new ArrayList<List<Integer>>();

            if (tile == -1) {
                continue;
            }

            switch ((int) gameContext.getHand().stream().filter(x -> x == tile).count()) {
                case 1:
                    // we know this tile cannot be a pair/pon/kan, so it must be part of a chi
                    checkChi(gameContext.getHand(), tile, possibleChis);
                    if (possibleChis.size() == 0) {
                        // if no chi can be made, the hand is invalid
                        LOGGER.error("Invalid hand detected in reduceHand(): {}", gameContext.getHand());
                        throw new InvalidHandException("Invalid hand");
                    } else if (possibleChis.size() == 1) {
                        // if the tile only fits into one meld, that meld must be part of the hand
                        // add those tiles to the current meld, then remove the tiles from the hand and
                        // increment currentMeld
                        CommonUtil.removeAndAddChiFromList(gameContext.getHand(), possibleChis.get(0).get(0));
                        gameContext.getHand().add(0, -1);
                        gameContext.getHand().add(0, -1);
                        gameContext.getHand().add(0, -1);
                        gameContext.getMelds().get(gameContext.getCurrentMeld()).addAll(possibleChis.get(0));
                        gameContext.setChiCount(gameContext.getChiCount() + 1);
                        gameContext.setCurrentMeld(gameContext.getCurrentMeld() + 1);
                        break;

                    }
                    // if a tile fits into more than one meld, don't do anything other than adding
                    // those melds to possibleMelds obj. for now, we just want to identify all tiles
                    // that only fit into one meld.
                    else {
                        for (int j = 0; j < possibleChis.size(); i++) {
                            possibleMelds.getChis().add(possibleChis.get(i));
                        }

                        break;
                    }

                case 2:
                    checkChi(gameContext.getHand(), tile, possibleChis);
                    if (possibleChis.size() == 0) {
                        // if we have two of a given tile, and that tile does not fit into a chi, it
                        // must be a pair.
                        if (gameContext.getPairCount() > 0) {
                            // if we already have a pair the hand is invalid
                            LOGGER.error("Invalid hand detected in reduceHand(): {}", gameContext.getHand());
                            throw new InvalidHandException("Invalid Hand");
                        }
                        gameContext.getMelds().get(gameContext.getCurrentMeld()).add(tile);
                        gameContext.getMelds().get(gameContext.getCurrentMeld()).add(tile);
                        CommonUtil.removeAndAddPonFromList(gameContext.getHand(), tile, 2);
                        gameContext.setPairCount(gameContext.getPairCount() + 1);
                        break;

                    } else if (possibleChis.size() == 1) {
                        if (gameContext.getPairCount() == 1) {
                            // if we already know the pair, and there's only one possible chi, then that chi
                            // must be one of the melds
                            CommonUtil.removeAndAddChiFromList(gameContext.getHand(), possibleChis.get(0).get(0));
                            gameContext.getHand().add(0, -1);
                            gameContext.getHand().add(0, -1);
                            gameContext.getHand().add(0, -1);
                            gameContext.getMelds().get(gameContext.getCurrentMeld()).addAll(possibleChis.get(0));
                            gameContext.setChiCount(gameContext.getChiCount() + 1);
                            gameContext.setCurrentMeld(gameContext.getCurrentMeld() + 1);

                        }
                        break;
                    } else {
                        // if we don't know the pair, add both melds to possibleMelds and continue
                        for (int j = 0; j < possibleChis.size(); i++) {
                            possibleMelds.getChis().add(possibleChis.get(i));
                        }
                        possibleMelds.getPairs().add(Arrays.asList(tile, tile));
                        break;
                    }

                case 3:
                    checkChi(gameContext.getHand(), tile, possibleChis);

                    if (possibleChis.size() == 0) {
                        // if we have three of a given tile, and that tile does not fit into a chi, it
                        // must be a pon

                        gameContext.getMelds().get(gameContext.getCurrentMeld())
                                .addAll(Arrays.asList(tile, tile, tile));
                        CommonUtil.removeAndAddPonFromList(gameContext.getHand(), tile, 3);
                        gameContext.setPonCount(gameContext.getPonCount() + 1);
                        gameContext.setCurrentMeld(gameContext.getCurrentMeld() + 1);

                        break;

                    } else {
                        for (int j = 0; j < possibleChis.size(); i++) {
                            possibleMelds.getChis().add(possibleChis.get(i));
                        }
                        possibleMelds.getPons().add(Arrays.asList(tile, tile, tile));
                        break;
                    }

                case 4:
                    checkChi(gameContext.getHand(), tile, possibleChis);

                    if (possibleChis.size() == 0) {
                        gameContext.getMelds().get(gameContext.getCurrentMeld())
                                .addAll(new ArrayList<Integer>(Arrays.asList(tile, tile, tile, tile)));
                        CommonUtil.removeAndAddPonFromList(gameContext.getHand(), tile, 4);
                        gameContext.setKanCount(gameContext.getKanCount() + 1);
                        gameContext.setCurrentMeld(gameContext.getCurrentMeld() + 1);
                        break;

                    } else {
                        for (int j = 0; j < possibleChis.size(); i++) {
                            possibleMelds.getChis().add(possibleChis.get(i));
                        }
                        possibleMelds.getKans().add(Arrays.asList(tile, tile, tile, tile));
                        break;
                    }

                default:
                    break;
            }
            if (unsortedCount > (int) gameContext.getHand().stream().filter(x -> x != -1).count()) {
                reduceHand(gameContext);
            }
            if (unsortedCount == (int) gameContext.getHand().stream().filter(x -> x != -1).count()) {
                return possibleMelds;
            }

        }
        return possibleMelds;
    }

}