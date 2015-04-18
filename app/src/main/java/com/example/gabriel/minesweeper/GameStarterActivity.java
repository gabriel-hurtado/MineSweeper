package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class GameStarterActivity extends Activity {
    private Intent in = new Intent(this, GameActivity.class);
    private Bundle starterInfos = new Bundle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_starter);
    }

    /*In the class with the help checkbox
     starterInfos.putBoolean("timer", boolean value) */

        /*In the class with the help percentages handler
     starterInfos.putBoolean("percentage", int value) */

    public void showGame (View clickedButton) {
        in.putExtras(starterInfos);
        startActivity(in);
    }
}
