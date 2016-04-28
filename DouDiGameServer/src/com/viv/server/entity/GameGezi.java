package com.viv.server.entity;

import com.viv.server.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viv on 16-4-27.
 */
public class GameGezi {

    /*标记土地所属*/
    public RoomPlayer geziHost;
    /*标记土地价格*/
    public long price;
    /*当前在这个格子上的玩家*/
    public ArrayList<RoomPlayer> roomPlayers;

    public GameGezi(){
        this.price = Config.GEZHI_PRICE;
        this.geziHost = null;
        roomPlayers = new ArrayList<RoomPlayer>();
    }
}
