package com.example.gabriel.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.TextView;

public class LogGameFrag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_log_game, container, false);
    }

    public void showGeneralInfo(Intent i) {
        TextView txtGeneralInfo = (TextView) getView().findViewById(R.id.GeneralInfo);
        Bundle gameLog = i.getExtras();

        String user = gameLog.getString("UserName");
        int size = Integer.parseInt(gameLog.getString("size"));
        double percentage = gameLog.getDouble("percentage");
        int totalBoxes = (int) ((size * size) - (size * size * percentage) + 1.0);
        int totalMines = (int) (size * size * percentage);

        String GeneralInfo = " User : " + user + " Number of Boxes : " + totalBoxes + " Percentage of Mines : " + percentage + " Number of Mines : " + totalMines;
        txtGeneralInfo.setText(GeneralInfo);
    }

    public void showBoxInfo(ClickedArray cl) {
        TextView txtGeneralInfo = (TextView) getView().findViewById(R.id.SpecificInfo);
        txtGeneralInfo.setText(cl.boxesInfo());
    }

}

