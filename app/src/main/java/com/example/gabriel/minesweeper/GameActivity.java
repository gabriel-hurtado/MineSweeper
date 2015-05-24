package com.example.gabriel.minesweeper;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.example.gabriel.minesweeper.GameFrag.GridListener;


public class GameActivity extends FragmentActivity implements GridListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_versionfrag);
    }

    @Override
    public void onBoxSelected(ClickedArray cl) {
        boolean gameInfo =(getSupportFragmentManager().findFragmentById(R.id.FrgLogGame) != null);

        if (gameInfo) {
            LogGameFrag fragLogGame = (LogGameFrag) getSupportFragmentManager().findFragmentById(R.id.FrgLogGame);
            fragLogGame.showGeneralInfo(getIntent());
            fragLogGame.showBoxInfo(cl);
        }
    }

}


