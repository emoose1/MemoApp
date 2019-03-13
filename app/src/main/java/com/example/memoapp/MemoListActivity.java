package com.example.memoapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MemoListActivity  extends ListActivity {
    boolean isDeleting = false;
    MemoAdapter adapter;
    ArrayList<Memo> memos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);


        initItemClick();
        initDeleteButton();
        initAddNoteButton();
    }

    private void initDeleteButton() {
        final Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isDeleting) {
                    deleteButton.setText("Delete");
                    isDeleting = false;
                    adapter.notifyDataSetChanged();
                }
                else {
                    deleteButton.setText("Done Deleting");
                    isDeleting = true;
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        String sortBy = getSharedPreferences("MemoListPreferences", Context.MODE_PRIVATE).getString("sortorder", "date");

        MemoDataSource ds = new MemoDataSource(this);
        try {
            ds.open();
            memos = ds.getMemos(sortBy);
            ds.close();
            if (memos.size() > 0) {
                ListView listView = getListView();
                adapter = new MemoAdapter(this, memos);
                listView.setAdapter(adapter);
            }
            else {
                Intent intent = new Intent(MemoListActivity.this, MemoActivity.class);
                startActivity(intent);
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }

    }


    private void initItemClick(){
        ListView listView = (ListView) getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memo selectedMemo = memos.get(position);
                if(isDeleting){
                    adapter.showDelete(position, view, MemoListActivity.this, selectedMemo);
                } else{
                    Intent intent = new Intent(MemoListActivity.this, MemoActivity.class);
                    intent.putExtra("contactid", selectedMemo.getMemoID());
                    startActivity(intent);
                }
            }
        });
    }

    private void initAddNoteButton() {
        Button newContact = (Button) findViewById(R.id.buttonAdd);
        newContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemoListActivity.this, MemoActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initSettings() {
        String sortBy = getSharedPreferences("MemoPriority", Context.MODE_PRIVATE).getString("sortfield","date");

        RadioButton rbSortDate = (RadioButton) findViewById(R.id.);
        RadioButton rbSortPriority = (RadioButton) findViewById(R.id.radioCity);

        if (sortBy.equalsIgnoreCase("date")) {
            rbSortDate.setChecked(true);
        }
        else if (sortBy.equalsIgnoreCase("priority")) {
            rbSortPriority.setChecked(true);
        }
      


    }
}
