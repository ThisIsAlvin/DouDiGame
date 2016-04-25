package com.viv.server.controller;

import com.viv.server.entity.Room;
import com.viv.server.service.GamePlayer;

import java.util.Vector;

/**
 * Created by viv on 16-4-25.
 */
public class SocketManager {
    private SocketManager(){}
    private static final SocketManager socketManager = new SocketManager();
    public static SocketManager getSocketManager(){
        return socketManager;
    }
    private Vector<GamePlayer> login = new Vector<GamePlayer>();
    private Vector<GamePlayer> wait = new Vector<GamePlayer>();
    private Vector<GamePlayer> playing = new Vector<GamePlayer>();
    private Vector<Room> rooms = new Vector<Room>();

    /*登录玩家添加进来*/
    public void login(GamePlayer gamePlayer) {
        synchronized (GamePlayer.class){
            login.add(gamePlayer);
        }
    }

    /*登录玩家进入大厅或者房间等待游戏开始*/
    public void login2wait() {
        synchronized (GamePlayer.class){

        }
    }

    /*在房间中的玩家开始游戏*/
    public void wait2playing() {
        synchronized (GamePlayer.class){

        }

    }

    /*游戏中的玩家结束游戏，返回房间或者大厅*/
    public void playing2wait() {
        synchronized (GamePlayer.class){

        }
    }

    /*玩家结束游戏，离开*/
    public void leave() {
        synchronized (GamePlayer.class){

        }
    }

    /*玩家创建房间*/
    public void newRoom() {
        synchronized (GamePlayer.class){

        }
    }

    /*玩家加入房间*/
    public void inRoom() {
        synchronized (GamePlayer.class){

        }

    }

    /*玩家离开房间*/
    public void outRoom() {
        synchronized (GamePlayer.class){

        }

    }
}
