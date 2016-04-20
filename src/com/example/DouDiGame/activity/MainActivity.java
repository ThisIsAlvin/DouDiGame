package com.example.DouDiGame.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.DouDiGame.R;

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
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button_begin:
                Intent gameIntent=new Intent(MainActivity.this,GameActivity.class);
                startActivity(gameIntent);
        }
    }
}