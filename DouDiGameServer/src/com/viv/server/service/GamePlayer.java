package com.viv.server.service;

import com.viv.server.Config;
import com.viv.server.controller.SocketManager;
import com.viv.server.netEntity.ClassRoom;
import com.viv.server.netEntity.Message;
import com.viv.server.entity.Room;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by viv on 16-4-25.
 */
public class GamePlayer extends Thread{
    Socket socket;
    public Room room;
    SocketManager manager;
    public String playerName;


    public GamePlayer(Socket socket){
        this.socket = socket;
        this.manager = SocketManager.getSocketManager();
        this.mapper = new ObjectMapper();
    }

    public BufferedReader br;
    public BufferedWriter bw;
    public ObjectMapper mapper;
    Message message;
    String line;

    @Override
    public void run() {
        /*游戏操作*/
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), Config.ENCODE));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),Config.ENCODE));
            System.out.println("------------ready for accept-----------");
            while (true){
                while ((line = br.readLine()) != null){
                    message = mapper.readValue(line,Message.class);
                    System.out.println("forWhat---->"+message.getForWhat());
                    System.out.println(line);
                    switch (message.getForWhat()){
                        case Config.LOGIN:{
                            /*登录操作处理*/
                            login();
                            break;
                        }
                        case Config.HALL:{
                            /*获取大厅数据*/
                            getHall();
                            break;
                        }
                        case Config.IN_ROOM:{
                            /*进入房间*/
                            in_room();
                            break;
                        }
                        case Config.NEW_ROOM:{
                            /*创建房间*/
                            new_room();
                            break;
                        }
                        case Config.GET_ROOM:{
                            /*获取某个房间数据*/
                            get_room();
                            break;
                        }
                        case Config.START_GAME:{
                            /*开始游戏*/
                            start_game();
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*登录操作*/
    private void login(){
        System.out.println("登录玩家："+message.getDoSomething()+"； 数据："+message.getData());
        System.out.println("----------------------");
        try {
            /*判断用户是否链接却未登录*/
            if (manager.getConnect().contains(this)){
            /*记录用户名*/
                playerName = message.getData();
            /*将用户从connect---->wait*/
                manager.connect2wait(this);

                message = new Message();
                message.setForWhat(Config.LOGIN_STATUS);
                message.setDoSomething(Config.SUCCESS);
            line = mapper.writeValueAsString(message);
            bw.write(line+"\n");
            bw.flush();

        }else {
                message = new Message();
                message.setForWhat(Config.LOGIN_STATUS);
                message.setDoSomething(Config.FAIL);
                line = mapper.writeValueAsString(message);
                bw.write(line + "\n");
                bw.flush();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*获取大厅数据*/
    private void getHall() {
        System.out.println("大厅欢迎你:"+message.getDoSomething()+"； 数据："+message.getData());
        System.out.println("----------------------");
        try {
        /*判断用户是否登录却未开始游戏*/
            if (manager.getWait().contains(this)) {
                Vector<Room> room = manager.getRooms();
                int roomSize = room.size();
                ArrayList<ClassRoom> classRooms = new ArrayList<ClassRoom>();
                ClassRoom classRoom;
                for (int i = 0; i < roomSize; i++) {
                    classRoom = new ClassRoom();
                    classRoom.setRoomName(room.get(i).getRoomName());
                    classRoom.setRoom_status(room.get(i).getGameData().roomStatus);
                    ArrayList<String> players = new ArrayList<String>();
                    for (GamePlayer gameplayer :
                        room.get(i).getPlayers()) {
                        players.add(gameplayer.playerName);
                    }
                    classRoom.setPlayers(players);
                    classRooms.add(classRoom);
                }


                String room_json = mapper.writeValueAsString(classRooms);
                message = new Message();
                message.setForWhat(Config.HALL_RESULT);
                message.setDoSomething(Config.SUCCESS);
                message.setData(room_json);
                line = mapper.writeValueAsString(message);
                bw.write(line + "\n");
                bw.flush();

        }else {
                message = new Message();
                message.setForWhat(Config.HALL_RESULT);
            message.setDoSomething(Config.FAIL);
            message.setData("没有登录，非法操作，报警啦");

            line = mapper.writeValueAsString(message);
            bw.write(line + "\n");
            bw.flush();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*进入房间*/
    private void in_room(){
        System.out.println("进入房间:"+message.getDoSomething()+"； 数据："+message.getData());
        System.out.println("----------------------");
        try{
            /*判断用户是否登录却未开始游戏*/
            if (manager.getWait().contains(this)) {
                /*判断用户是否还有房间没有退出，帮他退出之前的房间*/
                Vector<Room> rooms = manager.getRooms();
                for (Room r :
                        rooms) {
                    if (r.getPlayers().contains(this)) {
                        r.remotePlayers(this);
                    }
                }
                /*进入房间*/
                manager.inRoom(this,message.getData());

                message.setForWhat(Config.IN_ROOM_RESULT);
                message.setDoSomething(Config.SUCCESS);
                line = mapper.writeValueAsString(message);
                bw.write(line + "\n");
                bw.flush();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*创建房间*/
    private void new_room(){
        System.out.println("创建房间："+message.getDoSomething()+"； 数据："+message.getData());
        System.out.println("----------------------");
        try {
        /*判断用户是否登录却未开始游戏*/
            if(manager.getWait().contains(this)){
                /*判断房间名是否已经存在*/
                Vector<Room> rooms = manager.getRooms();
                String roomName = message.getData();
                for (Room r :
                        rooms) {
                    if (roomName.equals(r.getRoomName())) {
                        message.setForWhat(Config.NEW_ROOM_RESULT);
                        message.setDoSomething(Config.FAIL);
                        message.setDoSomething("房间名已存在");
                        line = mapper.writeValueAsString(message);
                        bw.write(line + "\n");
                        bw.flush();
                        return;
                    }
                }
            /*实例化房间*/
                room = new Room();
            /*将房主添加进房间*/
                room.addPlayers(this);
            /*设置房间名*/
                room.setRoomName(roomName);
            /*将房间添加到manager*/
                manager.newRoom(room);

                message = new Message();
                message.setForWhat(Config.NEW_ROOM_RESULT);
                message.setDoSomething(Config.SUCCESS);
                line = mapper.writeValueAsString(message);
                bw.write(line+"\n");
                bw.flush();
            } else {
            message.setDoSomething(Config.FAIL);
            line = mapper.writeValueAsString(message);
            bw.write(line + "\n");
            bw.flush();
        }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*获取某个房间数据*/
    private void get_room() {
        System.out.println("房间数据："+message.getDoSomething()+"； 数据："+message.getData());
        System.out.println("----------------------");
        try{
            /*判断用户是否登录并且没有开始游戏*/
            if (manager.getWait().contains(this)) {
                Vector<Room> rooms = manager.getRooms();
                String roomName = message.getData();
                message = new Message();
                ClassRoom classRoom = new ClassRoom();
                Vector<GamePlayer> players;
                for (Room r :
                        rooms) {
                    if (roomName.equals(r.getRoomName())) {
                        message.setForWhat(Config.GET_ROOM_RESULT);
                        message.setDoSomething(Config.SUCCESS);

                        classRoom.setRoomName(r.getRoomName());
                        classRoom.setRoom_status(r.getGameData().roomStatus);
                        players = r.getPlayers();
                        ArrayList<String> playerList = new ArrayList<String>();
                        for (GamePlayer p :
                                players) {
                            playerList.add(p.playerName);
                        }
                        classRoom.setPlayers(playerList);
                        String room_json = mapper.writeValueAsString(classRoom);
                        message.setData(room_json);
                        line = mapper.writeValueAsString(message);
                        bw.write(line + "\n");
                        bw.flush();
                        return;
                    }
                }
                /*没有这个名字的房间*/
                message.setForWhat(Config.GET_ROOM_RESULT);
                message.setDoSomething(Config.FAIL);
                line = mapper.writeValueAsString(message);
                bw.write(line + "\n");
                bw.flush();
                return;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*开始游戏*/
    private void start_game() {
        System.out.println("开始游戏："+message.getDoSomething()+"； 数据："+message.getData());
        System.out.println("----------------------");
        String roomName = message.getData();
        message = new Message();
        try{
            /*判断用户是否登录并且没有开始游戏*/
            if (manager.getWait().contains(this)) {
                /*判断房间是否存在*/
                /*判断是否是房主*/
                /*检查房间人数是否两人或以上*/
                String result = null;
                Vector<Room> rooms = manager.getRooms();
                int i = 0;
                for (i = 0; i < rooms.size(); i++) {
                    result = "房间不存在";
                    if (rooms.get(i).getRoomName().equals(roomName)) {
                        result ="你不是房主";
                        if (rooms.get(i).getPlayers().get(0).playerName.equals(playerName)) {
                            result = "房间人数至少2个";
                            if (rooms.get(i).getPlayers().size() >= 2) {
                                  /*更改每个玩家的状态为playing*/
                                manager.wait2playing(rooms.get(i).getPlayers());
                                     /*初始化游戏数据*/
                                rooms.get(i).getGameData().initGameData();
                                /*通知房间玩家开始游戏*/
                                message.setForWhat(Config.START_GAME_RESULT);
                                message.setDoSomething(Config.SUCCESS);
                                rooms.get(i).notifyAllPlayer(message);
                                break;
                            }
                        }
                    }
                }
                if (i == rooms.size()) {
                    /*不存在这个房间或者不是房主操作或者人数不够*/
                    message.setForWhat(Config.START_GAME_RESULT);
                    message.setDoSomething(Config.FAIL);
                    message.setData(result);
                    line = mapper.writeValueAsString(message);
                    bw.write(line + "\n");
                    bw.flush();
                    return;
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        }


    }
}
