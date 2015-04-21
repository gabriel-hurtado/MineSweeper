package com.example.gabriel.minesweeper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class ResultGameActivity extends ActionBarActivity {
    String resultOfGame;
    int time = 0;
    TextView Date;
    TextView GameResult;
    private Intent in;
    private Bundle gameLog;

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
        gameLog.putBoolean("defeatByTime", defeatByTime);
        gameLog.putInt("time", time);
        int boxToDiscover = size * size - remainingMine - remainingBox;
        int numberOfLine = position / size + 1;
        int numberOfColumn = position % size + 1;


        resultOfGame = "User : " + user + "\n" + " Discovered Boxes : " + (totalBoxes - remainingBox) + "\n" + " Percentage Discovered Mines : " + remainingMine / totalMines + "\n" + " Total of Mines " + totalMines + "\n";

        if (timer && !defeatByTime && victory) {
            resultOfGame += " You won " + "\n" + " You have been overrun " + (120 - time) + " Seconds !";
        }
        if (defeatByTime) {
            resultOfGame += "You have run out of time !!" + "\n" + " We have been " + remainingBox + " boxes to discover";
        }
        if (timer && !defeatByTime && !victory) {
            resultOfGame += " You lost !! " + "\n" + " Pump in box " + "( " + numberOfLine + ", " + numberOfColumn + " )" + "\n" + " We have been " + remainingBox + " boxes to discover" + "\n" + " You have been overrun " + (120 - time) + " Seconds !";
        }
        if (!victory) {
            resultOfGame += " You lost !! " + "\n" + " Pump in box " + "( " + numberOfLine + ", " + numberOfColumn + " )" + "\n" + " We have been " + remainingBox + " boxes to discover";
        }

        GameResult = (TextView) findViewById(R.id.GameResult);

        GameResult.setText(resultOfGame);


    }


    public void showMail(View clickedButton) {


        final EditText et1 = (EditText) findViewById(R.id.ResultGameEditText3);
        String recipient = et1.getText().toString();

        if (recipient.equals("")) {
            Toast.makeText(ResultGameActivity.this, "You have to put a content inEmail Adress", Toast.LENGTH_LONG).show();
        } else {
            Date = (TextView) findViewById(R.id.Datetv);
            String time = Date.getText().toString();

            GameResult = (TextView) findViewById(R.id.Datetv);
            String resultOfGame = Date.getText().toString();

            Intent testIntent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?subject=" + time + "&body=" + resultOfGame + "&to=" + recipient);
            testIntent.setData(data);
            startActivity(testIntent);
        }
    }

    public void showGameStarter(View clickedButton) {
        Intent in = new Intent(this, GameStarterActivity.class);
        startActivity(in);
    }

    public void quitApp(View clickedButton) {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
