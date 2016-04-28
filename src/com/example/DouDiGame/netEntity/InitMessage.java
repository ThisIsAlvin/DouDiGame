package com.example.DouDiGame.netEntity;

import java.util.ArrayList;

/**
 * Created by viv on 16-4-28.
 */
public class InitMessage {
    /*初始时，格子数*/
    private Integer geziNum;
    /*每个格子的价格*/
    private long geziPrice;
    /*每回合，格子的涨幅*/
    private double geziRate;
    /*最大游戏回合数*/
    private Integer gameTime;
    /*玩家数据*/
    private ArrayList<String> players;


    public Integer getGameTime() {
        return gameTime;
    }

    public void setGameTime(Integer gameTime) {
        this.gameTime = gameTime;
    }

    public Integer getGeziNum() {
        return geziNum;
    }

    public void setGeziNum(Integer geziNum) {
        this.geziNum = geziNum;
    }

    public long getGeziPrice() {
        return geziPrice;
    }

    public void setGeziPrice(long geziPrice) {
        this.geziPrice = geziPrice;
    }

    public double getGeziRate() {
        return geziRate;
    }

    public void setGeziRate(double geziRate) {
        this.geziRate = geziRate;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }
}
