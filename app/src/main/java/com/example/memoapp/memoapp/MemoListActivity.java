package com.example.memoapp.memoapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MemoListActivity extends ListActivity {
    boolean isDeleting = false;
    MemoAdapter adapter;
    ArrayList<Memo> memos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        initAddMemoButton();
        initListButton();
        initSettingsButton();
        initDeleteButton();
        initItemClick();
    }
    @Override
    public void onResume() {
        super.onResume();
        String sortBy = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortfield","High");
        String sortOrder = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortorder","ASC");

        MemoDataSource ds = new MemoDataSource(MemoListActivity.this);
        try {

            if(sortBy.equalsIgnoreCase("memoDate")) {
                ds.open();
                memos = ds.getMemosByDate(sortBy, sortOrder);
                ds.close();
            }else{
                ds.open();
                memos = ds.getMemosByPriority(sortBy, sortOrder);
                ds.close();
            }

            if (memos.size() > 0) {
                ListView listView = getListView();
                adapter = new MemoAdapter(this, memos);
                listView.setAdapter(adapter);
            }
            else {
                Intent intent = new Intent(MemoListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving memos", Toast.LENGTH_LONG).show();
        }

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


    private void initSettingsButton() {
        ImageButton ibSett = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibSett.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemoListActivity.this, MemoSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initListButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setEnabled(false);
    }

    private void initAddMemoButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MemoListActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
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
                    Intent intent = new Intent(MemoListActivity.this, MainActivity.class);
                    Bundle mBundle = new Bundle();

                    mBundle.putInt("memoid", selectedMemo.getMemoID());
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }
        });
    }


}
