package com.viv.server.entity;

import com.viv.server.Config;
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
public class Room {
    private String roomName;
    private GameData gameData;
    private Vector<GamePlayer> players;
    public String initDataJson = null;

    public Room() {
        this.players = new Vector<GamePlayer>();
        this.gameData = new GameData(players);
    }

    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
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
            gamePlayer.room = this;
            /*通知房间所有用户更新房间数据*/
            Message message = new Message();
            message.setForWhat(Config.ROOMER_UPDATE_ROOM);
            notifyAllPlayer(message);
        }
    }

    /*将玩家移出房间*/
    public void remotePlayers(GamePlayer gamePlayer) {
        /*判断该玩家在这个房间*/
        if (players.contains(gamePlayer)) {
            players.remove(gamePlayer);
            gamePlayer = null;
             /*通知房间所有用户更新房间数据*/
            Message message = new Message();
            message.setForWhat(Config.ROOMER_UPDATE_ROOM);
            notifyAllPlayer(message);
        }
    }

    /*获取房间中玩家数据的克隆！！！！！！！！注意只是浅拷贝*/
    public Vector<GamePlayer> getPlayers() {
        return (Vector<GamePlayer>) players.clone();
    }

    /*将信息同步给房间所有人*/
    public void notifyAllPlayer(Message message) {
        String forWhat = message.getForWhat();
        String line = null;
        ObjectMapper mapper = new ObjectMapper();
        try{
        switch (forWhat){
            case Config.START_GAME_RESULT:{
                /*初始化游戏数据*/
                InitMessage init = new InitMessage();
                ArrayList<String> arrayList =new ArrayList<>();
                init.setGameTime(gameData.time);
                init.setGeziNum(gameData.gameGezis.size());
                init.setGameTime(gameData.time);
                init.setGeziPrice(gameData.gameGezis.get(0).price);
                init.setGeziRate(gameData.rate);
                init.setPlayers(arrayList);
                GameMessage player = null;
                for (int i = 0; i< players.size();i++) {
                    player = new GameMessage();
                    player.setPlayerName(gameData.roomPlayers.get(i).playerName);
                    player.setCash(gameData.roomPlayers.get(i).cash);
                    player.setLand(gameData.roomPlayers.get(i).land);
                    player.setDirection(gameData.roomPlayers.get(i).direction);
                    player.setDealer(gameData.dealPlayer.playerName);
                    for (int j = 0; j < gameData.gameGezis.size(); j++) {
                        if (gameData.gameGezis.get(j).roomPlayers.size()>=1) {
                            if (gameData.gameGezis.get(j).roomPlayers.get(0).playerName.equals(player.getPlayerName())) {
                                player.setAddress(j);
                                break;
                            }
                        }
                    }
                        String player_json = mapper.writeValueAsString(player);
                        init.getPlayers().add(player_json);
                }
                initDataJson = mapper.writeValueAsString(init);
                System.out.println(initDataJson);
                message.setForWhat(Config.START_GAME_RESULT);
                message.setDoSomething(Config.SUCCESS);
                /*通知房间所有玩家来获取初始化数据*/
                line = mapper.writeValueAsString(message);
                for (GamePlayer gp :
                        players) {
                    gp.bw.write(line + "\n");
                    gp.bw.flush();
                }
                break;
            }
            case Config.ROOMER_UPDATE_ROOM:{
                /*通知房间用户更新房间数据*/
                line = mapper.writeValueAsString(message);
                for (GamePlayer gp :
                        players) {
                    gp.bw.write(line + "\n");
                    gp.bw.flush();
                }
                break;
            }
            case Config.PLAY_RESULT:{
                /*通知房间所有人更新投掷色子点数*/
                GameMessage gameMessage = new GameMessage();
                gameMessage.setPlayerName(gameData.dealPlayer.playerName);
                gameMessage.setCash(gameData.dealPlayer.cash);
                gameMessage.setLand(gameData.dealPlayer.land);
                gameMessage.setDirection(gameData.dealPlayer.direction);
                gameMessage.setAddress(gameData.dealPlayer.geziId);
                gameMessage.setNum(gameData.num);
                gameMessage.setDealer(gameData.dealPlayer.playerName);
                String dateJson = mapper.writeValueAsString(gameMessage);
                message.setDoSomething(Config.SUCCESS);
                message.setData(dateJson);
                line = mapper.writeValueAsString(message);
                for (GamePlayer gp :
                        players) {
                    gp.bw.write(line + "\n");
                    gp.bw.flush();
                }
                break;
            }
            case Config.DEAL_RESULT:{
                /*处理操作结果*/
                switch (message.getDoSomething()) {
                    case Config.OVER:{
                        /*结束游戏*/
                        line = mapper.writeValueAsString(message);
                        for (GamePlayer gp :
                                players) {
                            gp.bw.write(line + "\n");
                            gp.bw.flush();
                        }
                        break;
                    }
                    case Config.LOSER:{
                        /*破产*/
                        GameMessage gameMessage = new GameMessage();
                        RoomPlayer player = gameData.dealPlayer;
                        gameMessage.setPlayerName(player.playerName);
                        gameMessage.setCash(player.cash);
                        gameMessage.setLand(player.land);
                        gameMessage.setDirection(player.direction);
                        gameMessage.setAddress(player.geziId);
                        gameMessage.setDealer(gameData.dealPlayer.playerName);
                        String dateJson = mapper.writeValueAsString(gameMessage);
                        message.setDoSomething(Config.LOSER);
                        message.setData(dateJson);
                        line = mapper.writeValueAsString(message);
                        for (GamePlayer gp :
                                players) {
                            gp.bw.write(line + "\n");
                            gp.bw.flush();
                        }
                        break;
                    }
                    default:{
                        GameMessage gameMessage = new GameMessage();
                        RoomPlayer player = gameData.dealPlayer;
                        gameMessage.setPlayerName(player.playerName);
                        gameMessage.setCash(player.cash);
                        gameMessage.setLand(player.land);
                        gameMessage.setDirection(player.direction);
                        gameMessage.setAddress(player.geziId);
                        gameMessage.setDealer(gameData.dealPlayer.playerName);
                        String dateJson = mapper.writeValueAsString(gameMessage);
                        message.setDoSomething(Config.SUCCESS);
                        message.setData(dateJson);
                        line = mapper.writeValueAsString(message);
                        for (GamePlayer gp :
                                players) {
                            gp.bw.write(line + "\n");
                            gp.bw.flush();
                        }
                        break;
                    }
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
