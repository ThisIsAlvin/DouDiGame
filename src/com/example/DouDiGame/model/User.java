package com.example.DouDiGame.model;

import com.example.DouDiGame.utils.Config;

/**
 * Created by viv on 16-4-19.
 */
public class User {
    /*角色所有现金*/
    public long cash;
    /*角色所有资产*/
    public long assets;
    /*角色所处位置*/
    private int position;
    /*角色行走方向*/
    public boolean direction = true;

    public User(){
        cash = Config.start_cash;
        this.assets = 0;
    }

    /*移动（距离，方向）*/
    public void move(int distance){
        if(direction){
//            顺时针方向
            if(Config.gezi_total > position+distance){
//                够走
                position = position+distance;
            }else{
//                不够走
                int i = Config.gezi_total - position;
                position = distance -i;
            }
        }else{
//            逆时针方向
            if(0 <= position-distance){
//                够走
                position = position-distance;
            }else{
//                不够走
//                int i = position;
                position = Config.gezi_total + position-distance;
            }

        }
    }

    public int getPosition(){
        return position;
    }
    public void setPosition(int position){

        this.position = position;
    }
}
