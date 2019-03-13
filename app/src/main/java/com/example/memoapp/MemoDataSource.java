package com.example.memoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class MemoDataSource {

    private SQLiteDatabase database;
    private MemoDatabaseHelper dbHelper;

    public MemoDataSource(Context context) {
        dbHelper = new MemoDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertMemo(Memo c) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("memoID", c.getMemoID());
            initialValues.put("memoNotes", c.getMemoNotes());
            initialValues.put("memoDate", String.valueOf(c.getDateCreated().getTimeInMillis()));
            initialValues.put("priorityID", c.getPriorityID());



            didSucceed = database.insert("memo", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateMemo(Memo m) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) m.getMemoID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("memoID", m.getMemoID());
            updateValues.put("memoNotes", m.getMemoNotes());
            updateValues.put("memoDate", String.valueOf(m.getDateCreated().getTimeInMillis()));
            updateValues.put("priorityID", m.getPriorityID());
            didSucceed = database.update("memo", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public int getLastMemoID() {
        int lastID = -1;
        try {
            String query = "Select MAX(_id) from memo";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastID = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastID = -1;
        }
        return lastID;
    }

    public ArrayList<String> getMemoNotes() {
        ArrayList<String> memoNotes = new ArrayList
                <String>();
        try {
            String query = "Select memoNotes from memo";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                memoNotes.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            memoNotes = new ArrayList<String>();
        }
        return memoNotes;
    }


    public ArrayList<Memo> getMemos(String sortField, String sortOrder) {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "SELECT  * FROM memo ORDER BY " + sortField + " " + sortOrder;

            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();                                          //1
                newMemo.setMemoID(cursor.getInt(0));
                newMemo.setMemoNotes(cursor.getString(1));
                Calendar calendar = Calendar.getInstance();                         //2
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(2)));
                newMemo.setDateCreated(calendar);
                newMemo.setPriorityID(cursor.getInt(3));

                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            memos = new ArrayList<Memo>();
        }
        return memos;
    }


    public boolean deleteMemo(int memoID) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("memo", "_id=" + memoID, null) > 0;
        } catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }

    public Memo getSpecificMemo (int memoID) {
        Memo memo = new Memo();
        String query = "SELECT  * FROM memo WHERE _id =" + memoID;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            memo.setMemoID(cursor.getInt(0));
            memo.setMemoNotes(cursor.getString(1));
            Calendar calendar = Calendar.getInstance();                         //2
            calendar.setTimeInMillis(Long.valueOf(cursor.getString(2)));
            memo.setDateCreated(calendar);
            memo.setPriorityID(cursor.getInt(3));

            cursor.close();
        }
        return memo;
    }

}