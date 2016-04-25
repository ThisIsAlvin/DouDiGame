package com.viv.server.service;

import com.viv.server.entity.PlayerStatus;
import com.viv.server.entity.Room;

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
        int i =0;
        while (true){
            try {
                System.out.println("Thread: "+currentThread().getName()+"---->"+i);
                i++;
                this.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
