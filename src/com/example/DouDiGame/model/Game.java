package com.example.DouDiGame.model;

import com.example.DouDiGame.utils.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viv on 16-4-19.
 */
public class Game {
    /*存放格子相关的信息*/
    public List<Gezi> geziList;
    /*存放角色相关的信息*/
    public List<User> userList;
    /*存放时间进度*/
    public int time;
    /*存放操作权指向*/
    public int userId;
    /*当前产生的随机数1-6*/
    public int num;

    public Game(){
        geziList = new ArrayList<Gezi>();
        userList = new ArrayList<User>();
        User user0 = new User();
        User user1 = new User();
        user0.setPosition(0);
        user1.setPosition(1);
        userList.add(0,user0);
        userList.add(1,user1);
        for (int i = 0; i < Config.gezi_total; i++) {
            geziList.add(i,new Gezi());
        }

    }
}
