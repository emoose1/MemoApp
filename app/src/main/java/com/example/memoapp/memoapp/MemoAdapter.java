package com.example.memoapp.memoapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
                v = vi.inflate(R.layout.list_item, null);
            }

            TextView memoNotes = (TextView) v.findViewById(R.id.textMem);
            TextView memoDate = (TextView) v.findViewById(R.id.textMemoDate);
            TextView memoPriority = (TextView) v.findViewById(R.id.textPriority);
            Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);
            String memoNote =        memo.getMemoNotes();
            memoNotes.setText(memoNote);

            memoPriority.setText(memo.getPriority());
            memoNotes.setTextColor(ContextCompat.getColor(adapterContext,R.color.backgroundRed));



            Date date=new Date(memo.getDateCreated());
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd  'T'  HH:mm:ss.SSSZ");
            String dateText = df2.format(date);

            memoDate.setText(dateText);






            b.setVisibility(View.INVISIBLE);

        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

    public void showDelete(final int position, final View convertView,final Context context, final Memo memo) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);
        //2
        if (b.getVisibility()==View.INVISIBLE) {
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDelete(position, convertView, context);
                    items.remove(memo);
                    deleteOption(memo.getMemoID(), context);
                }
            });
        }
        else {
            hideDelete(position, convertView, context);
        }
    }

    private void deleteOption(int memoToDelete, Context context) {
        MemoDataSource db = new MemoDataSource(context);
        try {
            db.open();
            db.deleteMemo(memoToDelete);
            db.close();
        }
        catch (Exception e) {
            Toast.makeText(adapterContext, "Delete Contact Failed", Toast.LENGTH_LONG).show();
        }
        this.notifyDataSetChanged();
    }
    //4
    public void hideDelete(int position, View convertView, Context context) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);
    }

}
