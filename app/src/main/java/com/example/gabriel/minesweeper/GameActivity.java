package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class GameActivity extends Activity {
    private Intent in;
    private Bundle infos;
    private Boolean timer;
    private int percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        in = getIntent();
        infos = in.getExtras();
        timer = infos.getBoolean("timer");
        percentage = infos.getInt("percentage");


    }



}
