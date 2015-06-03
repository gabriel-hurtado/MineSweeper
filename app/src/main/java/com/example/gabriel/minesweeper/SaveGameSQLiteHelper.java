package com.example.gabriel.minesweeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaveGameSQLiteHelper extends SQLiteOpenHelper {
    //cd C:\
    //cd C:\Users\maxime\AppData\Local\Android\sdk\platform-tools
    //adb devices
    //adb -s emulator-5554 shell
    //sqlite3 /data/data/com.example.gabriel.minesweeper/databases/DBSaveGame
    //select * from SaveGame;
    //delete from SaveGame;

    String sqlCreate = "CREATE TABLE SaveGame " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " user TEXT, " +
            " date TEXT, " +
            " succes TEXT, " +
            " discoveringBox TEXT, " +
            " percentage TEXT, " +
            " numberMines TEXT, " +
            " time TEXT, " +
            " message TEXT )";

    public SaveGameSQLiteHelper(Context context, String name,
                                SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int lastVersion,
                          int newVersion) {

        //Se elimina la versi�n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS SaveGame");

        //Se crea la nueva versi�n de la tabla
        db.execSQL(sqlCreate);
    }

}

