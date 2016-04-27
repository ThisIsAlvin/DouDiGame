package com.viv.server.controller;


import com.viv.server.service.GamePlayer;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by viv on 16-4-25.
 */
public class ServerListener extends  Thread {
    @Override
    public void run() {
        System.out.println("Server is starting now !");
        try{
            ServerSocket severSocket = new ServerSocket(10801);
            while (true){
                Socket socket = severSocket.accept();
                JOptionPane.showMessageDialog(null,"有客户机连接本机108108端口");
                /*实例化一个GamePlayer,启动这个子线程,将这个Gameplayer添加到SocketManager中管理*/
                GamePlayer gamePlayer = new GamePlayer(socket);
                gamePlayer.start();
                SocketManager.getSocketManager().connect(gamePlayer);

            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
