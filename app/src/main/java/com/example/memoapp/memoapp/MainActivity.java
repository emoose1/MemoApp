package com.example.memoapp.memoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Memo currentMemo = new Memo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListButton();
        initAddMemoButton();
        initSettingsButton();
        initTextChangedEvents();
        initSaveButton();
        initPrioritySpinner();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int selectedMemoId = getIntent().getExtras().getInt("memoid");
            initMemo(selectedMemoId);
        }
        else {
            currentMemo = new Memo();
//            currentMemo.setDateCreated(new Date().getTime()) ;
//            Toast.makeText(this, currentMemo.getDateCreated().toString(), Toast.LENGTH_LONG).show();

        }

    }



    private void initMemo(int id) {

        MemoDataSource ds = new MemoDataSource(MainActivity.this);
        try {
            ds.open();
            currentMemo= ds.getSpecificMemo(id);
            EditText editMemo = (EditText) findViewById(R.id.editText2);
            editMemo.setText(ds.getSpecificMemo(id).getMemoNotes());
            ds.close();
        }
        catch (Exception e) {
            Toast.makeText(this, "Load Contact Failed", Toast.LENGTH_LONG).show();
        }


        Spinner editPriority = (Spinner) findViewById(R.id.spinner1);






        if (currentMemo.getPriority().equals("High") ){
            editPriority.setSelection(0);
        }else if (currentMemo.getPriority().equals("Medium")){
            editPriority.setSelection(1);
        }else{
            editPriority.setSelection(2);
        }


    }

    private void initPrioritySpinner(){
        Spinner editPriority = (Spinner) findViewById(R.id.spinner1);
        editPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    currentMemo.setPriority(item.toString());
                }
                currentMemo.setPriority(item.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                currentMemo.setPriority("Low");
            }
        });
    }




    private void initListButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, MemoListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }


    private void initSaveButton() {
        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //hideKeyboard();
                boolean wasSuccessful = false;
                MemoDataSource ds = new MemoDataSource(MainActivity.this);
                try {
                    ds.open();

                    if (currentMemo.getMemoID() == -1) {
                        wasSuccessful = ds.insertMemo(currentMemo);

                        Toast.makeText(MainActivity.this, Boolean.toString(wasSuccessful),
                                Toast.LENGTH_SHORT).show();


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

//                if (wasSuccessful) {
//                    ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
//                    editToggle.toggle();
//                    setForEditing(false);
//                }
            }
        });
    }


    private void initAddMemoButton() {
        ImageButton ibMap = (ImageButton) findViewById(R.id.imageButtonMap);
        ibMap.setEnabled(false);

    }

    private void initSettingsButton() {
        ImageButton ibSett = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibSett.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemoSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initTextChangedEvents(){
        final EditText etMemo = (EditText) findViewById(R.id.editText2);
        etMemo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               currentMemo.setMemoNotes(etMemo.getText().toString());

            }
        });
    }
}
