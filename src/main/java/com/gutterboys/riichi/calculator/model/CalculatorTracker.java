package com.gutterboys.riichi.calculator.model;

import java.util.ArrayList;
import java.util.List;

public class CalculatorTracker {

    private List<Integer> tiles = new ArrayList<Integer>();
    private List<List<Integer>> melds = new ArrayList<List<Integer>>();
    private int kanCount = 0;
    private int pairCount = 0;
    private int doraCount = 0;

    public int getPairCount() {
        return pairCount;
    }

    public void setPairCount(int pairCount) {
        this.pairCount = pairCount;
    }

    public int getKanCount() {
        return kanCount;
    }

    public void setKanCount(int kanCount) {
        this.kanCount = kanCount;
    }

    public int getDoraCount() {
        return doraCount;
    }

    public void setDoraCount(int doraCount) {
        this.doraCount = doraCount;
    }

    public List<List<Integer>> getMelds() {
        return melds;
    }

    public List<Integer> getTiles() {
        return tiles;
    }

}
