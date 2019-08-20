package com.example.memoapp.memoapp;

import java.util.Calendar;
import java.util.Date;

public class Memo {
    private int memoID;
    private String memoNotes;
    private Long dateCreated;
    private String priority;



    public Memo() {
        memoID = -1;
   dateCreated = new Date().getTime();

    }

    /*public Memo(int memoID, String memoNotes, Calendar dateCreated, int priorityID){

        this.memoID = memoID;
        this.memoNotes = memoNotes;
        this.dateCreated = dateCreated;
        this.priorityID = priorityID;

    }
    */

    public int getMemoID() {
        return memoID;
    }
    public void setMemoID(int i) {
        this.memoID = i;
    }
    public String getMemoNotes() {
        return memoNotes;
    }
    public void setMemoNotes(String s) {
        this.memoNotes = s;
    }
    public Long getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Long c) {
        this.dateCreated = c;
    }
    public String getPriority(){
        return priority;
    }
    public void setPriority(String i){
        this.priority = i;
    }
}
