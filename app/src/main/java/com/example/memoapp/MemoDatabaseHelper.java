package com.example.memoapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseErrorHandler;
import android.util.Log;
import android.content.Context;
import android.content.ContentValues;
import java.util.Calendar;



public class MemoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "memo.db";
    private static final int DATABASE_VERSION = 1;
    /*private static final String COL1 = "priorityID";
    private static final String COL2 = "memoText";
    private static final String COL3 = "memoDate";
    private int _id;
    */

    private static final String CREATE_TABLE_MEMO =
            "create table memo (_id integer primary key autoincrement, "
            + " memoText text not null, memoDate Calendar, "
            + "priorityID int);";

    public MemoDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(CREATE_TABLE_MEMO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MemoDatabaseHelper.class.getName(), "Upgrade database from verson "
            + oldVersion + " to " + newVersion + " which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS memo");
    onCreate(db);
    }
}
