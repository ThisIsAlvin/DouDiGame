package com.example.DouDiGame.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import com.example.DouDiGame.R;
import com.example.DouDiGame.model.Message;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
                    socket = new Socket("192.168.1.103",10801);

                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));

                    Message message = new Message();
                    message.setFoWhat("登录");
                    message.setDoSomething("我来了");
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(message);

                    for(int i=0; i<10;i++){
                        writer.write(json+"\n");
                        writer.flush();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        }.execute();


    }

}
