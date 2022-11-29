package com.gutterboys.riichi.calculator.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.exception.InvalidHandException;
import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.PossibleMelds;
import com.gutterboys.riichi.calculator.model.ScoreResponse;

import ch.qos.logback.classic.Logger;

@Component
public class HandSortUtil {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(HandSortUtil.class);

    public void swapFives(RiichiCalculatorRequest request) {
        LOGGER.info("Swapping fives...");
        for (int i = 0; i < request.getTiles().size(); i++) {
            Integer tile = request.getTiles().get(i);
            switch (tile) {
                case 34:
                    request.getTiles().remove(request.getTiles().indexOf(tile));
                    request.getTiles().add(0, 4);
                    request.setDoraCount(request.getDoraCount() + 1);
                    continue;
                case 35:
                    request.getTiles().remove(request.getTiles().indexOf(tile));
                    request.getTiles().add(0, 13);
                    request.setDoraCount(request.getDoraCount() + 1);
                    continue;
                case 36:
                    request.getTiles().remove(request.getTiles().indexOf(tile));
                    request.getTiles().add(0, 22);
                    request.setDoraCount(request.getDoraCount() + 1);
                    continue;
                default:
                    continue;
            }
        }
    }

    public void checkChi(List<Integer> hand, int tile, List<List<Integer>> possibleChis, int numberOfDuplicateTiles) {
        if (tile > 26) {
            return;
        }

        if (!RiichiCalculatorConstants.NINES.contains(tile) && !RiichiCalculatorConstants.EIGHTS.contains(tile)
                && hand.contains(tile + 1) && hand.contains(tile + 2)) {
            int tile2 = tile + 1;
            int tile3 = tile + 2;

            long maxInstances = hand.stream().filter(x -> x == tile).count();
            maxInstances = Math.min(maxInstances, hand.stream().filter(x -> x == tile2).count());
            maxInstances = Math.min(maxInstances, hand.stream().filter(x -> x == tile3).count());
            for (int i = 0; i < maxInstances; i++) {
                List<Integer> chi = new ArrayList<Integer>(Arrays.asList(tile, tile + 1, tile + 2));
                possibleChis.add(chi);
            }
        }
        if (!RiichiCalculatorConstants.ONES.contains(tile) && !RiichiCalculatorConstants.TWOS.contains(tile)
                && hand.contains(tile - 1) && hand.contains(tile - 2)) {
            int tile1 = tile - 2;
            int tile2 = tile - 1;
            int tile3 = tile;

            long maxInstances = hand.stream().filter(x -> x == tile1).count();
            maxInstances = Math.min(maxInstances, hand.stream().filter(x -> x == tile2).count());
            maxInstances = Math.min(maxInstances, hand.stream().filter(x -> x == tile3).count());
            for (int i = 0; i < maxInstances; i++) {
                List<Integer> chi = new ArrayList<Integer>(Arrays.asList(tile - 2, tile - 1, tile));
                possibleChis.add(chi);
            }
        }
        if (!RiichiCalculatorConstants.ONES.contains(tile) && !RiichiCalculatorConstants.NINES.contains(tile)
                && hand.contains(tile - 1) && hand.contains(tile + 1)) {
            int tile1 = tile - 1;
            int tile2 = tile;
            int tile3 = tile + 1;

            long maxInstances = hand.stream().filter(x -> x == tile1).count();
            maxInstances = Math.min(maxInstances, hand.stream().filter(x -> x == tile2).count());
            maxInstances = Math.min(maxInstances, hand.stream().filter(x -> x == tile3).count());
            for (int i = 0; i < maxInstances; i++) {
                List<Integer> chi = new ArrayList<Integer>(Arrays.asList(tile - 1, tile, tile + 1));
                possibleChis.add(chi);
            }
        }
    }

