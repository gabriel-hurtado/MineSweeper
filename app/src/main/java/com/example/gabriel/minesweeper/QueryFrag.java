package com.example.gabriel.minesweeper;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QueryFrag extends ListFragment {

    SaveGameSQLiteHelper usdbh;
    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.frag_query, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        usdbh = new SaveGameSQLiteHelper(getActivity(), "DBSaveGame", null, 1);
        db = usdbh.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM SaveGame", null);
        String[] from = new String[]{"user", "date", "succes"};
        int[] to = new int[]{R.id.user_entry, R.id.date_entry, R.id.succes_entry};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.frag_list,
                cursor,
                from,
                to);

        this.setListAdapter(adapter);
    }

    public void showMainMenu(View clickedButton) {
        Intent in = new Intent(getActivity(), MainMenu.class);
        startActivity(in);
    }


    //public void onListItemClick(ListView l, View v, int position, long id) {}
}
