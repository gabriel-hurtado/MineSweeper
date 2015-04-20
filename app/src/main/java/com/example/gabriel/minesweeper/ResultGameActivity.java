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
    String resultOfGame;
    int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_game);

        in = getIntent();
        gameLog = in.getExtras();
        boolean timer = gameLog.getBoolean("timer");
        boolean victory = gameLog.getBoolean("victory");
        String user = gameLog.getString("UserName");
        int size = Integer.parseInt(gameLog.getString("size"));
        Double percentage = gameLog.getDouble("percentage");
        int remainingBox = gameLog.getInt("remainingBox");
        int remainingMine = gameLog.getInt("remainingMine");
        int totalMines =(int) (size * size * percentage);

        boolean defeatByTime = false;

        if(timer) {
            int minutes= gameLog.getInt("minutes");
            int seconds = gameLog.getInt("seconds");
            time = minutes*60+seconds;


            if(time == 0){
                defeatByTime = true;
            }
        }
        gameLog.putBoolean("defeatByTime",defeatByTime);
        gameLog.putInt("time",time);
        int boxToDiscover = size * size - remainingMine-remainingBox;

        resultOfGame = "User : " + user + "\n" + " Discovered Boxes : " + remainingBox +"\n" + " %  Discovered Mines : " + remainingMine/totalMines + " Total of Mines " + totalMines + "\n" ;

        if(timer && !defeatByTime && victory){
            resultOfGame += " You won " + "\n" + " You have been overrun " + (120-time) + " Seconds !";
        }
        if(defeatByTime) {
            resultOfGame += "You have run out of time !!" + "\n" + " We have been " + boxToDiscover +" boxes to discover";
        }
        if(timer && !defeatByTime && !victory){
            resultOfGame += " You lost !! " + "\n" + " Pump in box " + ""+ "\n" + " We have been " + boxToDiscover +" boxes to discover" + "\n" + " You have been overrun " + (120-time) + " Seconds !";
        }
        if(!victory){
            resultOfGame +=" You lost !! " + "\n" + " Pump in box " + ""+ "\n" + " We have been " + boxToDiscover +" boxes to discover" ;
        }

    }

    public void showMail (View clickedButton){
        final EditText et1 = (EditText) findViewById(R.id.ResultGameEditText3);
        String recipient = et1.getText().toString();
        // starterInfos.putString("UserName", user_name);

        if (recipient.equals("")) {
            Toast.makeText(ResultGameActivity.this, "You have to put a content inEmail Adress", Toast.LENGTH_LONG).show();
        }
        else {

            Intent testIntent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?subject=" + "Game Result" + "&body=" + resultOfGame + "&to=" + recipient);
            testIntent.setData(data);
            startActivity(testIntent);
            //cas victoire : 1)plus de mine 2)
            //defaite : 1)plus de temp 2) trouver une bombe
        }
    }

    public void showGameStarter (View clickedButton) {
        Intent in = new Intent(this, GameStarterActivity.class);
        startActivity(in);
    }

    public void quitApp (View clickedButton){
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