    public void checkHonors(RiichiCalculatorRequest request) throws RiichiCalculatorException {
        LOGGER.info("Checking honors...");
        for (int i = 0; i < request.getTiles().size(); i++) {
            int tile = request.getTiles().get(i);
            if (RiichiCalculatorConstants.HONORS.contains(tile)) {
                switch ((int) request.getTiles().stream().filter(x -> x == tile).count()) {
                    case 2:
                        request.getMelds().add(request.getCurrentMeld(),
                                new ArrayList<Integer>(Arrays.asList(tile, tile)));
                        CommonUtil.removeAndAddPonFromList(request.getTiles(), tile, 2);
                        request.setPairCount(request.getPairCount() + 1);
                        break;
                    case 3:
                        request.getMelds().add(request.getCurrentMeld(),
                                new ArrayList<Integer>(Arrays.asList(tile, tile, tile)));
                        CommonUtil.removeAndAddPonFromList(request.getTiles(), tile, 3);
                        request.setPonCount(request.getPonCount() + 1);
                        break;
                    case 4:
                        request.getMelds().add(request.getCurrentMeld(),
                                new ArrayList<Integer>(Arrays.asList(tile, tile, tile, tile)));
                        CommonUtil.removeAndAddPonFromList(request.getTiles(), tile, 4);
                        request.setKanCount(request.getKanCount() + 1);
                        break;
                    default:
                        break;
                }
                request.setCurrentMeld(request.getCurrentMeld() + 1);
            }
        }
    }

    public void reduceHand(RiichiCalculatorRequest request, ScoreResponse response, PossibleMelds possibleMelds)
            throws InvalidHandException {
        int unsortedCount = (int) request.getTiles().stream().filter(x -> x != -1).count();

        for (int i = 0; i < request.getTiles().size(); i++) {
            int tile = request.getTiles().get(i);
            List<List<Integer>> possibleChis = new ArrayList<List<Integer>>();

            if (tile == -1) {
                continue;
            }

            tryGetMeld(request, possibleChis, possibleMelds, tile,
                    (int) request.getTiles().stream().filter(x -> x == tile).count());

        }
        if (unsortedCount > (int) request.getTiles().stream().filter(x -> x != -1).count()) {
            reduceHand(request, response, possibleMelds);
            return;
        }

        if (request.getTiles().stream().filter(x -> x != -1).count() == 0 && request.getMelds().size() == 5) {
            PossibleHand possibleHand = new PossibleHand();
            possibleHand.getTiles().addAll(response.getTiles());
            possibleHand.getMelds().addAll(request.getMelds());
            possibleHand.getMelds().sort((a, b) -> a.get(0) - b.get(0));
            response.getPossibleHands().add(possibleHand);
            return;
        }
        return;
    }

