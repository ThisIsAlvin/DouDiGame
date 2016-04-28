package com.viv.server.entity;

import com.viv.server.Config;
import com.viv.server.service.GamePlayer;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by viv on 16-4-25.
 */
public class GameData {

    /*房间状态*/
    public String roomStatus;

    /*房间中的玩家*/
    private Vector<GamePlayer> players;
    public ArrayList<RoomPlayer> roomPlayers;

    /*时间进度*/
    public int time;

    /*操作权持有有人*/
    public RoomPlayer dealPlayer;

    /*当前产生的随机数*/
    public int num;

    /*地图格子*/
    public ArrayList<GameGezi> gameGezis;

    /*格子每回合的增长率*/
    public double rate;

    public GameData(Vector<GamePlayer> players) {
        this.roomStatus = Config.ROOM_WAIT;
        this.players = players;
    }

    /*开始游戏的时候，初始化游戏数据*/
    public void initGameData(){
        this.roomStatus = Config.ROOM_PLAYING;
        this.time = Config.GAME_TIME;
        this.rate = Config.GEZHI_RATE;
        this.gameGezis = new ArrayList<GameGezi>();
        this.roomPlayers = new ArrayList<RoomPlayer>();

        /*初始化玩家*/
        String direction = Config.DIRECTION_RIGHT;
        for (GamePlayer gp :
                players) {
            roomPlayers.add(new RoomPlayer(gp,direction));
        }
        /*初始化地图*/
        for (int i = 0; i < Config.GEZHI_NUM; i++) {
            gameGezis.add(new GameGezi());
        }
        /*初始化玩家位置*/
        for (int i = 0; i < roomPlayers.size(); i++) {
            gameGezis.get(i).roomPlayers.add(roomPlayers.get(i));
        }
        /*操作权持有者*/
        dealPlayer = roomPlayers.get(0);


    }


}
