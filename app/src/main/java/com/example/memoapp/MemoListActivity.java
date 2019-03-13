package com.example.memoapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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
}
