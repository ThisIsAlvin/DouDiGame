package com.viv.server.entity;

import com.viv.server.Config;
import com.viv.server.service.GamePlayer;

import java.util.ArrayList;

/**
 * Created by viv on 16-4-27.
 */
public class RoomPlayer {
    /*玩家的名字*/
    public String playerName;
    /*角色所有的现金*/
    public long cash;
    /*角色的地产价值*/
    public long land;
    /*角色行走的方向*/
    public String direction;
    /*该用户的 gamePlayer对象*/
    public GamePlayer gamePlayer;
    /*该玩家所在的格子*/
    public GameGezi gezi;
    /*玩家所在格子的标号*/
    public int geziId;

    public RoomPlayer(GamePlayer gamePlayer,String direction,GameGezi gameGezi,int geziId){
        this.cash = Config.PLAYER_PRICE;
        this.land = 0;
        this.direction = direction;
        this.gamePlayer = gamePlayer;
        this.playerName = gamePlayer.playerName;
        this.gezi = gameGezi;
        this.geziId = geziId;
    }

    /*玩家移动*/
    public void move(int num) {
        ArrayList<GameGezi> gameGezis = gamePlayer.room.getGameData().gameGezis;
        for (int i = 0; i < gameGezis.size(); i++) {
            if (gameGezis.get(i) == gezi) {
                int go;
                if (direction.equals(Config.DIRECTION_RIGHT)) {
                    if (i + num < Config.GEZHI_NUM) {
                        go = i + num;
                    } else {
                        go = i + num - Config.GEZHI_NUM;
                    }
                } else {
                    if (i - num >= 0) {
                        go = i - num;
                    } else {
                        go = i - num + Config.GEZHI_NUM;
                    }
                }
                gameGezis.get(i).roomPlayers.remove(this);
                gameGezis.get(go).roomPlayers.add(this);
                gezi = gameGezis.get(go);
                geziId = go;
                break;
            }
        }
    }
}