    public void reducePossibleMelds(PossibleMelds possibleMelds, RiichiCalculatorRequest request,
            ScoreResponse response)
            throws InvalidHandException {
        LOGGER.info("Reducing possible melds...");
        List<Integer> reducedHand = new ArrayList<>(request.getTiles());
        List<List<Integer>> lockedMelds = new ArrayList<List<Integer>>(request.getMelds());
        if (possibleMelds.getPairs().size() > 0 && request.getPairCount() == 0) {
            for (int i = 0; i < possibleMelds.getPairs().size(); i++) {
                RiichiCalculatorRequest tempRequest = new RiichiCalculatorRequest();
                List<Integer> tempHand = new ArrayList<>(reducedHand);
                List<Integer> pair = possibleMelds.getPairs().get(i);
                tempHand.remove(pair.get(0));
                tempHand.remove(pair.get(1));
                if (tempRequest.getTiles().size() > 0) {
                    tempRequest.getTiles().clear();
                }
                tempRequest.getTiles().addAll(tempHand);
                // set temp pair count to one so we don't find a pair in reduceHand() and think
                // that is this hand's pair, which would result in 2 pairs in the final
                // evaluation
                // ref [18, 18, 19, 19, 19, 19, 20, 20, 2, 3, 4, 13, 14, 15]
                tempRequest.setPairCount(1);
                try {
                    reduceAndAddHand(request, tempRequest, response, lockedMelds, pair);
                } catch (InvalidHandException e) {
                    // this did not work, try another pair
                    continue;
                }
            }

            // Probably wrong but we'll get there when we get there

        }

        if (possibleMelds.getPons().size() > 0) {
            for (int i = 0; i < possibleMelds.getPons().size(); i++) {
                RiichiCalculatorRequest tempRequest = new RiichiCalculatorRequest();
                List<Integer> tempHand = new ArrayList<Integer>(reducedHand);
                List<Integer> pon = possibleMelds.getPons().get(i);
                tempHand.remove(pon.get(0));
                tempHand.remove(pon.get(1));
                tempHand.remove(pon.get(2));
                tempRequest.getTiles().addAll(tempHand);
                // set temp pair count to one so we don't find a pair in reduceHand() and think
                // that is this hand's pair, which would result in 2 pairs in the final
                // evaluation
                // ref [18, 18, 19, 19, 19, 19, 20, 20, 2, 3, 4, 13, 14, 15]
                try {
                    reduceAndAddHand(request, tempRequest, response, lockedMelds, pon);
                } catch (InvalidHandException e) {
                    // this did not work, try another pair
                    continue;
                }

            }
        }
        if (possibleMelds.getChis().size() > 0) {
            for (int i = 0; i < possibleMelds.getChis().size(); i++) {
                RiichiCalculatorRequest tempRequest = new RiichiCalculatorRequest();
                List<Integer> tempHand = new ArrayList<Integer>(reducedHand);
                List<Integer> chi = possibleMelds.getChis().get(i);
                tempHand.remove(chi.get(0));
                tempHand.remove(chi.get(1));
                tempHand.remove(chi.get(2));
                tempRequest.getTiles().addAll(tempHand);
                // set temp pair count to one so we don't find a pair in reduceHand() and think
                // that is this hand's pair, which would result in 2 pairs in the final
                // evaluation
                // ref [18, 18, 19, 19, 19, 19, 20, 20, 2, 3, 4, 13, 14, 15]
                try {
                    reduceAndAddHand(request, tempRequest, response, lockedMelds, chi);
                } catch (InvalidHandException e) {
                    // this did not work, try another pair
                    continue;
                }
            }
        }

        // probably wrong but we'll get there when we get there
        if (response.getPossibleHands().size() == 0) {
            LOGGER.error("Invalid hand detected in reducePossibleMelds(): {}",
                    request.getTiles());
            throw new InvalidHandException("Invalid hand");
        } else {
            CommonUtil.checkAndRemoveDuplicatePossibleHands(response.getPossibleHands());
            request.getTiles()
                    .removeAll(request.getTiles().stream().filter(x -> x != -1).collect(Collectors.toList()));
            return;
        }

    }

    private void reduceAndAddHand(RiichiCalculatorRequest request, RiichiCalculatorRequest tempRequest,
            ScoreResponse response,
            List<List<Integer>> lockedMelds, List<Integer> meld) throws InvalidHandException {
        PossibleMelds tempPossibleMelds = new PossibleMelds();
        try {
            reduceHand(tempRequest, response, tempPossibleMelds);
        } catch (InvalidHandException e) {
            // this did not work, try another pair
            throw new InvalidHandException("Invalid hand detected.");

        }

        if (tempRequest.getTiles().stream().filter(x -> x != -1).count() == 0) {

            CommonUtil.checkMeldTypesAndRemoveDupes(tempPossibleMelds, tempRequest.getTiles());
            LOGGER.info("Hand determined!");
            CommonUtil.addTempMeldsToRequest(tempRequest, request);
            request.getMelds().add(meld);

            for (int j = 0; j < response.getPossibleHands().size(); j++) {
                if (response.getPossibleHands().get(j).getMelds().size() < 5) {
                    response.getPossibleHands().remove(j);
                }
            }
            PossibleHand possibleHand = new PossibleHand();
            possibleHand.getMelds().addAll(request.getMelds());
            possibleHand.getTiles().addAll(response.getTiles());
            possibleHand.getMelds().sort((a, b) -> a.get(0) - b.get(0));
            response.getPossibleHands().add(possibleHand);
            request.getMelds().clear();
            request.getMelds().addAll(lockedMelds);

        }
    }

