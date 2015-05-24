package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import com.example.gabriel.minesweeper.GameFrag.CorreosListener;
import android.widget.Toast;


public class GameActivity extends FragmentActivity implements CorreosListener {
        String minutes;
        String seconds;
        Boolean finished;
        Boolean isLandscape;
        FrameLayout layoutbase;
        int heightColumn;
        private Intent in;
        private Bundle gameLog;
        private Boolean timer;
        public double percentage;
        public int size;
        public GridView gridView;
        private Intent i;
        private Boolean victory;
        private int remainingBox;
        long TIME;
        TextView timeTextView;
        TextView boxTextView;
        public tools game;
        private ClickedArray clickedButtons;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_versionfrag);





        }


    @Override
    public void onCorreoSeleccionado(ClickedArray cl) {
        boolean hayDetalle =
                (getSupportFragmentManager().findFragmentById(R.id.FrgLogGame) != null);

        if (hayDetalle) {
            LogGameFrag fgdet = (LogGameFrag) getSupportFragmentManager().findFragmentById(R.id.FrgLogGame);
           // fgdet.mostrarDetalle(cl.getPos());
        }

    }
    }


