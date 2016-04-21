package com.example.DouDiGame.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DouDiGame.R;
import com.example.DouDiGame.model.User;
import com.example.DouDiGame.service.Play;

import java.util.logging.Handler;

/**
 * Created by 何锦源 on 2016/4/19.
 */
public class GameActivity extends Activity implements View.OnClickListener
{
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private final int COL_COUNT=19;//列数
    private final int ROW_COUNT=13;//行数
    private TableLayout tableLayout;
    private ImageView head;
    private TextView name;
    private TextView points;
    private Button begin;
    private TextView cash;
    private TextView assets;
    private EditText money_input;
    private ImageView[] imageViews=new ImageView[60];
    private static Play play;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tableLayout=(TableLayout)findViewById(R.id.table);
        head=(ImageView)findViewById(R.id.head);
        name=(TextView)findViewById(R.id.name);
        points=(TextView)findViewById(R.id.points);
        begin=(Button)findViewById(R.id.begin);
        cash=(TextView)findViewById(R.id.cash);
        assets=(TextView)findViewById(R.id.assets);
        Log.i("log",name.getId()+"");

        play=new Play();
        begin.setOnClickListener(this);

        //获取屏幕的宽和高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels-20;//因为margin=10
        int height = dm.heightPixels-20;

        //生成ROW_COUNT行，COL_COUNT列的表格
        int idNumberTop=0;
        int idNumberLeft=59;
        int idNumberRight=19;
        int idNumberBottom=48;
        for(int row=0;row<ROW_COUNT;row++)
        {
            TableRow tableRow=new TableRow(this);
            tableRow.setId(Integer.valueOf(row));
            //如果是第一行
            for(int col=0;col<COL_COUNT;col++)
            {
                //tv用于显示
                ImageView tv=new ImageView(this);;
                if(row==0)
                {
                    tv.setId(Integer.valueOf(idNumberTop));
                    tv.setImageResource(R.drawable.land);
                    imageViews[idNumberTop]=tv;
                    idNumberTop++;
                }
                //如果是最后一行
                else if(row==ROW_COUNT-1)
                {
                    tv.setId(Integer.valueOf(idNumberBottom));
                    tv.setImageResource(R.drawable.land);
                    imageViews[idNumberBottom]=tv;
                    idNumberBottom--;
                }
                //如果是第一列
                else if(col==0)
                {
                    tv.setId(Integer.valueOf(idNumberLeft));
                    tv.setImageResource(R.drawable.land);
                    imageViews[idNumberLeft]=tv;
                    idNumberLeft--;
                }
                //如果是最后一列
                else if(col==COL_COUNT-1)
                {
                    tv.setId(Integer.valueOf(idNumberRight));
                    tv.setImageResource(R.drawable.land);
                    imageViews[idNumberRight]=tv;
                    idNumberRight++;
                }
                tv.setMaxHeight((height-35) / ROW_COUNT);
                tv.setMaxWidth((int) (width * 0.9 / COL_COUNT));
                tv.setAdjustViewBounds(true);
                tableRow.addView(tv);
            }
            //新建的TableRow添加到TableLayout
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(WC, WC));
        }

        updateFrame();
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.begin:
                play.go();
              //  所在的地，可以追投
                if(play.get_gezi(play.get_user(play.game.userId).getPosition()).userId == play.game.userId)
                {
                    AlertDialog.Builder gameDialog=new AlertDialog.Builder(GameActivity.this);
                    money_input=new EditText(this);
                    gameDialog.setTitle("游戏提示");
                    gameDialog.setIcon(R.drawable.avatar1_1);
                    gameDialog.setMessage("输入追投金额：");
                    gameDialog.setView(money_input);
                    gameDialog.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(!money_input.getText().toString().equals(""))
                            {
                                play.addTo(play.get_user(play.game.userId).getPosition(),Integer.parseInt(money_input.getText().toString()));
                            }

                            play.updateUserId();
                            updateFrame();
                        }
                    });
                    gameDialog.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                            play.updateUserId();
                            updateFrame();
                        }
                    });
                    gameDialog.show();
                }
                //所在的地，可以购买
               else if(play.get_gezi(play.get_user(play.game.userId).getPosition()).userId == -1)
                {
                    AlertDialog.Builder gameDialog=new AlertDialog.Builder(GameActivity.this);
                    money_input=new EditText(this);
                    gameDialog.setTitle("游戏提示");
                    gameDialog.setIcon(R.drawable.avatar1_1);
                    gameDialog.setMessage("是否购入?");
                    gameDialog.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            play.buy(play.get_user(play.game.userId).getPosition());
                            play.updateUserId();
                            updateFrame();
                        }
                    });
                    gameDialog.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            play.updateUserId();
                            updateFrame();
                        }
                    });
                    gameDialog.show();
                }
                //别人的地，扣钱
                else if(!(play.get_gezi(play.get_user(play.game.userId).getPosition()).userId == play.game.userId))
                {
                    AlertDialog.Builder gameDialog=new AlertDialog.Builder(GameActivity.this);
                    money_input=new EditText(this);
                    gameDialog.setTitle("游戏提示");
                    gameDialog.setIcon(R.drawable.avatar1_1);
                    gameDialog.setMessage("别人的地，扣钱");
                    gameDialog.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            play.consume(play.get_user(play.game.userId).getPosition());
                            play.updateUserId();
                            updateFrame();
                        }
                    });
                    gameDialog.show();
                }
                if(play.game.userId == 1)
                {
                    String result =play.update();
                    if(result.equals("user_0 win")||result.equals("user_1 win"))
                    {

                        AlertDialog.Builder gameDialog=new AlertDialog.Builder(GameActivity.this);
                        money_input=new EditText(this);
                        gameDialog.setTitle("游戏提示");
                        gameDialog.setIcon(R.drawable.avatar1_1);
                        gameDialog.setMessage(result);
                        gameDialog.setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                finish();
                            }
                        });
                        gameDialog.show();
                    }
                }
        }
    }

  /*  public void onClick(DialogInterface dialog,int which)
    {

        play.addTo(play.get_user(play.game.userId).getPosition(), Integer.parseInt(money_input.getText().toString()));
        updateFrame();
        play.updateUserId();
    }*/

    public Dialog onCreateDialog(Bundle save)
    {
        return  null;
    }
    public void updateFrame()
    {
        int userId=play.game.userId;
        User user=play.get_user(userId);
        name.setText(userId+"");
        cash.setText(user.cash + "");
        assets.setText(user.assets + user.cash + "");
        points.setText(play.game.num+"");
        int user0_position=play.game.userList.get(0).getPosition();
        int user1_position=play.game.userList.get(1).getPosition();
        for(int i=0;i<60;i++)
        {
            if(play.game.geziList.get(i).userId==0)
            {
                imageViews[i].setImageResource(R.drawable.occupy0);
            }
            else if(play.game.geziList.get(i).userId==1)
            {
                imageViews[i].setImageResource(R.drawable.occupy1);
            }
            else
            {
                imageViews[i].setImageResource(R.drawable.land);
            }
        }
        imageViews[user0_position].setImageResource(R.drawable.player0);
        imageViews[user1_position].setImageResource(R.drawable.player1);

        /*Log.i("aa", userId + "");
        Log.i("aa",user.cash+"");
        Log.i("aa",user.assets+"");
        Log.i("aa",userId+"");
        Log.i("aa","------------");*/
    }


}