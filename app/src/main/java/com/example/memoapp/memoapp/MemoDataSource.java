package com.example.memoapp.memoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

            initialValues.put("memoNotes", c.getMemoNotes());
            initialValues.put("memoDate", c.getDateCreated());
            initialValues.put("priority", c.getPriority());

            didSucceed = database.insert("memo", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public int getLastMemoID() {
        int lastID = -1;
        try {
            String query = "Select MAX(memoID) from memo";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastID = cursor.getInt(0);
            System.out.print(lastID);
            cursor.close();
        }
        catch (Exception e) {
            lastID = -1;
            System.out.print("Exception " + e);
        }
        return lastID;
    }
    public boolean updateMemo(Memo m) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) m.getMemoID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("memoID", m.getMemoID());
            updateValues.put("memoNotes", m.getMemoNotes());
            updateValues.put("memoDate", m.getDateCreated());
            updateValues.put("priority", m.getPriority());
            didSucceed = database.update("memo", updateValues, "memoID=" + rowId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }


    public boolean deleteMemo(int memoID) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("memo", "memoID=" + memoID, null) > 0;
        } catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }


    public ArrayList<Memo> getMemosByPriority(String sortField, String sortOrder) {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            //String query = "SELECT  * FROM memo WHERE priority = " + "'" + sortField + "'"  ;
            String query = "SELECT * FROM memo" +
                    " ORDER BY CASE priority " +
                    "WHEN " + "'" + sortField + "'" + " THEN 0  ELSE 1  END " +
                    ", priority " + sortOrder ;
            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();                                          //1
                newMemo.setMemoID(cursor.getInt(0));
                newMemo.setMemoNotes(cursor.getString(1));


                Long today = new Date().getTime();//2
                newMemo.setDateCreated(cursor.getLong(2));
                newMemo.setPriority(cursor.getString(3));

                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            memos = new ArrayList<Memo>();
        }
        return memos;
    }

    public ArrayList<Memo> getMemosByDate(String sortField, String sortOrder) {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "SELECT  * FROM memo ORDER BY " + sortField + " " + sortOrder ;

            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();                                          //1
                newMemo.setMemoID(cursor.getInt(0));
                newMemo.setMemoNotes(cursor.getString(1));


                Long today = new Date().getTime();//2
                newMemo.setDateCreated(cursor.getLong(2));
                newMemo.setPriority(cursor.getString(3));

                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            memos = new ArrayList<Memo>();
        }
        return memos;
    }

    public Memo getSpecificMemo (int memoID) {
        Memo memo = new Memo();
        String query = "SELECT  * FROM memo WHERE memoid =" + memoID;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            memo.setMemoID(cursor.getInt(0));
            memo.setMemoNotes(cursor.getString(1));

            memo.setDateCreated(cursor.getLong(2));
            memo.setPriority(cursor.getString(3));

            cursor.close();
        }
        return memo;
    }

}
