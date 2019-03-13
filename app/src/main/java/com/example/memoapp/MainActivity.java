package com.example.memoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private Memo currentMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo1);

        initListButton();
        initSaveButton();
        initTextChangedEvents();
        initPriority();

        currentMemo = new Memo();
    }

    private void initListButton() {

        Button listButton = (Button) findViewById(R.id.button3);
        listButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemoListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initTextChangedEvents() {
        final EditText newMemo = (EditText) findViewById(R.id.editText);
        newMemo.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                currentMemo.setMemoNotes(newMemo.getText().toString());
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Auto-generated method stub
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Auto-generated method stub
            }
        });

    }

    private void initPriority() {
           RadioGroup rgPriority = (RadioGroup) findViewById(R.id.radioGroupPriority);
           rgPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

               @Override
               public void onCheckedChanged(RadioGroup arg0, int arg1) {
                   RadioButton rbLow = (RadioButton) findViewById(R.id.low);
                   RadioButton rbMedium = (RadioButton) findViewById(R.id.medium);
                   RadioButton rbHigh = (RadioButton) findViewById(R.id.high);
                   if (rbLow.isChecked()) {
                       getSharedPreferences("MemoPriority", Context.MODE_PRIVATE).edit() .putString("sortfield", "low").commit();
                       rbLow.setChecked(true);
                   }
                   else if (rbMedium.isChecked()) {
                       getSharedPreferences("MemoPriority", Context.MODE_PRIVATE).edit().putString("sortfield", "medium").commit();
                       rbMedium.setChecked(true);
                   }
                   else {
                       getSharedPreferences("MemoPriority", Context.MODE_PRIVATE).edit().putString("sortfield", "high").commit();
                       rbHigh.setChecked(true);
                   }
               }
           });
       }

    private void initSaveButton() {
        Button saveButton = (Button) findViewById(R.id.button2);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // hideKeyboard();
                boolean wasSuccessful = false;
                MemoDataSource ds = new MemoDataSource(MainActivity.this);
                try {
                    ds.open();

                    if (currentMemo.getMemoID() == -1) {
                        wasSuccessful = ds.insertMemo(currentMemo);
                        int newId = ds.getLastMemoID();
                        currentMemo.setMemoID(newId);
                    } else {
                        wasSuccessful = ds.updateMemo(currentMemo);
                    }
                    ds.close();
                }
                catch (Exception e) {
                    wasSuccessful = false;
                }

              /*  if (wasSuccessful) {
                    ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
                    editToggle.toggle();
                    setForEditing(false);
                }  */
            }


        });
    }
}

