package com.example.memoapp;

import java.util.Calendar;

public class Memo {
    private int memoID;
    private String memoNotes;
    private Calendar dateCreated;
    private int priorityID;



    public Memo() {
        memoID = getMemoID();
        dateCreated = Calendar.getInstance();

    }

    public int getMemoID() {
        return memoID;
    }
    public void setMemoID(int i) {
        memoID = i;
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
    public int getPriorityID(){return priorityID;}
    public void setPriorityID(int i){priorityID = i;}
}
