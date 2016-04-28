package com.example.DouDiGame.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.DouDiGame.R;
import com.example.DouDiGame.utils.MyDisplayMetrics;

/**
 * Created by 何锦源 on 2016/4/19.
 */
public class MainActivity extends Activity implements View.OnClickListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_begin=(Button)findViewById(R.id.button_begin);
        button_begin.setOnClickListener(this);

        //获取屏幕大小并保存
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        MyDisplayMetrics.width= dm.widthPixels;
        MyDisplayMetrics.height = dm.heightPixels;
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button_begin:
                EditText nameEditText=(EditText)findViewById(R.id.name);
                String name=nameEditText.getText().toString();
                if(name.equals(""))
                {
                    AlertDialog.Builder nameAlert=new AlertDialog.Builder(MainActivity.this);
                    nameAlert.setMessage("名字不能为空");
                    nameAlert.setPositiveButton("确定",null);
                    nameAlert.setCancelable(false);
                    nameAlert.show();
                }
                else
                {
                    Intent lobbyIntent=new Intent(MainActivity.this,LobbyActivity.class);
                    lobbyIntent.putExtra("name",name);
                    startActivity(lobbyIntent);
                }
        }
    }
}