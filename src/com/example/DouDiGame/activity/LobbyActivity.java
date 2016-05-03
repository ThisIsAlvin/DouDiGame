package com.example.DouDiGame.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.DouDiGame.Config;
import com.example.DouDiGame.R;
import com.example.DouDiGame.netEntity.ClassRoom;
import com.example.DouDiGame.service.NetService;
import com.example.DouDiGame.utils.MyDisplayMetrics;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by jinyuan on 2016/4/26.
 */
public class LobbyActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private List<String> listItem1=new ArrayList<>();
    private List<String> listItem2=new ArrayList<>();
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private LobbyHandler lobbyHandler;
    private com.example.DouDiGame.netEntity.Message message;
    private ArrayList<ClassRoom> classRooms = new ArrayList<ClassRoom>();
    private  EditText nameInput;
    private Vector<com.example.DouDiGame.netEntity.Message> lobbyMasseges=new Vector<>();
    private String roomName;
    private long mExitTime;
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

        lobbyHandler =new LobbyHandler();
        NetService.getNetService().setLobbyHandler(lobbyHandler);
        message=new com.example.DouDiGame.netEntity.Message();
        message.setForWhat(Config.HALL);
        NetService.sentMessage(message);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.room_create:
                AlertDialog.Builder myDialog=new AlertDialog.Builder(this);
                myDialog.setMessage("请输入房间名：");
                nameInput=new EditText(this);
                myDialog.setView(nameInput);
                myDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        roomName = nameInput.getText().toString();
                        if (!roomName.equals(""))
                        {
                            message=new com.example.DouDiGame.netEntity.Message();
                            message.setForWhat(Config.NEW_ROOM);
                            message.setData(roomName);
                            NetService.sentMessage(message);
                            Intent roomIntent=new Intent(LobbyActivity.this,RoomActivity.class);
                            roomIntent.putExtra("roomName", roomName);
                            roomIntent.putExtra("name", getIntent().getStringExtra("name"));
                            startActivity(roomIntent);
                        }
                        else
                        {
                            AlertDialog.Builder myDialog2=new AlertDialog.Builder(LobbyActivity.this);
                            myDialog2.setMessage("房间名不能为空！");
                            myDialog2.setPositiveButton("确定",null);
                            myDialog2.show();
                        }
                    }
                });

                myDialog.show();
        }
    }

    public void onItemClick(AdapterView<?> parent,View view,int position,long id)
    {
       switch(parent.getId())
       {
           case R.id.roomList1:
               roomName=listItem1.get(position);
               break;
           case R.id.roomList2:
               roomName=listItem2.get(position);
               break;
       }

        message=new com.example.DouDiGame.netEntity.Message();
        message.setForWhat(Config.IN_ROOM);
        message.setData(roomName);
        NetService.sentMessage(message);
    }

    public boolean onKeyDown(int keyCode,KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
        {
            if ((System.currentTimeMillis() - mExitTime) > 2000)
            {
                mExitTime = System.currentTimeMillis();
                Toast.makeText(getApplicationContext(),"再按一次退出游戏",Toast.LENGTH_SHORT).show();
            }
            else
            {
                finish();
            }
        }
        return true;
    }

    class LobbyHandler extends Handler
    {
        public LobbyHandler() {}

        public LobbyHandler(Looper l)
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
                case Config.HALL_RESULT:
                    Log.i("mylog","lobby hall_result");
                    if(message.getDoSomething().equals(Config.SUCCESS))
                    {
                        try
                        {
                            ObjectMapper objectMapper=new ObjectMapper();
                            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ClassRoom.class);
                            classRooms=objectMapper.readValue(message.getData(), javaType);
                            listItem1.clear();
                            listItem2.clear();
                            for(ClassRoom room:classRooms)
                            {
                                int roomCount = listItem1.size() + listItem2.size();
                                if (listItem1.size() <= listItem2.size())
                                {
                                    listItem1.add(room.getRoomName());
                                }
                                else
                                {
                                    listItem2.add(room.getRoomName());
                                }
                            }
                            adapter1.notifyDataSetChanged();
                            adapter2.notifyDataSetChanged();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {

                    }
                    break;
                case Config.WAITER_UPDATE_HALL:
                    Log.i("mylog","lobby Config.WAITER_UPDATE_HALL");
                case Config.NEW_ROOM_RESULT:
                    message=new com.example.DouDiGame.netEntity.Message();
                    message.setForWhat(Config.HALL);
                    NetService.sentMessage(message);
                    break;
                case Config.IN_ROOM_RESULT:
                    Intent roomIntent=new Intent(LobbyActivity.this,RoomActivity.class);
                    roomIntent.putExtra("roomName",roomName);
                    roomIntent.putExtra("name",getIntent().getStringExtra("name"));
                    startActivity(roomIntent);
                    break;
                default:break;
            }

        }
    }

}