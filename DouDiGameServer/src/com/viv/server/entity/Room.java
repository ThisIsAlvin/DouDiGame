package com.viv.server.entity;

import com.viv.server.service.GamePlayer;

import java.util.Vector;

/**
 * Created by viv on 16-4-25.
 */
public class Room {
    GameData gameData;
    Vector<GamePlayer> players;

    /*将信息同步给房间所有人*/
    public void notifyAllPlayer() {

    }
}
