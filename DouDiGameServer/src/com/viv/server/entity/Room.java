package com.viv.server.entity;

import com.viv.server.service.GamePlayer;

import java.util.Vector;

/**
 * Created by viv on 16-4-25.
 */
public class Room {
    private String roomName;
    private GameData gameData;
    private Vector<GamePlayer> players;

    public Room() {
        this.gameData = new GameData();
        this.players = new Vector<GamePlayer>();
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /*添加玩家进入房间*/
    public void addPlayers(GamePlayer gamePlayer) {
        /*判断该玩家不在这个房间*/
        if (!players.contains(gamePlayer)) {
            players.add(gamePlayer);
        }
    }

    /*将玩家移出房间*/
    public void remotePlayers(GamePlayer gamePlayer) {
        /*判断该玩家在这个房间*/
        if (players.contains(gamePlayer)) {
            players.remove(gamePlayer);
        }
    }

    /*获取房间中玩家数据的克隆*/
    public Vector<GamePlayer> getPlayers() {
        return (Vector<GamePlayer>) players.clone();
    }

    /*将信息同步给房间所有人*/
    public void notifyAllPlayer() {

    }

}
