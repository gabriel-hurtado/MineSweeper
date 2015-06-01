package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ResultGameActivity extends Activity {
    String resultOfGame;
    int time = 0;
    long TIME;
    long UseTime;
    TextView Date;
    TextView GameResult;
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
    String message;
    Date today;
    double succes;
    private Intent in;
    private Bundle gameLog;
    private SQLiteDatabase db;
    private ContentValues newRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_game);

        in = getIntent();
        message = "";
        gameLog = in.getExtras();
        timer = gameLog.getBoolean("timer");
        TIME = gameLog.getLong("TIME");
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

        UseTime = TIME/1000-time;

        succes = 100 * (totalBoxes - remainingBox) / totalBoxes;

        makeDate();

        makeLog();

        saveGameSQL();
    }

    public void makeDate() {
        today = new Date();
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

        return " User : " + user + "\n" + " Discovered Boxes : " + (totalBoxes - remainingBox) + "\n" + " Percentage of Mines " + percentage + "\n" + " Total of Mines : " + totalMines + "\n" + " Timer : " + chronometer + "\n";
    }

    public String makeLogSpecific() {
        String logSpecific = "";
        int numberOfLine = position / size + 1;
        int numberOfColumn = position % size + 1;

        if (timer && victory) {
            message = "Victory !! You had " + time + " seconds left !";
            logSpecific = " You won " + "\n" + " You had " + time + " seconds left !";
        }
        if (!timer && victory) {
            message = "Victory !!";
            logSpecific = " You won ";
        }
        if (defeatByTime) {
            message = "You ran out of time !! They were " + remainingBox + " more boxes to discover";
            logSpecific = " You ran out of time !!" + "\n" + " They were " + remainingBox + " more boxes to discover";
        }
        if (timer && !defeatByTime && !victory) {
            message = "Defeat !! They were " + remainingBox + " more boxes to discover." + "\n" + " Pump in box " + "( " + numberOfLine + ", " + numberOfColumn + " )";
            logSpecific= " You lost !! " + "\n" + " Pump in box " + "( " + numberOfLine + ", " + numberOfColumn + " )" + "\n" + " They were " + remainingBox + " more boxes to discover" + "\n" + " You had " + time + " seconds left !";
        }
        if (!victory && !timer) {
            message = "Defeat !! They were " + remainingBox + " more boxes to discover." + "\n" + " Pump in box " + "( " + numberOfLine + ", " + numberOfColumn + " )";
            logSpecific = " You lost !! " + "\n" + " Pump in box " + "( " + numberOfLine + ", " + numberOfColumn + " )" + "\n" + " They were " + remainingBox + " more boxes to discover";
        }

        return logSpecific;
    }

    public void showMail(View clickedButton) {
        final EditText et1 = (EditText) findViewById(R.id.ResultGameEditText3);
        String recipient = et1.getText().toString();

        if (recipient.equals("")) {
            Toast.makeText(ResultGameActivity.this, "You have to put a content in Email Adress", Toast.LENGTH_LONG).show();
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

    public void saveGameSQL(){
        SaveGameSQLiteHelper gsdbh =
                new SaveGameSQLiteHelper(this, "DBSaveGame", null, 1);

        db = gsdbh.getWritableDatabase();
        newRegister = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat(" -- yyyy-MM-dd HH:mm:ss");

        newRegister.put("user", user);
        newRegister.put("date", dateFormat.format(today));
        newRegister.put("succes", succes + " % finished");
        newRegister.put("discoveringBox", totalBoxes-remainingBox);
        newRegister.put("percentage", percentage);
        newRegister.put("numberMines", totalMines);
        newRegister.put("time", UseTime);
        newRegister.put("message", message);

        db.insert("SaveGame", null, newRegister);
    }

}
