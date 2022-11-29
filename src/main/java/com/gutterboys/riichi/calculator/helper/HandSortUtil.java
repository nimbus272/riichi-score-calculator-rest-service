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
import com.gutterboys.riichi.calculator.model.CalculatorTracker;
import com.gutterboys.riichi.calculator.model.PossibleHand;
import com.gutterboys.riichi.calculator.model.PossibleMelds;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorResponse;

import ch.qos.logback.classic.Logger;

@Component
public class HandSortUtil {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(HandSortUtil.class);

    public void swapFives(List<Integer> redFives, CalculatorTracker tracker) {
        LOGGER.info("Swapping fives...");
        for (int i = 0; i < redFives.size(); i++) {
            Integer tile = redFives.get(i);
            switch (tile) {
                case 34:
                    tracker.getTiles().remove(tracker.getTiles().indexOf(tile));
                    tracker.getTiles().add(0, 4);
                    tracker.setDoraCount(tracker.getDoraCount() + 1);
                    continue;
                case 35:
                    tracker.getTiles().remove(tracker.getTiles().indexOf(tile));
                    tracker.getTiles().add(0, 13);
                    tracker.setDoraCount(tracker.getDoraCount() + 1);
                    continue;
                case 36:
                    tracker.getTiles().remove(tracker.getTiles().indexOf(tile));
                    tracker.getTiles().add(0, 22);
                    tracker.setDoraCount(tracker.getDoraCount() + 1);
                    continue;
                default:
                    continue;
            }
        }
    }

