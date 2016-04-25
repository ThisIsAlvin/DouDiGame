package com.viv.server.controller;

/**
 * Created by viv on 16-4-25.
 */
public class StartServer {

    public static void main(String[] args){
        new ServerListener().start();
    }
}
