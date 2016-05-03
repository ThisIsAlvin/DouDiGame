package com.example.DouDiGame.service;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.DouDiGame.Config;
import com.example.DouDiGame.activity.RoomActivity;
import com.example.DouDiGame.netEntity.Message;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by jinyuan on 2016/5/2.
 */
public class NetService
{
    private Socket socket;
    private static BufferedReader br;
    private static BufferedWriter bw;
    private Handler roomHandle;
    private Handler lobbyHandler;
    private Handler mainHandler;
    private Handler gameHandler;
    private static ObjectMapper mapper=new ObjectMapper();
    private static final NetService netService=new NetService();

    public static NetService getNetService()
    {
        return netService;
    }
    private NetService()
    {
        connet();
    }

    private void connet()
    {
        try
        {
            socket=new Socket(Config.IP,Config.PORT);
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        mapper=new ObjectMapper();
        new Thread(new MyThread()).start();
    }
    public static void sentMessage(Message message)
    {
        String line;
        try
        {
            line=mapper.writeValueAsString(message);
            bw.write(line+"\n");
            bw.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setRoomHandle(Handler roomHandle) {
        this.roomHandle = roomHandle;
    }

    public void setLobbyHandler(Handler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
    }

    public void setMainHandler(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

    public void setGameHandler(Handler gameHandler) {
        this.gameHandler = gameHandler;
    }

    class MyThread implements Runnable
    {
        private Message message;
        private android.os.Message msg;
        private String msgLine;
        private Bundle bundle;
        public void run()
        {
            try
            {
                while(true)
                {
                    msgLine=br.readLine();
                    Log.i("mylog",msgLine);
                    if(msgLine!=null)
                    {
                        msg=new android.os.Message();
                        message=mapper.readValue(msgLine, Message.class);
                        bundle=new Bundle();
                        bundle.putSerializable("message",message);
                        msg.setData(bundle);
                        Log.i("mylog", "netservice:"+message.getForWhat());
                        switch(message.getForWhat())
                        {
                            case Config.LOGIN_STATUS:{
                            /*登录操作处理*/
                                if(mainHandler!=null)
                                {
                                    mainHandler.sendMessage(msg);
                                }
                                break;
                            }
                            case Config.HALL_RESULT:{
                            /*获取大厅数据*/
                                if(lobbyHandler!=null)
                                {
                                    lobbyHandler.sendMessage(msg);
                                }
                                break;
                            }
                            case Config.IN_ROOM_RESULT:{
                            /*进入房间*/
                                if(lobbyHandler!=null)
                                {
                                    lobbyHandler.sendMessage(msg);
                                }
                                break;
                            }
                            case Config.NEW_ROOM_RESULT:{
                            /*创建房间*/
                                if(lobbyHandler!=null)
                                {
                                    lobbyHandler.sendMessage(msg);
                                }
                                break;
                            }
                            case Config.GET_ROOM_RESULT:{
                            /*获取某个房间数据*/
                                if(roomHandle!=null)
                                {
                                    roomHandle.sendMessage(msg);
                                }
                                break;
                            }
                            case Config.START_GAME_RESULT:{
                            /*开始游戏数据初始化*/
                                if(roomHandle!=null)
                                {
                                    roomHandle.sendMessage(msg);
                                }
                                break;
                            }
                            case Config.START_INIT_DATA:{
                            /*获取游戏初始化数据*/
                                break;
                            }
                            case Config.OUT_ROOM:{
                            /*退出房间操作*/
                                break;
                            }
                            case Config.WAITER_UPDATE_HALL:{
                                if(lobbyHandler!=null)
                                {
                                    lobbyHandler.sendMessage(msg);
                                }
                                break;
                            }
                            case Config.ROOMER_UPDATE_ROOM:{
                                if(roomHandle!=null)
                                {
                                    roomHandle.sendMessage(msg);
                                }
                                break;
                            }
                            default:
                                break;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
