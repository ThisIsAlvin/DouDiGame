package com.example.DouDiGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.DouDiGame.activity.testActivity;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent = new Intent(this,testActivity.class);
        startActivity(intent);
    }
}
