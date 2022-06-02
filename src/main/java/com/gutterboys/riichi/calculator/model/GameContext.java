package com.gutterboys.riichi.calculator.model;

public class GameContext {

    private String hand;
    private String winningTile;
    private String seatWind;
    private String prevalentWind;
    private boolean riichi;
    private boolean doubleRiichi;
    private boolean ippatsu;
    private boolean robbedKan;
    private boolean deadWallDraw;
    private boolean lastDiscard;
    private boolean tsumo;
    private boolean opened;
    private int dora;
    private int honba;

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

    public String getWinningTile() {
        return winningTile;
    }

    public void setWinningTile(String winningTile) {
        this.winningTile = winningTile;
    }

    public String getHand() {
        return hand;
    }

    public void setHand(String hand) {
        this.hand = hand;
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

    public int getDora() {
        return dora;
    }

    public void setDora(int dora) {
        this.dora = dora;
    }

    public String getSeatWind() {
        return seatWind;
    }

    public void setSeatWind(String seatWind) {
        this.seatWind = seatWind;
    }

    public String getPrevalentWind() {
        return prevalentWind;
    }

    public void setPrevalentWind(String prevalentWind) {
        this.prevalentWind = prevalentWind;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

}
