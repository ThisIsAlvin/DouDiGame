package com.example.DouDiGame.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.DouDiGame.Config;
import com.example.DouDiGame.R;
import com.example.DouDiGame.netEntity.ClassRoom;
import com.example.DouDiGame.service.NetService;
import com.example.DouDiGame.utils.MyDisplayMetrics;


import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinyuan on 2016/4/27.
 */
public class RoomActivity extends Activity implements View.OnClickListener{
    private List<String> listItem=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private RoomHandler roomHandler;
    private com.example.DouDiGame.netEntity.Message message;
    private String roomName;
    private String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        ListView listView=(ListView)findViewById(R.id.name_list);
        ViewGroup.LayoutParams params=listView.getLayoutParams();
        params.height= (int)(MyDisplayMetrics.height*0.9);
        listView.setLayoutParams(params);
        adapter=new ArrayAdapter<>(this,R.layout.name_list_item,listItem);
        listView.setAdapter(adapter);

        Button begin=(Button)findViewById(R.id.begin);
        begin.setOnClickListener(this);

        roomName=getIntent().getStringExtra("roomName");
        name=getIntent().getStringExtra("name");
        roomHandler =new RoomHandler();
        NetService.getNetService().setRoomHandle(roomHandler);
        message=new com.example.DouDiGame.netEntity.Message();
        message.setForWhat(Config.GET_ROOM);
        message.setData(roomName);
        NetService.sentMessage(message);


    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.begin:
                message=new com.example.DouDiGame.netEntity.Message();
                message.setForWhat(Config.START_GAME);
                message.setData(roomName);
                NetService.sentMessage(message);
        }

    }

    public boolean onKeyDown(int keyCode,KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
        {
            message=new com.example.DouDiGame.netEntity.Message();
            message.setForWhat(Config.OUT_ROOM);
            NetService.sentMessage(message);
        }
        return super.onKeyDown(keyCode, event);
    }


    class RoomHandler extends Handler
    {
        public RoomHandler()
        {

        }
        public RoomHandler(Looper l)
        {
            super(l);
        }
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            Bundle bundle=msg.getData();
            message=(com.example.DouDiGame.netEntity.Message)bundle.get("message");
            switch (message.getForWhat())
            {
                case Config.IN_ROOM_RESULT:
                    if(message.getDoSomething().equals(Config.SUCCESS))
                    {
                        message=new com.example.DouDiGame.netEntity.Message();
                        message.setForWhat(Config.GET_ROOM);
                        message.setData(roomName);
                        NetService.sentMessage(message);
                    }
                    else
                    {

                    }
                    break;
                case Config.GET_ROOM_RESULT:
                    if(message.getDoSomething().equals(Config.SUCCESS))
                    {
                        ObjectMapper mapper=new ObjectMapper();
                        try
                        {
                            ClassRoom room=mapper.readValue(message.getData(), ClassRoom.class);
                            listItem.clear();
                            for(String players:room.getPlayers())
                            {
                                listItem.add(players);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {

                    }
                    break;
                case Config.ROOMER_UPDATE_ROOM:
                    message=new com.example.DouDiGame.netEntity.Message();
                    message.setForWhat(Config.GET_ROOM);
                    message.setData(roomName);
                    NetService.sentMessage(message);
                    break;
                case Config.START_GAME_RESULT:
                    if(message.getDoSomething().equals(Config.SUCCESS))
                    {
                        Intent intent=new Intent(RoomActivity.this,GameActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("roomName",roomName);
                        startActivity(intent);
                    }
                    else
                    {
                        AlertDialog.Builder dialog=new AlertDialog.Builder(RoomActivity.this);
                        dialog.setMessage(message.getData());
                        dialog.setPositiveButton("确定",null);
                        dialog.show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}