package com.viv.server.controller;

import com.viv.server.Config;
import com.viv.server.entity.Room;
import com.viv.server.netEntity.GameMessage;
import com.viv.server.netEntity.InitMessage;
import com.viv.server.netEntity.Message;
import com.viv.server.service.GamePlayer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by viv on 16-4-25.
 */
public class SocketManager {
    private SocketManager() {
    }

    private static final SocketManager socketManager = new SocketManager();

    public static SocketManager getSocketManager() {
        return socketManager;
    }

    private Vector<GamePlayer> connect = new Vector<GamePlayer>();
    private Vector<GamePlayer> wait = new Vector<GamePlayer>();
    private Vector<GamePlayer> playing = new Vector<GamePlayer>();
    private Vector<Room> rooms = new Vector<Room>();

    /*登录玩家添加进来*/
    public void connect(GamePlayer gamePlayer) {
        synchronized (GamePlayer.class) {
            if (!connect.contains(gamePlayer)) {
                connect.add(gamePlayer);
            }
        }
    }

    /*登录玩家进入大厅或者房间等待游戏开始*/
    public void connect2wait(GamePlayer gamePlayer) {
        synchronized (GamePlayer.class) {
            if (connect.remove(gamePlayer)) {
                wait.add(gamePlayer);
            }
        }
    }

    /*在房间中的玩家开始游戏*/
    public void wait2playing(Vector<GamePlayer> gamePlayers) {
        synchronized (GamePlayer.class) {
            for (GamePlayer g :
                    gamePlayers) {
                if (wait.remove(g)) {
                    playing.add(g);
                }
            }

        }

    }

    /*游戏中的玩家结束游戏，返回房间或者大厅*/
    public void playing2wait() {
        synchronized (GamePlayer.class) {

        }
    }

    /*玩家结束游戏，离开*/
    public void leave() {
        synchronized (GamePlayer.class) {

        }
    }

    /*玩家创建房间*/
    public void newRoom(Room room) {
        synchronized (GamePlayer.class) {
            if (!rooms.contains(room)) {
                rooms.add(room);
                /*通知其他等待用户更新大厅数据 */
                Message message = new Message();
                message.setForWhat(Config.WAITER_UPDATE_HALL);
                notifyAllPlayer(message);
            }
        }
    }

    /*玩家加入房间*/
    public boolean inRoom(GamePlayer gamePlayer,String  roomName) {
        synchronized (GamePlayer.class) {
            for (Room r :
                    rooms) {
                if (r.getRoomName().equals(roomName) && r.getGameData().roomStatus.equals(Config.ROOM_WAIT)) {
                    r.addPlayers(gamePlayer);
                    return true;
                }
            }
            return false;
        }

    }

    /*玩家离开房间*/
    public void outRoom(GamePlayer gamePlayer) {
        synchronized (GamePlayer.class) {
            for (Room r :
                    rooms) {
                if (r.getPlayers().contains(gamePlayer)) {
                    r.remotePlayers(gamePlayer);
                    /*如果房间空了，就删除掉房间*/
                    if (r.getPlayers().size()==0) {
                        deleteRoom(r);
                    }
                    break;
                }
            }
        }
    }

    public void deleteRoom(Room room) {
        synchronized (GamePlayer.class) {
            if (rooms.contains(room)) {
                rooms.remove(room);
                 /*通知其他等待用户更新大厅数据 */
                Message message = new Message();
                message.setForWhat(Config.WAITER_UPDATE_HALL);
                notifyAllPlayer(message);
            }
        }
    }

    /*获取connect克隆对象！！！！！！！！注意只是浅拷贝*/
    public Vector<GamePlayer> getConnect() {
        return (Vector<GamePlayer>) connect.clone();
    }

    /*获取wait克隆对象！！！！！！！！注意只是浅拷贝*/
    public Vector<GamePlayer> getWait() {
        return (Vector<GamePlayer>) wait.clone();
    }

    /*获取playing克隆对象！！！！！！！！注意只是浅拷贝*/
    public Vector<GamePlayer> getPlaying() {
        return (Vector<GamePlayer>) playing.clone();
    }
    /*获取rooms克隆对象！！！！！！！！注意只是浅拷贝*/
    public Vector<Room> getRooms(){
        return (Vector<Room>) rooms.clone();
    }

    /*给所有等待的玩家发通知*/
    public void notifyAllPlayer(Message message){
        String forWhat = message.getForWhat();
        String line = null;
        ObjectMapper mapper = new ObjectMapper();
        try{
            switch (forWhat){
                case Config.WAITER_UPDATE_HALL:{
                    /*通知所有waiter更新大厅数据*/
                    line = mapper.writeValueAsString(message);
                    for (GamePlayer gp :
                            wait) {
                        gp.bw.write(line + "\n");
                        gp.bw.flush();
                    }
                    break;
                }
                default:break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
