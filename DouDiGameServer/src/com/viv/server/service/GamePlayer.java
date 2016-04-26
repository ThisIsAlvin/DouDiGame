package com.viv.server.service;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.viv.server.entity.Message;
import com.viv.server.entity.PlayerStatus;
import com.viv.server.entity.Room;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.Socket;

/**
 * Created by viv on 16-4-25.
 */
public class GamePlayer extends Thread{
    Socket socket;
    PlayerStatus playerStatus;
    Room room;

    public GamePlayer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        /*游戏操作*/
        BufferedReader br;
        ObjectMapper mapper;
        Message message;
        while (true){
            try {
                 br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                String line = null;
                System.out.println("------------ready for accept-----------");
                while ((line = br.readLine()) != null){
                    mapper = new ObjectMapper();
                    message = mapper.readValue(line,Message.class);
                    System.out.println("我的名字是："+message.getFoWhat());
                }

                this.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
