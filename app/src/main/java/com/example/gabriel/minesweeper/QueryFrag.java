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
import android.widget.Button;
import android.widget.ListView;

public class QueryFrag extends ListFragment {

    private SaveGameSQLiteHelper gsdbh;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Button showMainMenuButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_query, container, false);
        showMainMenuButton = (Button) v.findViewById(R.id.showMainMenu);

        showMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                Intent i = new Intent(getActivity(), MainMenu.class);
                startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gsdbh = new SaveGameSQLiteHelper(getActivity(), "DBSaveGame", null, 1);
        db = gsdbh.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM SaveGame", null);
        String[] from = new String[]{"user", "date", "succes"};
        int[] to = new int[]{R.id.user_entry, R.id.date_entry, R.id.succes_entry};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.frag_list,
                cursor,
                from,
                to);

        this.setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        RegisterFrag frag = (RegisterFrag) getFragmentManager().findFragmentById(R.id.FrgRegister);
        Intent i = new Intent (getActivity(), DetailRegisterActivity.class);
        i.putExtra("position", position);
        startActivity(i);
    }
}
