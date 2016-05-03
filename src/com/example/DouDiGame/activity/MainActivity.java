package com.example.DouDiGame.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.DouDiGame.Config;
import com.example.DouDiGame.R;
import com.example.DouDiGame.service.NetService;
import com.example.DouDiGame.utils.MyDisplayMetrics;


/**
 * Created by 何锦源 on 2016/4/19.
 */
public class MainActivity extends Activity implements View.OnClickListener
{
    private MainHandler mainHandler;
    private String name;
    private com.example.DouDiGame.netEntity.Message message;
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
        mainHandler=new MainHandler();
        new Thread(new MyThread()).start();
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button_begin:
                EditText nameEditText=(EditText)findViewById(R.id.name);
                name=nameEditText.getText().toString();
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
                    message=new com.example.DouDiGame.netEntity.Message();
                    message.setForWhat(Config.LOGIN);
                    message.setData(name);
                    NetService.sentMessage(message);
                }
        }
    }

    class MainHandler extends Handler
    {
        public MainHandler(){}

        public MainHandler(Looper l)
        {
            super(l);
        }

        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            Bundle bundle=msg.getData();
            message=(com.example.DouDiGame.netEntity.Message)bundle.get("message");
            if(message.getDoSomething().equals(Config.SUCCESS))
            {
                Intent intent=new Intent(MainActivity.this,LobbyActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    class MyThread implements Runnable
    {
        public void run()
        {
            NetService netService=NetService.getNetService();
            netService.setMainHandler(mainHandler);
        }
    }
}