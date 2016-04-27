package com.viv.server.netEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viv on 16-4-27.
 */
public class ClassRoom{
    private String roomName;
    private ArrayList<String> players;

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
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
                '}';
    }
}
