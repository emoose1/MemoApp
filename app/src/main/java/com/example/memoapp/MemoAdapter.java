package com.example.memoapp;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Calendar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MemoAdapter extends ArrayAdapter<Memo> {

    private ArrayList<Memo> items;
    private Context adapterContext;

    public MemoAdapter(Context context, ArrayList<Memo> items) {
        super(context, R.layout.list_item, items);
        adapterContext = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        try {
            Memo memo = items.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item_memo, null);
            }

            TextView memoNotes = (TextView) v.findViewById(R.id.textMemoTitle);
            TextView memoDate = (TextView) v.findViewById(R.id.textMemoDate);
            Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);
            memoNotes.setText(memo.getMemoNotes());
            memoDate.setText(memo.getDateCreated());
            b.setVisibility(View.INVISIBLE);
            
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

}
