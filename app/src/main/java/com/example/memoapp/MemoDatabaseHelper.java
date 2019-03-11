package com.example.memoapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseErrorHandler;
import android.util.Log;
import android.content.Context;
import android.content.ContentValues;



public class MemoDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "MemoDatabaseHelper";
    private static final String TABLE_NAME = "memo_table";
    private static final int DATABASE_VERSION = 1;
    private static final String COL1 = "importanceID";
    private static final String COL2 = "memoText";


    public MemoDatabaseHelper(Context context){
        super(context, TABLE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
