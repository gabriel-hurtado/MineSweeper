package com.example.gabriel.minesweeper;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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

    public void showMail (View clickedButton){
        final EditText et1 = (EditText) findViewById(R.id.ResultGameEditText1);
        String recipient = et1.getText().toString();
        // starterInfos.putString("UserName", user_name);

        if (recipient.equals("")) {
            Toast.makeText(ResultGameActivity.this, "You have to put a content inEmail Adress", Toast.LENGTH_LONG).show();
        }
        else {
            startActivity(in);
        }
        in = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:123456789"));
        in.putExtra("sms_body","Hola");
        startActivity(in);
    }

    public void showGameStarter (View clickedButton) {
        Intent in = new Intent(this, GameStarterActivity.class);
        startActivity(in);
    }

    public void quitApp (View clickedButton){
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
