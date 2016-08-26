package com.example.rodri.letsworkout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.util.Util;

/**
 * Created by rodri on 8/26/2016.
 */
public class UpdateCurrentMeasuresActivity extends AppCompatActivity {

    private Toolbar toolbar;
    EditText etWeight;
    EditText etHeight;
    EditText etRightUpperArm;
    EditText etLeftUpperArm;
    EditText etRightForearm;
    EditText etLeftForearm;
    EditText etRightThigh;
    EditText etLeftThigh;
    EditText etRightCalf;
    EditText etLeftCalf;
    EditText etWaist;
    EditText etShoulder;
    EditText etChest;
    Button btConfirm;

    MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_current_measures);

        initializeViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dataSource = new MyDataSource(getApplicationContext());

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "YEAH!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarUpdateCurrentMeasures);
        etWeight = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etWeight);
        etHeight = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etHeight);
        etRightUpperArm = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etRightUpperArm);
        etLeftUpperArm = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etLeftUpperArm);
        etRightForearm = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etRightForearm);
        etLeftForearm = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etLeftForearm);
        etRightThigh = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etRightThigh);
        etLeftThigh = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etLeftThigh);
        etRightCalf = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etRightCalf);
        etLeftCalf = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etLeftCalf);
        etWaist = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etWaist);
        etShoulder = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etShoulder);
        etChest = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etChest);
        btConfirm = (Button) findViewById(R.id.activityUpdateCurrentMeasures__btConfirm);
    }
}

