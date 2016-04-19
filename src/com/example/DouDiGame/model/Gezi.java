package com.example.DouDiGame.model;

import com.example.DouDiGame.utils.Config;

/**
 * Created by viv on 16-4-19.
 */
public class Gezi {
    /*标记所属：-1表示没有玩家，0表示属于玩家0,1表示属于玩家1*/
    public int userId=-1;
    /*标记价格*/
    public long price = Config.start_gezi_price;


}
