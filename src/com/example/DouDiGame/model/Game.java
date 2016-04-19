package com.example.DouDiGame.model;

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
}
