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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.DouDiGame.R;
import com.example.DouDiGame.utils.MyDisplayMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinyuan on 2016/4/26.
 */
public class LobbyActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private List<String> listItem1=new ArrayList<>();
    private List<String> listItem2=new ArrayList<>();
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private MyHandler myHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        Button roomCreate=(Button)findViewById(R.id.room_create);
        roomCreate.setOnClickListener(this);
        ListView roomList1=(ListView)findViewById(R.id.roomList1);
        ViewGroup.LayoutParams params1=roomList1.getLayoutParams();
        params1.height=(int)(MyDisplayMetrics.height*0.9);
        roomList1.setLayoutParams(params1);
        adapter1=new ArrayAdapter<>(this,R.layout.room_list_item,listItem1);
        roomList1.setAdapter(adapter1);
        roomList1.setOnItemClickListener(this);

        ListView roomList2=(ListView)findViewById(R.id.roomList2);
        roomList2.setLayoutParams(params1);
        adapter2=new ArrayAdapter<>(this,R.layout.room_list_item,listItem2);
        roomList2.setAdapter(adapter2);
        roomList2.setOnItemClickListener(this);

        myHandler=new MyHandler();
        MyThread myThread=new MyThread();

    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.room_create:
                int roomCount=listItem1.size()+listItem2.size();
                if(listItem1.size()<=listItem2.size())
                {
                    listItem1.add("房间"+(roomCount+1));
                    adapter1.notifyDataSetChanged();
                }
                else
                {
                    listItem2.add("房间"+(roomCount+1));
                    adapter2.notifyDataSetChanged();
                }
        }
    }

    public void onItemClick(AdapterView<?> parent,View view,int position,long id)
    {
        String roomName="";
       switch(parent.getId())
       {
           case R.id.roomList1:
               roomName=listItem1.get(position);
               break;
           case R.id.roomList2:
               roomName=listItem2.get(position);
               break;
       }
        Intent roomIntent=new Intent(this,RoomActivity.class);
        roomIntent.putExtra("roomName",roomName);
        roomIntent.putExtra("name",getIntent().getStringExtra("name"));
        startActivity(roomIntent);
    }

    class MyHandler extends Handler
    {
        public MyHandler()
        {

        }

        public MyHandler(Looper l)
        {
            super(l);
        }

        public void handleMessage(Message msg)
        {

        }
    }

    class MyThread implements Runnable
    {
        public void run()
        {

        }
    }
}