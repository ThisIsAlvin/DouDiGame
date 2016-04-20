package com.example.DouDiGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.DouDiGame.activity.testActivity;

import com.example.DouDiGame.activity.MainActivity;

/**
 * Created by 何锦源 on 2016/4/19.
 */
public class MyActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa);
        Intent MyIntent=new Intent(MyActivity.this,MainActivity.class);
        startActivity(MyIntent);

    }
}