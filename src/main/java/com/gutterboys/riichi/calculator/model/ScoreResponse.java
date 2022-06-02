package com.gutterboys.riichi.calculator.model;

public class ScoreResponse extends GenericResponse {

    private int han;
    private int fu;
    private int baseScore;
    private int tsumoToNonDealer;
    private int tsumoToDealer;
    private int ronToNonDealer;
    private int ronToDealer;

    public int getTsumoToDealer() {
        return tsumoToDealer;
    }

    public void setTsumoToDealer(int tsumoToDealer) {
        this.tsumoToDealer = tsumoToDealer;
    }

    public int getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(int baseScore) {
        this.baseScore = baseScore;
    }

    public void setHan(int han) {
        this.han = han;
    }

    public int getHan() {
        return han;
    }

    public int getFu() {
        return fu;
    }

    public void setFu(int fu) {
        this.fu = fu;
    }

    public int getTsumoToNonDealer() {
        return tsumoToNonDealer;
    }

    public void setTsumoToNonDealer(int tsumoToNonDealer) {
        this.tsumoToNonDealer = tsumoToNonDealer;
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

}
