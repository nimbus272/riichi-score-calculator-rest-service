package com.gutterboys.riichi.calculator.model;

import java.util.ArrayList;
import java.util.List;

public class PossibleMelds {

    private List<List<Integer>> chis = new ArrayList<List<Integer>>();
    private List<List<Integer>> pons = new ArrayList<List<Integer>>();
    private List<List<Integer>> kans = new ArrayList<List<Integer>>();
    private List<List<Integer>> pairs = new ArrayList<List<Integer>>();

    public List<List<Integer>> getChis() {
        return chis;
    }

    public List<List<Integer>> getPons() {
        return pons;
    }

    public List<List<Integer>> getKans() {
        return kans;
    }

    public List<List<Integer>> getPairs() {
        return pairs;
    }

}
