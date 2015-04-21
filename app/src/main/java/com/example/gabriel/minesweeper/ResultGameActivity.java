package com.example.gabriel.minesweeper;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class ResultGameActivity extends ActionBarActivity {    
    private Intent in;
    private Bundle gameLog;
    String resultOfGame;
    int time = 0;
    TextView Date;
    TextView GameResult;

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
        int position = gameLog.getInt("position");
        int totalBoxes = (int) ((size * size) - (size * size * percentage) + 1.0);
        int totalMines = (int) (size * size * percentage);
        boolean defeatByTime = false;

        Date today = new Date();
        Date = (TextView) findViewById(R.id.Datetv);
        DateFormat localDf = DateFormat.getDateTimeInstance(
                DateFormat.FULL,
                DateFormat.FULL, new Locale("EN", "en"));
        Date.setText(localDf.format(today));

        if (timer) {
            int minutes = gameLog.getInt("minutes");
            int seconds = gameLog.getInt("seconds");
            time = minutes * 60 + seconds;


            if (time == 0) {
                defeatByTime = true;
            }
        }

        gameLog.putInt("time",time);
        int numberOfLine = position/size + 1;
        int numberOfColumn = position%size + 1;

        String chronometer;
        if(timer){
            chronometer = "activate";
        }
        else {
            chronometer = "disable";
        }
        
        resultOfGame = "User : " + user + "\n" + " Discovered Boxes : " + (totalBoxes-remainingBox) + "\n" +" Total of Mines : " + totalMines + "\n" + " Timer : " + chronometer +"\n";
        //"Comment faire : Percentage Discovered Mines"

        if(timer && victory){
            resultOfGame += " You won " + "\n" + " It remained to you " + time + " Seconds !";
        }
        if(!timer && victory){
            resultOfGame += " You won ";
        }
        if(defeatByTime) {
            resultOfGame += " You have run out of time !!" + "\n" + " We have been " + remainingBox +" boxes to discover";
        }
        if(timer && !defeatByTime && !victory){
            resultOfGame += " You lost !! " + "\n" + " Pump in box " + "( "+ numberOfLine + ", " + numberOfColumn + " )"+ "\n" + " We have been " + remainingBox +" boxes to discover" + "\n" + " It remained to you " + time + " Seconds !";
        }
        if(!victory && !timer){
            resultOfGame +=" You lost !! " + "\n" + " Pump in box " + "( "+ numberOfLine + ", " + numberOfColumn + " )"+"\n" + " We have been " + remainingBox +" boxes to discover" ;
        }

        GameResult = (TextView) findViewById(R.id.GameResult);
        GameResult.setText(resultOfGame.replaceAll("[\r\n]+", ""));
    }

    public void showMail (View clickedButton){
        final EditText et1 = (EditText) findViewById(R.id.ResultGameEditText3);
        String recipient = et1.getText().toString();

        if (recipient.equals("")) {
            Toast.makeText(ResultGameActivity.this, "You have to put a content inEmail Adress", Toast.LENGTH_LONG).show();
        }
        else {
            Date = (TextView) findViewById(R.id.Datetv);
            String time = Date.getText().toString();
            
            Intent email = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?subject=" + time + "&body=" + resultOfGame + "&to=" + recipient);
            email.setData(data);
            startActivity(email);
        }
    }

    public void showGameStarter (View clickedButton) {
        Intent in = new Intent(this, GameStarterActivity.class);
        startActivity(in);
    }

    public void quitApp (View clickedButton) {
        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("LOGOUT", true);
        startActivity(intent);
    }

}
