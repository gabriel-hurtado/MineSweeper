package com.example.gabriel.minesweeper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class RegisterFrag extends Fragment {

    private SaveGameSQLiteHelper gsdbh;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Button showQueryResultButton;
    private TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_register, container, false);
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        if (!tabletSize) {
            showQueryResultButton = (Button) v.findViewById(R.id.showQueryResult);

            showQueryResultButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg) {
                    Intent i = new Intent(getActivity(), AccesDBActivity.class);
                    startActivity(i);
                }
            });
        }

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showDetail(String id) {
        gsdbh = new SaveGameSQLiteHelper(getActivity(), "DBSaveGame", null, 1);
        db = gsdbh.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM SaveGame", null);

        if(cursor.moveToFirst()) {
            do {
                if ( cursor.getString(0).equals(id)){
                    String message = "";
                    tv = (TextView) getView().findViewById(R.id.DetailGame);

                    message += "User : " + cursor.getString(1)+ "\n"
                            + "Date : " + cursor.getString(2)+ "\n"
                            + "Discovering Box : " + cursor.getString(4)+ "\n"
                            + "Percentage of mines : " + cursor.getString(5)+ "\n"
                            + "Number of Mines : " + cursor.getString(6)+ "\n"
                            + "Used time : " + cursor.getString(7) + " seconds" + "\n"
                            + cursor.getString(8);
                    tv.setText(message);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

}
