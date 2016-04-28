package com.viv.server.entity;

import com.viv.server.Config;
import com.viv.server.service.GamePlayer;

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

    public RoomPlayer(GamePlayer gamePlayer,String direction){
        this.cash = Config.PLAYER_PRICE;
        this.land = 0;
        this.direction = direction;
        this.gamePlayer = gamePlayer;
        this.playerName = gamePlayer.playerName;
    }
}
