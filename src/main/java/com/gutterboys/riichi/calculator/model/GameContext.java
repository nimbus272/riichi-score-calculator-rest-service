package com.gutterboys.riichi.calculator.model;

import java.util.ArrayList;
import java.util.List;

public class GameContext {

    private List<Integer> tiles = new ArrayList<Integer>();
    private List<List<Integer>> melds = new ArrayList<List<Integer>>();
    private List<Integer> doraTiles = new ArrayList<Integer>();
    private int currentMeld = 0;
    private int winningTile;
    private int seatWind;
    private int prevalentWind;
    private boolean riichi;
    private boolean doubleRiichi;
    private boolean ippatsu;
    private boolean robbedKan;
    private boolean deadWallDraw;
    private boolean lastDiscard;
    private boolean tsumo;
    private boolean opened;
    private int honba;
    private int ponCount;
    private int chiCount;
    private int kanCount;
    private int pairCount;
    private int doraCount = 0;

    public int getCurrentMeld() {
        return currentMeld;
    }

    public void setCurrentMeld(int currentMeld) {
        this.currentMeld = currentMeld;
    }

    public int getPairCount() {
        return pairCount;
    }

    public void setPairCount(int pairCount) {
        this.pairCount = pairCount;
    }

    public int getPonCount() {
        return ponCount;
    }

    public void setPonCount(int ponCount) {
        this.ponCount = ponCount;
    }

    public int getChiCount() {
        return chiCount;
    }

    public void setChiCount(int chiCount) {
        this.chiCount = chiCount;
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

    public List<Integer> getDoraTiles() {
        return doraTiles;
    }

    public List<List<Integer>> getMelds() {
        return melds;
    }

    public boolean isDoubleRiichi() {
        return doubleRiichi;
    }

    public void setDoubleRiichi(boolean doubleRiichi) {
        this.doubleRiichi = doubleRiichi;
    }

    public boolean isIppatsu() {
        return ippatsu;
    }

    public void setIppatsu(boolean ippatsu) {
        this.ippatsu = ippatsu;
    }

    public boolean isRobbedKan() {
        return robbedKan;
    }

    public void setRobbedKan(boolean robbedKan) {
        this.robbedKan = robbedKan;
    }

    public boolean isDeadWallDraw() {
        return deadWallDraw;
    }

    public void setDeadWallDraw(boolean deadWallDraw) {
        this.deadWallDraw = deadWallDraw;
    }

    public boolean isLastDiscard() {
        return lastDiscard;
    }

    public void setLastDiscard(boolean lastDiscard) {
        this.lastDiscard = lastDiscard;
    }

    public int getHonba() {
        return honba;
    }

    public void setHonba(int honba) {
        this.honba = honba;
    }

    public int getWinningTile() {
        return winningTile;
    }

    public void setWinningTile(int winningTile) {
        this.winningTile = winningTile;
    }

    public List<Integer> getTiles() {
        return tiles;
    }

    public boolean isRiichi() {
        return riichi;
    }

    public void setRiichi(boolean riichi) {
        this.riichi = riichi;
    }

    public boolean isTsumo() {
        return tsumo;
    }

    public void setTsumo(boolean tsumo) {
        this.tsumo = tsumo;
    }

    public int getSeatWind() {
        return seatWind;
    }

    public void setSeatWind(int seatWind) {
        this.seatWind = seatWind;
    }

    public int getPrevalentWind() {
        return prevalentWind;
    }

    public void setPrevalentWind(int prevalentWind) {
        this.prevalentWind = prevalentWind;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

}
