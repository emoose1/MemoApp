package com.example.memoapp;

import java.util.Calendar;

public class Memo {
    private int memoID;
    private String memoNotes;
    private Calendar dateCreated;


    public Memo() {
        memoID = -1;
        dateCreated = Calendar.getInstance();


    }

    public String getMemoNotes() {
        return memoNotes;
    }
    public void setMemoNotes(String s) {
        memoNotes = s;
    }
    public Calendar getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Calendar c) {
        dateCreated = c;
    }
}
