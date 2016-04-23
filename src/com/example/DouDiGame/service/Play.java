package com.example.DouDiGame.service;

import android.util.Log;
import com.example.DouDiGame.model.Game;
import com.example.DouDiGame.model.Gezi;
import com.example.DouDiGame.model.User;
import com.example.DouDiGame.utils.Config;

import java.util.List;

/**
 * Created by viv on 16-4-19.
 */
public  class Play {
    public Game game;
    public List<Gezi> geziList;
    public List<User> userList;

    public Play(){
        game = new Game();
        geziList = game.geziList;
        userList = game.userList;

        initGame();
    }
/*模型的初始化信息*/
    private void initGame() {
        userList.get(0).direction=true;
        userList.get(1).direction=false;
        game.time = 0;
        game.userId = 0;


    }

    /*传入格子ID，查找对应的格子信息*/
    public Gezi get_gezi(int geziId){
        return geziList.get(geziId);
    }

    /*查询用户信息*/
    public User get_user(int userId){
        return userList.get(userId);
    }

    /*投掷色子的操作*/
    public void go(){

//        获取随机数
        game.num = (int)(1+Math.random()*(6-1+1));

//        更新角色位置信息
        userList.get(game.userId).move(game.num);



    }

    public void updateUserId(){
        //        更新操作权
        if(game.userId == 0){
            game.userId = 1;
        }else {
            game.userId = 0;
        }
    }

    /*购地操作*/
    public String buy(int geziId){
        String result = "error";
//        查看是否有足够资金
        if(userList.get(game.userId).cash < geziList.get(geziId).price){
            return result="not enough";
        }
//         查看该地是否有所属
        if(geziList.get(geziId).userId!=-1)
        {
            return result="is somebody";
        }
//        扣钱，改所属,更新资产
        userList.get(game.userId).cash = userList.get(game.userId).cash - geziList.get(geziId).price;
        geziList.get(geziId).userId = game.userId;
        userList.get(game.userId).assets = userList.get(game.userId).assets + geziList.get(geziId).price;
        return result= "succeed";
    }

    /*追投操作*/
    public String addTo(int geziId,int money){
        String result = "error";
//         查看该地是否属于自己
        if(geziList.get(geziId).userId!=game.userId)
        {
            return result="is no yours";
        }
//        查看是否有足够资金
        if(userList.get(game.userId).cash < money){
            return result="not enough";
        }

//       扣钱， 追投,更新资产
        userList.get(game.userId).cash = userList.get(game.userId).cash - money;
        geziList.get(geziId).price = geziList.get(geziId).price+money;
        userList.get(game.userId).assets = userList.get(game.userId).assets + money;

        return result = "succeed";
    }

    /*踩到对方地块的操作*/
    public String consume(int geziId){
        String result = "error";
        long money = (long) (geziList.get(geziId).price * Config.consume_percent);
        if(userList.get(game.userId).cash<money){
//            现金不够
//         直接破产
            return result = "not enough";

        }
//        扣钱，对方加钱
        userList.get(game.userId).cash = userList.get(game.userId).cash - money;
        userList.get(geziList.get(geziId).userId).cash = userList.get(geziList.get(geziId).userId).cash +money;

        return result = "succeed";
    }

    /*每一局的更新操作*/
    public String update(){
        String result = "error";
//        棋局结束判定胜负
        if(game.time>=Config.game_time_end){

            if(userList.get(0).assets+userList.get(0).cash>userList.get(1).assets+userList.get(1).cash){
                return result="user_0 win";
            }else if (userList.get(0).assets+userList.get(0).cash<userList.get(1).assets+userList.get(1).cash){
                return result="user_1 win";
            }
        }
//        棋局未结束，更新时间进度，地价信息,更新资产
        game.time++;
        for (Gezi gezi :
                geziList) {
            gezi.price = (long) (gezi.price + gezi.price * Config.gezi_price_percent);

        }
        userList.get(0).assets += userList.get(0).assets*Config.gezi_price_percent;
        userList.get(1).assets += userList.get(1).assets*Config.gezi_price_percent;

        return result = "succeed";
    }

}
