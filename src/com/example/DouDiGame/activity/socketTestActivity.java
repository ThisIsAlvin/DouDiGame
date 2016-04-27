package com.example.DouDiGame.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.example.DouDiGame.Config;
import com.example.DouDiGame.R;
import com.example.DouDiGame.netEntity.Message;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.Socket;

/**
 * Created by viv on 16-4-26.
 */
public class socketTestActivity extends Activity {

    Socket socket = null;

    BufferedWriter writer = null;

    BufferedReader reader = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_activity);


        new AsyncTask<Void,String,Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    socket = new Socket(Config.IP,Config.PORT);

                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),Config.ENCODE));

                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), Config.ENCODE));

                    ObjectMapper mapper = new ObjectMapper();

                    String line;
                    Message message;
                    /*登录*/
                    message  = new Message();
                    message.setForWhat(Config.LOGIN);
                    message.setData("大哥");
                    line = mapper.writeValueAsString(message);
                    writer.write(line + "\n");
                    writer.flush();

                    line =  reader.readLine();
                    if(line != null){
                        Log.i("viv",line);
                    }

                    /*新建房间并进入*/
                    message = new Message();
                    message.setForWhat(Config.NEW_ROOM);
                    message.setData("丽晶大宾馆");
                    line = mapper.writeValueAsString(message);
                    writer.write(line + "\n");
                    writer.flush();

                    line = reader.readLine();
                    if (line != null) {
                        Log.i("viv",line);
                    }
                    /*获取大厅数据*/
                    message = new Message();
                    message.setForWhat(Config.HALL);
                    line = mapper.writeValueAsString(message);
                    writer.write(line + "\n");
                    writer.flush();

                    line = reader.readLine();
                    if (line != null) {
                        Log.i("viv",line);
                    }

                    /*获取某个房间数据*/
                    message = new Message();
                    message.setForWhat(Config.GET_ROOM);
                    message.setData("丽晶大宾馆");
                    line = mapper.writeValueAsString(message);
                    writer.write(line + "\n");
                    writer.flush();

                    line = reader.readLine();
                    if (line != null) {
                        Log.i("viv",line);
                    }
                    /*进入某个房间*/
                    message = new Message();
                    message.setForWhat(Config.IN_ROOM);
                    message.setData("丽晶大宾馆");
                    line = mapper.writeValueAsString(message);
                    writer.write(line + "\n");
                    writer.flush();

                    line = reader.readLine();
                    if (line != null) {
                        Log.i("viv",line);
                    }





                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }

                return null;
            }
        }.execute();


    }

}
