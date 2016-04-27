package com.example.DouDiGame;

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
    /*开始游戏*/
    public static final String START_GAME = "startGame";
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
    /*开始游戏结果*/
    public static final String START_GAME_RESULT = "start_game_result";
    /*投掷色子结果*/
    public static final String PLAY_RESULT = "play_result";
    /*游戏操作结果*/
    public static final String DEAL_RESULT = "deal_result";


    /*doSomething常用通知*/
    /*成功*/
    public static final String SUCCESS = "success";
    /*失败*/
    public static final String FAIL = "fail";
}