    private void tryGetMeld(RiichiCalculatorRequest request, List<List<Integer>> possibleChis,
            PossibleMelds possibleMelds,
            int tile, int numberOfDuplicateTiles) throws InvalidHandException {

        checkChi(request.getTiles(), tile, possibleChis, numberOfDuplicateTiles);

        if (possibleChis.size() == 0) {
            switch (numberOfDuplicateTiles) {
                case 1:
                    LOGGER.error("Invalid hand detected in reduceHand(): {}", request.getTiles());
                    throw new InvalidHandException("Invalid hand -- tile " + tile + " does not fit into meld");
                case 2:
                    if (request.getPairCount() == 0) {
                        // HEY WILL it turns out if we already have a pair and we find 2 of a tile
                        // we cant error out, it could still be valid

                        // ref [10, 10, 11, 11, 13, 13, 14, 14, 30, 30, 10, 22, 23, 24]
                        request.getMelds().add(new ArrayList<Integer>(Arrays.asList(tile, tile)));

                        CommonUtil.removeAndAddPonFromList(request.getTiles(), tile, 2);
                        request.setPairCount(request.getPairCount() + 1);
                        // } else {
                        // throw new InvalidHandException("Invalid hand -- tile " + tile + " does not
                        // fit into meld");
                    }

                    break;
                case 3:
                    // if we have three of a given tile, and that tile does not fit into a chi, it
                    // must be a pon

                    request.getMelds().add(Arrays.asList(tile, tile, tile));
                    CommonUtil.removeAndAddPonFromList(request.getTiles(), tile, 3);
                    request.setPonCount(request.getPonCount() + 1);
                    request.setCurrentMeld(request.getCurrentMeld() + 1);

                    break;
                case 4:
                    request.getMelds().add(new ArrayList<Integer>(Arrays.asList(tile, tile, tile, tile)));
                    CommonUtil.removeAndAddPonFromList(request.getTiles(), tile, 4);
                    request.setKanCount(request.getKanCount() + 1);
                    request.setCurrentMeld(request.getCurrentMeld() + 1);
                    break;
                default:
                    break;

            }
        } else if (possibleChis.size() == 1) {
            switch (numberOfDuplicateTiles) {
                case 1:
                    // if the tile only fits into one meld, that meld must be part of the hand
                    // add those tiles to the current meld, then remove the tiles from the hand and
                    // increment currentMeld
                    CommonUtil.removeAndAddChiFromList(request.getTiles(), possibleChis.get(0).get(0));
                    request.getMelds().add(possibleChis.get(0));
                    request.setChiCount(request.getChiCount() + 1);
                    request.setCurrentMeld(request.getCurrentMeld() + 1);
                    break;
                case 2:
                    if (request.getPairCount() == 1) {
                        // if we already know the pair, and there's only one possible chi, then that chi
                        // must be one of the melds
                        CommonUtil.removeAndAddChiFromList(request.getTiles(), possibleChis.get(0).get(0));
                        request.getMelds().add(possibleChis.get(0));
                        request.setChiCount(request.getChiCount() + 1);
                        request.setCurrentMeld(request.getCurrentMeld() + 1);
                        break;
                    } else {
                        // if we don't know the pair, add both melds to possibleMelds and continue
                        for (int j = 0; j < possibleChis.size(); j++) {
                            possibleMelds.getChis().add(possibleChis.get(j));
                        }
                        possibleMelds.getPairs().add(Arrays.asList(tile, tile));
                        break;
                    }
                default:
                    break;
            }
        } else if (possibleChis.size() == 2) {
            if (possibleChis.get(0).containsAll(possibleChis.get(1)) && request.getPairCount() == 1) {
                // if there are only 2 possible chis and they are the same, and we've found a
                // pair, we assume they are
                // an identical sequence
                if (request.getTiles().stream().filter(x -> x == possibleChis.get(0).get(0)).count() == 2L
                        && request.getTiles().stream().filter(x -> x == possibleChis.get(0).get(1)).count() == 2L
                        && request.getTiles().stream().filter(x -> x == possibleChis.get(0).get(2)).count() == 2L) {
                    CommonUtil.removeAndAddChiFromList(request.getTiles(), possibleChis.get(0).get(0));
                    CommonUtil.removeAndAddChiFromList(request.getTiles(), possibleChis.get(1).get(0));
                    request.getMelds().add(possibleChis.get(0));
                    request.getMelds().add(possibleChis.get(1));
                } else {
                    handleTooManyPossibilities(possibleChis, possibleMelds, numberOfDuplicateTiles, tile);
                }

            } else {
                handleTooManyPossibilities(possibleChis, possibleMelds, numberOfDuplicateTiles, tile);
            }

        } else if (possibleChis.size() == 3 && request.getTiles().size() < 14) {
            if (possibleChis.get(0).containsAll(possibleChis.get(1))
                    && possibleChis.get(1).containsAll(possibleChis.get(2))) {
                if (request.getTiles().stream().filter(x -> x == possibleChis.get(0).get(0)).count() == 3L
                        && request.getTiles().stream().filter(x -> x == possibleChis.get(0).get(1)).count() == 3L
                        && request.getTiles().stream().filter(x -> x == possibleChis.get(0).get(2)).count() == 3L) {
                    CommonUtil.removeAndAddChiFromList(request.getTiles(), possibleChis.get(0).get(0));
                    CommonUtil.removeAndAddChiFromList(request.getTiles(), possibleChis.get(1).get(0));
                    CommonUtil.removeAndAddChiFromList(request.getTiles(), possibleChis.get(2).get(0));
                    request.getMelds().add(possibleChis.get(0));
                    request.getMelds().add(possibleChis.get(1));
                    request.getMelds().add(possibleChis.get(2));
                }

            }
        } else {

            handleTooManyPossibilities(possibleChis, possibleMelds, numberOfDuplicateTiles, tile);

        }
    }

