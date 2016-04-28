package com.example.DouDiGame.netEntity;

/**
 * Created by viv on 16-4-28.
 */
public class GameMessage {
    /*玩家名字*/
    private String playerName;
    /*玩家现金*/
    private long cash;
    /*玩家的地产*/
    private long land;
    /*玩家的行走方向*/
    private String direction;
    /*玩家的所在位置*/
    private int address;
    /*色子点数*/
    private int num;
    /*操作者*/
    private String dealer;

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public long getLand() {
        return land;
    }

    public void setLand(long land) {
        this.land = land;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
