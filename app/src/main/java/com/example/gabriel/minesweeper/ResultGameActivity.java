package com.example.gabriel.minesweeper;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ResultGameActivity extends ActionBarActivity {
    private Intent in;
    private Bundle gameLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_game);
        in = getIntent();
        gameLog = in.getExtras();
        boolean timer = gameLog.getBoolean("timer");

        if(timer) {
            int minutes= gameLog.getInt("minutes");
            int seconds = gameLog.getInt("seconds");
            TextView textView = (TextView) findViewById(R.id.ResultGameTextView1);
            textView.setText("" + minutes +":"+ seconds);
        }
    }

}
