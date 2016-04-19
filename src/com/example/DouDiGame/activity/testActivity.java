package com.example.DouDiGame.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.example.DouDiGame.R;
import com.example.DouDiGame.model.Gezi;
import com.example.DouDiGame.service.Play;

import java.util.List;

/**
 * Created by viv on 16-4-19.
 */
public class testActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        new AsyncTask<Void,String,Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                Play play = new Play();

                Log.i("play_init","玩家0----> "+play.get_user(0).cash+":"+play.get_user(0).assets+":"+play.get_user(0).getPosition()+":"+play.get_user(0).direction);
                Log.i("play_init","玩家1----> "+play.get_user(1).cash+":"+play.get_user(1).assets+":"+play.get_user(1).getPosition()+":"+play.get_user(1).direction);

                for (Gezi gezi :
                        play.geziList) {
                    Log.i("gezi_init","格子-----> "+gezi.userId+" : "+gezi.price);
                }


                for (int i = 0; i < 100; i++) {
                    Log.i("go_test","-----------"+i+"-----------------");
                    Log.i("go_test","第"+play.game.time+"回合");
                    Log.i("go_test","玩家："+play.game.userId+"; 位置："+play.get_user(play.game.userId).getPosition()+";" );
                    play.go();
                    Log.i("go_test","点数："+play.game.num+"; 位置: "+play.get_user(play.game.userId).getPosition()+";");

                    if(play.get_gezi(play.get_user(play.game.userId).getPosition()).userId == play.game.userId){
//                所在的地，可以追投
                        Log.i("go_test","追投前的情况： 所属:"+play.get_gezi(play.get_user(play.game.userId).getPosition()).userId+"; 价格："+play.get_gezi(play.get_user(play.game.userId).getPosition()).price);
                        Log.i("go_test","追投前，个人资产情况：现金:"+play.get_user(play.game.userId).cash+"; 资产:"+play.get_user(play.game.userId).assets);
                        String add_result = play.addTo(play.get_user(play.game.userId).getPosition(),2000);
                        Log.i("go_test","追投结果："+add_result);
                        Log.i("go_test","追投后的情况： 所属:"+play.get_gezi(play.get_user(play.game.userId).getPosition()).userId+"; 价格："+play.get_gezi(play.get_user(play.game.userId).getPosition()).price);
                        Log.i("go_test","追投后，个人资产情况：现金:"+play.get_user(play.game.userId).cash+"; 资产:"+play.get_user(play.game.userId).assets);

                    }

                    if(play.get_gezi(play.get_user(play.game.userId).getPosition()).userId == -1){
//                所在的地，可以购买
                        Log.i("go_test","该地购买前的情况："+play.get_gezi(play.get_user(play.game.userId).getPosition()).userId+": 价格:"+play.get_gezi(play.get_user(play.game.userId).getPosition()).price);
                        Log.i("go_test","购地前，个人资产情况：现金:"+play.get_user(play.game.userId).cash+"; 资产:"+play.get_user(play.game.userId).assets);
                        String buy_result = play.buy(play.get_user(play.game.userId).getPosition());
                        Log.i("go_test","购买土地情况:"+buy_result);
                        Log.i("go_test","该地购买后的情况："+play.get_gezi(play.get_user(play.game.userId).getPosition()).userId+": 价格:"+play.get_gezi(play.get_user(play.game.userId).getPosition()).price);
                        Log.i("go_test","购地后，个人资产情况：现金:"+play.get_user(play.game.userId).cash+"; 资产:"+play.get_user(play.game.userId).assets);
                    }else if(!(play.get_gezi(play.get_user(play.game.userId).getPosition()).userId == play.game.userId)){
//                别人的地，扣钱
                        Log.i("go_test","该地：所属"+play.get_gezi(play.get_user(play.game.userId).getPosition()).userId+": 价格:"+play.get_gezi(play.get_user(play.game.userId).getPosition()).price);
                        Log.i("go_test","扣钱前，个人资产情况：现金:"+play.get_user(play.game.userId).cash+"; 资产:"+play.get_user(play.game.userId).assets);
                        String consume_result = play.consume(play.get_user(play.game.userId).getPosition());
                        Log.i("go_test","扣钱结果："+consume_result);
                        Log.i("go_test","该地：所属"+play.get_gezi(play.get_user(play.game.userId).getPosition()).userId+": 价格:"+play.get_gezi(play.get_user(play.game.userId).getPosition()).price);
                        Log.i("go_test","扣钱后，个人资产情况：现金:"+play.get_user(play.game.userId).cash+"; 资产:"+play.get_user(play.game.userId).assets);

                        if(consume_result.equals("not enough")){
                            Log.i("go_test",play.game.userId+"你输了");
                            break;
                        }


                    }

                    if(play.game.userId == 1){
//                一局了，更新棋局
                        Log.i("go_test","更新前资产:--------------------");
                        Log.i("go_test","玩家0资产情况：现金:"+play.get_user(0).cash+"; 资产:"+play.get_user(0).assets);
                        Log.i("go_test","玩家1资产情况：现金:"+play.get_user(1).cash+"; 资产:"+play.get_user(1).assets);
                        String update_result= play.update();
                        Log.i("go_test",update_result);
                        if(update_result.equals("user_0 win")){
                            Log.i("go_test","user 0 你赢了");
                            Log.i("go_test","玩家0资产情况：现金:"+play.get_user(0).cash+"; 资产:"+play.get_user(0).assets);
                            Log.i("go_test","玩家1资产情况：现金:"+play.get_user(1).cash+"; 资产:"+play.get_user(1).assets);
                            break;
                        }
                        if(update_result.equals("user_1 win")){
                            Log.i("go_test","user 1 你赢了");
                            Log.i("go_test","玩家0资产情况：现金:"+play.get_user(0).cash+"; 资产:"+play.get_user(0).assets);
                            Log.i("go_test","玩家1资产情况：现金:"+play.get_user(1).cash+"; 资产:"+play.get_user(1).assets);
                            break;
                        }
                        Log.i("go_test","更新后资产:--------------------");
                        Log.i("go_test","玩家0资产情况：现金:"+play.get_user(0).cash+"; 资产:"+play.get_user(0).assets);
                        Log.i("go_test","玩家1资产情况：现金:"+play.get_user(1).cash+"; 资产:"+play.get_user(1).assets);
                        Log.i("go_test","========================================================================");
                    }


                    play.updateUserId();

                }

                Log.i("go_test","================结束=====================");




                return null;
            }
        }.execute();


    }

}