    private void handleTooManyPossibilities(List<List<Integer>> possibleChis, PossibleMelds possibleMelds,
            int numberOfDuplicateTiles, int tile) {
        if (possibleChis.size() > 0) {
            for (int i = 0; i < possibleChis.size(); i++) {
                possibleMelds.getChis().add(possibleChis.get(i));
            }
        }
        switch (numberOfDuplicateTiles) {
            case 2:
                possibleMelds.getPairs().add(Arrays.asList(tile, tile));
                break;
            case 3:
                possibleMelds.getPairs().add(Arrays.asList(tile, tile));
                possibleMelds.getPons().add(Arrays.asList(tile, tile, tile));
                break;
            case 4:
                possibleMelds.getPairs().add(Arrays.asList(tile, tile));
                possibleMelds.getPons().add(Arrays.asList(tile, tile, tile));
                possibleMelds.getKans().add(Arrays.asList(tile, tile, tile, tile));
                break;
            default:
                break;
        }
    }

    public void addOpenMeldsToTiles(RiichiCalculatorRequest request) {
        LOGGER.info("Adding open melds to list of tiles...");
        if (request.getOpenMelds().size() > 0) {
            for (int i = 0; i < request.getOpenMelds().size(); i++) {
                request.getTiles().addAll(request.getOpenMelds().get(i));
            }
        }
    }

}