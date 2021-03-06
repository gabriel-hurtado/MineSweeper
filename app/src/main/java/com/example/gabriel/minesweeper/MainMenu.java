package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("LOGOUT", false)) {
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void showHelp(View clickedButton) {
        Intent in = new Intent(this, HelpActivity.class);
        startActivity(in);
    }

    public void showGameStarter(View clickedButton) {
        Intent in = new Intent(this, GameStarterActivity.class);
        startActivity(in);
    }

    public void showSaveGame(View clickedButton) {
        Intent in = new Intent(this, AccesDBActivity.class);
        startActivity(in);
    }

    public void quitApp(View clickedButton) {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}