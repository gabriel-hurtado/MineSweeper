package com.example.gabriel.minesweeper;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class QueryFrag extends ListFragment {

    private SaveGameSQLiteHelper gsdbh;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Button showMainMenuButton;
    private SimpleCursorAdapter adapter;

    private QueryListener listener;


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
        registerForContextMenu(getListView());

        gsdbh = new SaveGameSQLiteHelper(getActivity(), "DBSaveGame", null, 1);
        db = gsdbh.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM SaveGame", null);
        String[] from = new String[]{"user", "date", "succes"};
        int[] to = new int[]{R.id.user_entry, R.id.date_entry, R.id.succes_entry};

        adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.frag_list,
                cursor,
                from,
                to);

        this.setListAdapter(adapter);
    }

    @Override
    public void onAttach(Activity ac) {
        super.onAttach(ac);
        try {
            listener = (QueryListener) ac;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(ac.toString() + " must implement OnQueryListener");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_list_view, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int order = item.getOrder();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;

        if (cursor.moveToPosition(pos)) {
            String[] args1 = new String[]{cursor.getString(1)};
            String[] args2 = new String[]{cursor.getString(1), cursor.getString(2)};

            if (order == 1){
                cursor = db.rawQuery("SELECT * FROM SaveGame WHERE user=?", args1);
                showToastQuery(cursor.getCount());
                String[] from = new String[]{"user", "date", "succes"};
                int[] to = new int[]{R.id.user_entry, R.id.date_entry, R.id.succes_entry};

                adapter = new SimpleCursorAdapter(getActivity(),
                        R.layout.frag_list,
                        cursor,
                        from,
                        to);

                this.setListAdapter(adapter);
            }

            if(order == 2){
                showToastDelete((int) db.delete("SaveGame", "user=? AND date=?", args2));
            }

            cursor.requery();
            adapter.notifyDataSetChanged();
        }
        return true;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null){
            listener.onGameSelected(String.valueOf(id));
        }
    }

    public interface QueryListener {
        void onGameSelected(String pos);
    }

    public void setQueryListener(QueryListener listener) {
        this.listener = listener;
    }


    public void showToastDelete(int i){
        if (i == 1){
            Toast.makeText(getActivity(), "End and succes of the operation", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(), "Failure of the operation", Toast.LENGTH_LONG).show();
        }
    }

    public void showToastQuery(int i){
        if (i > 0){
            Toast.makeText(getActivity(), "End and succes of the operation", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(), "Failure of the operation", Toast.LENGTH_LONG).show();
        }
    }

}
