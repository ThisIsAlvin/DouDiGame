package com.example.DouDiGame.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

/**
 * Created by 何锦源 on 2016/4/19.
 */
public class GameActivity extends Activity implements View.OnClickListener,DialogInterface.OnClickListener
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
                    idNumberTop++;
                }
                //如果是最后一行
                else if(row==ROW_COUNT-1)
                {
                    tv.setId(Integer.valueOf(idNumberBottom));
                    tv.setImageResource(R.drawable.land);
                    idNumberBottom--;
                }
                //如果是第一列
                else if(col==0)
                {
                    tv.setId(Integer.valueOf(idNumberLeft));
                    tv.setImageResource(R.drawable.land);
                    idNumberLeft--;
                }
                //如果是最后一列
                else if(col==COL_COUNT-1)
                {
                    tv.setId(Integer.valueOf(idNumberRight));
                    tv.setImageResource(R.drawable.land);
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

    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.begin:
                AlertDialog.Builder gameDialog=new AlertDialog.Builder(GameActivity.this);
                money_input=new EditText(this);
                gameDialog.setTitle("游戏提示");
                gameDialog.setIcon(R.drawable.avatar1_1);
                gameDialog.setMessage("是否购入?");
                gameDialog.setView(money_input);
                gameDialog.setPositiveButton("确定", this);
                gameDialog.setNegativeButton("取消",this);
                gameDialog.show();
        }
    }

    public void onClick(DialogInterface dialog,int which)
    {
        Log.i("mylog",":"+which);
    }
}