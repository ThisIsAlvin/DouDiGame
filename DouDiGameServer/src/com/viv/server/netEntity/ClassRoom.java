package com.viv.server.netEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viv on 16-4-27.
 */
public class ClassRoom{
    private String roomName;
    private ArrayList<String> players;
    private String room_status;


    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public String getRoom_status() {
        return room_status;
    }

    public void setRoom_status(String room_status) {
        this.room_status = room_status;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "players=" + players +
                ", roomName='" + roomName + '\'' +
                ", room_status='" + room_status + '\'' +
                '}';
    }
}