    public void checkChi(List<Integer> hand, int tile, List<List<Integer>> possibleChis, int numberOfDuplicates) {
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

    public void checkHonors(CalculatorTracker tracker)
            throws RiichiCalculatorException {
        LOGGER.info("Checking honors...");
        for (int i = 0; i < tracker.getTiles().size(); i++) {
            int tile = tracker.getTiles().get(i);
            if (RiichiCalculatorConstants.HONORS.contains(tile)) {
                switch ((int) tracker.getTiles().stream().filter(x -> x == tile).count()) {
                    case 2:
                        tracker.getMelds().add(new ArrayList<Integer>(Arrays.asList(tile, tile)));
                        CommonUtil.removeAndAddPonFromList(tracker.getTiles(), tile, 2);
                        tracker.setPairCount(tracker.getPairCount() + 1);
                        break;
                    case 3:
                        tracker.getMelds().add(new ArrayList<Integer>(Arrays.asList(tile, tile, tile)));
                        CommonUtil.removeAndAddPonFromList(tracker.getTiles(), tile, 3);
                        break;
                    case 4:
                        tracker.getMelds().add(new ArrayList<Integer>(Arrays.asList(tile, tile, tile, tile)));
                        CommonUtil.removeAndAddPonFromList(tracker.getTiles(), tile, 4);
                        tracker.setKanCount(tracker.getKanCount() + 1);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void determineConfirmedMelds(CalculatorTracker tracker,
            RiichiCalculatorResponse response,
            PossibleMelds possibleMelds)
            throws InvalidHandException {
        int unsortedCount = (int) tracker.getTiles().stream().filter(x -> x != -1).count();

        for (int i = 0; i < tracker.getTiles().size(); i++) {
            int tile = tracker.getTiles().get(i);
            int numberOfDuplicates = (int) tracker.getTiles().stream().filter(x -> x == tile).count();
            List<List<Integer>> possibleChis = new ArrayList<List<Integer>>();

            if (tile == -1) {
                continue;
            }

            tryGetMeld(tracker, possibleChis, possibleMelds, tile, numberOfDuplicates);

        }
        if (unsortedCount > (int) tracker.getTiles().stream().filter(x -> x != -1).count()) {
            determineConfirmedMelds(tracker, response, possibleMelds);
            return;
        }

        if (tracker.getTiles().stream().filter(x -> x != -1).count() == 0 && tracker.getMelds().size() == 5) {
            PossibleHand possibleHand = new PossibleHand();
            possibleHand.getTiles().addAll(response.getTiles());
            possibleHand.getMelds().addAll(tracker.getMelds());
            possibleHand.getMelds().sort((a, b) -> a.get(0) - b.get(0));
            response.getPossibleHands().add(possibleHand);
            return;
        }
        return;
    }

    public void guessAndCheckPossibleMelds(PossibleMelds possibleMelds, CalculatorTracker tracker,
            RiichiCalculatorResponse response)
            throws InvalidHandException {
        LOGGER.info("Reducing possible melds...");
        List<Integer> reducedHand = new ArrayList<>(tracker.getTiles());
        List<List<Integer>> lockedMelds = new ArrayList<List<Integer>>(tracker.getMelds());
        if (possibleMelds.getPairs().size() > 0 && tracker.getPairCount() == 0) {
            for (int i = 0; i < possibleMelds.getPairs().size(); i++) {
                CalculatorTracker tempTracker = new CalculatorTracker();
                List<Integer> tempHand = new ArrayList<>(reducedHand);
                List<Integer> pair = possibleMelds.getPairs().get(i);
                tempHand.remove(pair.get(0));
                tempHand.remove(pair.get(1));
                if (tempTracker.getTiles().size() > 0) {
                    tempTracker.getTiles().clear();
                }
                tempTracker.getTiles().addAll(tempHand);
                tempTracker.setPairCount(1);
                try {
                    determineConfirmedMeldsFromTempTracker(tracker, tempTracker, response, lockedMelds, reducedHand);
                } catch (InvalidHandException e) {
                    continue;
                }
            }

        }

        if (possibleMelds.getPons().size() > 0) {
            for (int i = 0; i < possibleMelds.getPons().size(); i++) {
                CalculatorTracker tempTracker = new CalculatorTracker();
                List<Integer> tempHand = new ArrayList<Integer>(reducedHand);
                List<Integer> pon = possibleMelds.getPons().get(i);
                tempHand.remove(pon.get(0));
                tempHand.remove(pon.get(1));
                tempHand.remove(pon.get(2));
                tempTracker.getTiles().addAll(tempHand);
                try {
                    determineConfirmedMeldsFromTempTracker(tracker, tempTracker, response, lockedMelds, pon);
                } catch (InvalidHandException e) {
                    continue;
                }

            }
        }
        if (possibleMelds.getChis().size() > 0) {
            for (int i = 0; i < possibleMelds.getChis().size(); i++) {
                CalculatorTracker tempTracker = new CalculatorTracker();
                List<Integer> tempHand = new ArrayList<Integer>(reducedHand);
                List<Integer> chi = possibleMelds.getChis().get(i);
                tempHand.remove(chi.get(0));
                tempHand.remove(chi.get(1));
                tempHand.remove(chi.get(2));
                tempTracker.getTiles().addAll(tempHand);
                try {
                    determineConfirmedMeldsFromTempTracker(tracker, tempTracker, response, lockedMelds, chi);
                } catch (InvalidHandException e) {
                    continue;
                }
            }
        }

        if (response.getPossibleHands().size() == 0) {
            LOGGER.error("Invalid hand detected in reducePossibleMelds(): {}",
                    tracker.getTiles());
            throw new InvalidHandException("Invalid hand");
        } else {
            CommonUtil.checkAndRemoveDuplicatePossibleHands(response.getPossibleHands());
            tracker.getTiles()
                    .removeAll(tracker.getTiles().stream().filter(x -> x != -1).collect(Collectors.toList()));
            return;
        }

    }

    private void determineConfirmedMeldsFromTempTracker(CalculatorTracker mainTracker, CalculatorTracker tempTracker,
            RiichiCalculatorResponse response,
            List<List<Integer>> lockedMelds, List<Integer> meld) throws InvalidHandException {
        PossibleMelds tempPossibleMelds = new PossibleMelds();
        try {
            determineConfirmedMelds(tempTracker, response, tempPossibleMelds);
        } catch (InvalidHandException e) {
            throw new InvalidHandException("Invalid hand detected.");
        }

        if (tempTracker.getTiles().stream().filter(x -> x != -1).count() == 0) {

            CommonUtil.checkMeldTypesAndRemoveDupes(tempPossibleMelds, tempTracker.getTiles());
            LOGGER.info("Hand determined!");
            CommonUtil.addTempMeldsToTracker(tempTracker, mainTracker);
            mainTracker.getMelds().add(meld);

            for (int j = 0; j < response.getPossibleHands().size(); j++) {
                if (response.getPossibleHands().get(j).getMelds().size() < 5) {
                    response.getPossibleHands().remove(j);
                }
            }
            PossibleHand possibleHand = new PossibleHand();
            possibleHand.getMelds().addAll(mainTracker.getMelds());
            possibleHand.getTiles().addAll(response.getTiles());
            possibleHand.getMelds().sort((a, b) -> a.get(0) - b.get(0));
            response.getPossibleHands().add(possibleHand);
            mainTracker.getMelds().clear();
            mainTracker.getMelds().addAll(lockedMelds);

        }
    }

    private void tryGetMeld(CalculatorTracker tracker,
            List<List<Integer>> possibleChis,
            PossibleMelds possibleMelds,
            int tile, int numberOfDuplicates) throws InvalidHandException {

        checkChi(tracker.getTiles(), tile, possibleChis, numberOfDuplicates);

        if (possibleChis.size() == 0) {
            switch (numberOfDuplicates) {
                case 1:
                    LOGGER.error("Invalid hand detected in reduceHand(): {}", tracker.getTiles());
                    throw new InvalidHandException("Invalid hand -- tile " + tile + " does not fit into meld");
                case 2:
                    if (tracker.getPairCount() == 0) {
                        tracker.getMelds().add(new ArrayList<Integer>(Arrays.asList(tile, tile)));
                        CommonUtil.removeAndAddPonFromList(tracker.getTiles(), tile, 2);
                        tracker.setPairCount(tracker.getPairCount() + 1);
                    }
                    break;
                case 3:
                    tracker.getMelds().add(Arrays.asList(tile, tile, tile));
                    CommonUtil.removeAndAddPonFromList(tracker.getTiles(), tile, 3);
                    break;
                case 4:
                    tracker.getMelds().add(new ArrayList<Integer>(Arrays.asList(tile, tile, tile, tile)));
                    CommonUtil.removeAndAddPonFromList(tracker.getTiles(), tile, 4);
                    tracker.setKanCount(tracker.getKanCount() + 1);
                    break;
                default:
                    break;
            }
        } else if (possibleChis.size() == 1) {
            switch (numberOfDuplicates) {
                case 1:
                    CommonUtil.removeAndAddChiFromList(tracker.getTiles(), possibleChis.get(0).get(0));
                    tracker.getMelds().add(possibleChis.get(0));
                    break;
                case 2:
                    if (tracker.getPairCount() == 1) {
                        CommonUtil.removeAndAddChiFromList(tracker.getTiles(), possibleChis.get(0).get(0));
                        tracker.getMelds().add(possibleChis.get(0));
                        break;
                    } else {
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
            if (possibleChis.get(0).containsAll(possibleChis.get(1)) && tracker.getPairCount() == 1) {
                checkMultipleDuplicateSequences(tracker, possibleChis, 2);

            } else {
                handleTooManyPossibilities(possibleChis, possibleMelds, numberOfDuplicates, tile);
            }
        } else if (possibleChis.size() == 3 && tracker.getTiles().size() < 14) {
            if (possibleChis.get(0).containsAll(possibleChis.get(1))
                    && possibleChis.get(1).containsAll(possibleChis.get(2))) {
                checkMultipleDuplicateSequences(tracker, possibleChis, 3);
            }
        } else {

            handleTooManyPossibilities(possibleChis, possibleMelds, numberOfDuplicates, tile);

        }
    }

    private void checkMultipleDuplicateSequences(CalculatorTracker tracker, List<List<Integer>> possibleChis,
            int numberOfChis) {
        if (tracker.getTiles().stream().filter(x -> x == possibleChis.get(0).get(0)).count() == (long) numberOfChis
                && tracker.getTiles().stream().filter(x -> x == possibleChis.get(0).get(1))
                        .count() == (long) numberOfChis
                && tracker.getTiles().stream().filter(x -> x == possibleChis.get(0).get(2))
                        .count() == (long) numberOfChis) {
            for (int i = 0; i < numberOfChis; i++) {
                CommonUtil.removeAndAddChiFromList(tracker.getTiles(), possibleChis.get(0).get(0));
                tracker.getMelds().add(possibleChis.get(i));
            }
        }
    }

    private void handleTooManyPossibilities(List<List<Integer>> possibleChis, PossibleMelds possibleMelds,
            int numberOfDuplicates, int tile) {
        if (possibleChis.size() > 0) {
            for (int i = 0; i < possibleChis.size(); i++) {
                possibleMelds.getChis().add(possibleChis.get(i));
            }
        }
        switch (numberOfDuplicates) {
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

    public void addOpenMeldsToTiles(RiichiCalculatorRequest request, CalculatorTracker tracker) {
        LOGGER.info("Adding open melds to list of tiles...");
        if (request.getOpenMelds().size() > 0) {
            for (int i = 0; i < request.getOpenMelds().size(); i++) {
                tracker.getTiles().addAll(request.getOpenMelds().get(i));
            }
        }
    }

}