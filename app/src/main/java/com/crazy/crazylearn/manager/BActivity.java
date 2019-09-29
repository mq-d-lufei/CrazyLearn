package com.crazy.crazylearn.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        intent.setClass(this, MyActivity.class);
        intent.setAction("android.intent.action.myactivity");

        startActivity(intent);
    }
}
