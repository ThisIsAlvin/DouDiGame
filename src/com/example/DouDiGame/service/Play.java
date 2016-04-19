package com.example.DouDiGame.service;

import com.example.DouDiGame.model.Game;
import com.example.DouDiGame.model.Gezi;

import java.util.List;

/**
 * Created by viv on 16-4-19.
 */
public class Play {
    public Game game;
    public List<Gezi> geziList;

    public Play(Game game){
        this.game = game;
        geziList = game.geziList;
    }

    /*传入格子ID，查找对应的格子信息*/
    public Gezi get_gezi(String geziId){

        for (Gezi gezi :
                geziList) {
            if(gezi.geziId==geziId){
                return gezi;
            }
        }
        return null;
    }

    public void go(){

    }

}
hahha
