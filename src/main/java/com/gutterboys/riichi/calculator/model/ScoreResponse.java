package com.gutterboys.riichi.calculator.model;

import java.util.ArrayList;
import java.util.List;

public class ScoreResponse extends GenericResponse {

    private int han = 0;
    private int fu = 0;
    private int baseScore;
    private int tsumoFromNonDealer;
    private int tsumoFromDealer;
    private int ronToNonDealer;
    private int ronToDealer;
    private List<String> qualifiedYaku = new ArrayList<String>();
    private boolean mangan;
    private boolean haneman;
    private boolean baiman;
    private boolean sanbaiman;
    private boolean yakuman;

    public boolean isMangan() {
        return mangan;
    }

    public void setMangan(boolean mangan) {
        this.mangan = mangan;
    }

    public boolean isHaneman() {
        return haneman;
    }

    public void setHaneman(boolean haneman) {
        this.haneman = haneman;
    }

    public boolean isBaiman() {
        return baiman;
    }

    public void setBaiman(boolean baiman) {
        this.baiman = baiman;
    }

    public boolean isSanbaiman() {
        return sanbaiman;
    }

    public void setSanbaiman(boolean sanbaiman) {
        this.sanbaiman = sanbaiman;
    }

    public boolean isYakuman() {
        return yakuman;
    }

    public void setYakuman(boolean yakuman) {
        this.yakuman = yakuman;
    }

    public List<String> getQualifiedYaku() {
        return qualifiedYaku;
    }

    public void setQualifiedYaku(List<String> qualifiedYaku) {
        this.qualifiedYaku = qualifiedYaku;
    }

    public int getTsumoFromDealer() {
        return tsumoFromDealer;
    }

    public void setTsumoFromDealer(int tsumoToDealer) {
        this.tsumoFromDealer = tsumoToDealer;
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

    public int getTsumoFromNonDealer() {
        return tsumoFromNonDealer;
    }

    public void setTsumoFromNonDealer(int tsumoFromNonDealer) {
        this.tsumoFromNonDealer = tsumoFromNonDealer;
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
