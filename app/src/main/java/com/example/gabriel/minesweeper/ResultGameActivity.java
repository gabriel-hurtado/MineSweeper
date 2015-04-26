package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class ResultGameActivity extends Activity {
    String resultOfGame;
    int time = 0;
    TextView Date;
    TextView GameResult;
    private Intent in;
    private Bundle gameLog;
    boolean timer;
    boolean victory;
    String user;
    int size;
    Double percentage;
    int remainingBox;
    int position;
    int totalBoxes;
    int totalMines;
    boolean defeatByTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_game);

        in = getIntent();
        gameLog = in.getExtras();
        timer = gameLog.getBoolean("timer");
        victory = gameLog.getBoolean("victory");
        user = gameLog.getString("UserName");
        size = Integer.parseInt(gameLog.getString("size"));
        percentage = gameLog.getDouble("percentage");
        remainingBox = gameLog.getInt("remainingBox");
        position = gameLog.getInt("position");
        totalBoxes = (int) ((size * size) - (size * size * percentage) + 1.0);
        totalMines = (int) (size * size * percentage);
        defeatByTime = false;

        if (timer) {
            int minutes = gameLog.getInt("minutes");
            int seconds = gameLog.getInt("seconds");
            time = minutes * 60 + seconds;


            if (time == 0) {
                defeatByTime = true;
            }
        }

        makeDate();

        makeLog();
    }

    public void makeDate() {
        Date today = new Date();
        Date = (TextView) findViewById(R.id.Datetv);
        DateFormat localDf = DateFormat.getDateTimeInstance(
                DateFormat.FULL,
                DateFormat.FULL, new Locale("EN", "en"));
        Date.setText(localDf.format(today));
    }

    public void makeLog(){
        resultOfGame = makeLogGeneral() + makeLogSpecific();

        GameResult = (TextView) findViewById(R.id.GameResult);
        GameResult.setText(resultOfGame.replaceAll("[\r\n]+", ""));
    }

    public String makeLogGeneral() {
        String chronometer;
        if (timer) {
            chronometer = "activate";
        } else {
            chronometer = "disable";
        }

        String logGeneral = " User : " + user + "\n" + " Discovered Boxes : " + (totalBoxes - remainingBox) + "\n" + " Percentage of Mines " + percentage + "\n" + " Total of Mines : " + totalMines + "\n" + " Timer : " + chronometer + "\n";
        return logGeneral;
    }

    public String makeLogSpecific() {
        String logSpecific = "";
        int numberOfLine = position / size + 1;
        int numberOfColumn = position % size + 1;

        if (timer && victory) {
            logSpecific = " You won " + "\n" + " It remained to you " + time + " Seconds !";
        }
        if (!timer && victory) {
            logSpecific = " You won ";
        }
        if (defeatByTime) {
            logSpecific = " You have run out of time !!" + "\n" + " We have been " + remainingBox + " boxes to discover";
        }
        if (timer && !defeatByTime && !victory) {
            logSpecific= " You lost !! " + "\n" + " Pump in box " + "( " + numberOfLine + ", " + numberOfColumn + " )" + "\n" + " We have been " + remainingBox + " boxes to discover" + "\n" + " It remained to you " + time + " Seconds !";
        }
        if (!victory && !timer) {
            logSpecific = " You lost !! " + "\n" + " Pump in box " + "( " + numberOfLine + ", " + numberOfColumn + " )" + "\n" + " We have been " + remainingBox + " boxes to discover";
        }

        return logSpecific;
    }

    public void showMail(View clickedButton) {
        final EditText et1 = (EditText) findViewById(R.id.ResultGameEditText3);
        String recipient = et1.getText().toString();

        if (recipient.equals("")) {
            Toast.makeText(ResultGameActivity.this, "You have to put a content inEmail Adress", Toast.LENGTH_LONG).show();
        } else {
            Date = (TextView) findViewById(R.id.Datetv);
            String time = Date.getText().toString();

            Intent email = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?subject=" + time + "&body=" + resultOfGame + "&to=" + recipient);
            email.setData(data);
            startActivity(email);
        }
    }

    public void showGameStarter(View clickedButton) {
        Intent in = new Intent(this, GameStarterActivity.class);
        startActivity(in);
    }

    public void quitApp(View clickedButton) {
        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("LOGOUT", true);
        startActivity(intent);
    }

}
