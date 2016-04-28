package com.viv.server;

/**
 * Created by viv on 16-4-27.
 */
public class Config {

    /*网络环境信息*/
    /*ip地址*/
    public static final String IP = "192.168.1.103";
    /*端口*/
    public static final Integer PORT = 10801;
    /*编码类型*/
    public static final String ENCODE = "UTF-8";


    /*forWhat通信数据类型标记client----->server*/
    /*登录*/
    public static final String LOGIN = "login";
    /*获取大厅数据*/
    public static final String HALL = "hall";
    /*进入房间*/
    public static final String IN_ROOM = "inRoom";
    /*创建房间*/
    public static final String NEW_ROOM = "newRoom";
    /*获取某个房间数据*/
    public static final String GET_ROOM = "getRoom";
    /*开始游戏数据初始化*/
    public static final String START_GAME = "startGame";
    /*获取游戏初始化数据*/
    public static final String START_INIT_DATA = "startInitData";
    /*投掷色子*/
    public static final String PLAY = "play";
    /*游戏操作：购买土地/追加投资/扣钱*/
    public static final String DEAL = "deal";

    /*forWhat通信数据类型标记server----->client*/
    /*登录结果通知*/
    public static final String LOGIN_STATUS = "login_status";
    /*获取大厅数据结果*/
    public static final String HALL_RESULT = "hall_result";
    /*进入房间结果*/
    public static final String IN_ROOM_RESULT = "in_room_result";
    /*创建房间结果*/
    public static final String NEW_ROOM_RESULT = "new_room_result";
    /*返回某个房间数据*/
    public static final String GET_ROOM_RESULT = "get_room_result";
    /*开始游戏数据初始化结果*/
    public static final String START_GAME_RESULT = "start_game_result";
    /*获取游戏初始化数据结果*/
    public static final String START_INIT_DATA_RESULT = "start_init_data_result";
    /*投掷色子结果*/
    public static final String PLAY_RESULT = "play_result";
    /*游戏操作结果*/
    public static final String DEAL_RESULT = "deal_result";

    /*forWhat常用的通知 server----> client*/
    /*所有等待的用户更新大厅数据*/
    public static final String WAITER_UPDATE_HALL = "waiter_update_hall";
    /*向房间的玩家发出更新房间数据的通知*/
    public static final String ROOMER_UPDATE_ROOM = "roomer_update_room";


    /*doSomething常用通知*/
    /*成功*/
    public static final String SUCCESS = "success";
    /*失败*/
    public static final String FAIL = "fail";


    /*房间状态*/
    /*等待*/
    public static final String ROOM_WAIT = "room_wait";
    /*游戏中*/
    public static final String ROOM_PLAYING = "room_playing";

    /*角色行走方向*/
    /*右转*/
    public static final String DIRECTION_RIGHT = "direction_right";
    /*左转*/
    public static final String DIRECTION_LEFT = "direction_left";

    /*游戏的初始信息*/
    /*地图的格子数量*/
    public static final Integer GEZHI_NUM = 60;
    /*初始时，玩家的现金*/
    public static final Integer PLAYER_PRICE = 10000;
    /*初始时，格子土地的价值*/
    public static final Integer GEZHI_PRICE = 100;
    /*格子每个回合的涨幅*/
    public static final double GEZHI_RATE = 0.1;
    /*游戏最大回合数*/
    public static final Integer GAME_TIME = 30;

}
