package com.example.rodri.letsworkout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rodri on 8/31/2016.
 */
public class NewRoutineActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner spinnerDays;
    private EditText etMuscleGroup;
    private Spinner spinnerExercises;
    private EditText etSets;
    private EditText etReps;
    private Button btAdd;
    private ListView listOfExercises;
    private Button btConfirm;

    private List<String> days = new ArrayList<>();
    private List<String> muscleGroup = new ArrayList<>();
    private List<String> exercises = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapterDays;
    //private ArrayAdapter<String> spinnerAdapterMuscleGroup;
    private ArrayAdapter<String> spinnerAdapterExercises;

    private int selectedDay = -1;
    private int selectedMuscleGroup = -1;

    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_routine);

        initializeViews();
        dataSource = new MyDataSource(getApplicationContext());

        days = Arrays.asList(getResources().getStringArray(R.array.days));
        spinnerAdapterDays = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        spinnerDays.setAdapter(spinnerAdapterDays);

        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        muscleGroup = Arrays.asList(getResources().getStringArray(R.array.muscle_groups));

        etMuscleGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "It worked!", Toast.LENGTH_SHORT).show();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarNewRoutine);
        spinnerDays = (Spinner) findViewById(R.id.activityNewRoutine_spinnerDays);
        etMuscleGroup = (EditText) findViewById(R.id.activityNewRoutine_etMuscleGroup);
        spinnerExercises = (Spinner) findViewById(R.id.activityNewRoutine_spinnerExercises);
        etSets = (EditText) findViewById(R.id.activityNewRoutine_etSets);
        etReps = (EditText) findViewById(R.id.activityNewRoutine_etReps);
        btAdd = (Button) findViewById(R.id.activityNewRoutine_btAdd);
        listOfExercises = (ListView) findViewById(R.id.activityNewRoutine_listOfExercises);
        btConfirm = (Button) findViewById(R.id.activityNewRoutine_btConfirm);
    }
}
