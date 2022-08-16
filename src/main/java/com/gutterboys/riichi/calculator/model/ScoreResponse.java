package com.gutterboys.riichi.calculator.model;

import java.util.ArrayList;
import java.util.List;

public class ScoreResponse extends GenericResponse {

    private List<PossibleHand> possibleHands = new ArrayList<PossibleHand>();
    private List<Integer> tiles = new ArrayList<Integer>();

    public List<Integer> getTiles() {
        return tiles;
    }

    public List<PossibleHand> getPossibleHands() {
        return possibleHands;
    }

}
