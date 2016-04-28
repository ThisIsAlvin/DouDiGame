package com.example.DouDiGame.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.DouDiGame.R;
import com.example.DouDiGame.utils.MyDisplayMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinyuan on 2016/4/27.
 */
public class RoomActivity extends Activity implements View.OnClickListener{
    private List<String> listItem=new ArrayList<>();
    private MyHandler myHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        ListView listView=(ListView)findViewById(R.id.name_list);
        ViewGroup.LayoutParams params=listView.getLayoutParams();
        params.height= (int)(MyDisplayMetrics.height*0.9);
        listView.setLayoutParams(params);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.name_list_item,listItem);
        listView.setAdapter(adapter);

        Button begin=(Button)findViewById(R.id.begin);
        begin.setOnClickListener(this);

        myHandler=new MyHandler();
        MyThread myThread=new MyThread();
        new Thread(myThread).start();
    }

    public void onClick(View view)
    {

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
            super.handleMessage(msg);
            Bundle bundle=msg.getData();

        }
    }

    class MyThread implements Runnable
    {
        public void run()
        {
            Message msg=new Message();
            Bundle bundle=new Bundle();



            msg.setData(bundle);
            RoomActivity.this.myHandler.sendMessage(msg);
        }
    }
}