package com.gutterboys.riichi.calculator.model;

import java.util.ArrayList;
import java.util.List;

public class PossibleHand {
    private List<List<Integer>> melds = new ArrayList<List<Integer>>();
    private List<String> qualifiedYaku = new ArrayList<String>();
    private List<Integer> tiles = new ArrayList<Integer>();

    private int han = 0;
    private int fu = 0;
    private int baseScore;
    private int tsumoFromNonDealer;
    private int tsumoFromDealer;
    private int ronToNonDealer;
    private int ronToDealer;
    private boolean doubleYakuman;
    private String specialScoreType;

    public List<Integer> getTiles() {
        return tiles;
    }

    public List<List<Integer>> getMelds() {
        return melds;
    }

    public List<String> getQualifiedYaku() {
        return qualifiedYaku;
    }

    public String getSpecialScoreType() {
        return specialScoreType;
    }

    public void setSpecialScoreType(String specialScoreType) {
        this.specialScoreType = specialScoreType;
    }

    public int getHan() {
        return han;
    }

    public void setHan(int han) {
        this.han = han;
    }

    public int getFu() {
        return fu;
    }

    public void setFu(int fu) {
        this.fu = fu;
    }

    public int getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(int baseScore) {
        this.baseScore = baseScore;
    }

    public int getTsumoFromNonDealer() {
        return tsumoFromNonDealer;
    }

    public void setTsumoFromNonDealer(int tsumoFromNonDealer) {
        this.tsumoFromNonDealer = tsumoFromNonDealer;
    }

    public int getTsumoFromDealer() {
        return tsumoFromDealer;
    }

    public void setTsumoFromDealer(int tsumoFromDealer) {
        this.tsumoFromDealer = tsumoFromDealer;
    }

    public int getRonToNonDealer() {
        return ronToNonDealer;
    }

    public void setRonToNonDealer(int ronToNonDealer) {
        this.ronToNonDealer = ronToNonDealer;
    }

    public int getRonToDealer() {
        return ronToDealer;
    }

    public void setRonToDealer(int ronToDealer) {
        this.ronToDealer = ronToDealer;
    }

    public boolean isDoubleYakuman() {
        return doubleYakuman;
    }

    public void setDoubleYakuman(boolean doubleYakuman) {
        this.doubleYakuman = doubleYakuman;
    }

}
