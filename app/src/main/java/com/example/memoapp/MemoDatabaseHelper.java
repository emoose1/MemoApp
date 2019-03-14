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
    private static final String TABLE_Memo = "memo";
    private static final String COLUMN_MemoID = "memoID";
    private static final String COLUMN_MemoNotes = "memoNotes";
    private static final String COLUMN_MemoDate = "memoDate";
    private static final String COLUMN_PriorityID = "priorityID";


    private static final String CREATE_TABLE_MEMO =
            "create table TABLE_Memo(COLUMN_MemoID integer primary key AUTOINCREMENT,"
            + "COLUMN_MemoNotes text not null,"
            + "COLUMN_MemoDate Calendar,"
            + "COLUMN_PriorityID int)";

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
