package com.example.rodri.letsworkout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;

/**
 * Created by rodri on 10/4/2016.
 */
public class RoutineActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etRoutineName;
    private Spinner daysSpinner;
    private TextView txtMuscleGroups;
    private CheckBox checkMainRoutine;
    private ListView listOfExercises;
    private Button btNewExercise;
    private Button btConfirm;
    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        dataSource = new MyDataSource(this);
        iniViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Routine - routineName (Edit Text)
        // Routine - dayId  / Day - list of days (Spinner)
        // RoutineMuscleGroupSet - list of muscle groups (Text View)
        // Routine - chosen (CheckBox)
        // *** RoutineExercisesSet ??? - list of exercises (ListView)
        // *** Need to study about SQLite Transactions
    }

    public void iniViews() {
        toolbar = (Toolbar) findViewById(R.id.activityRoutine_toolbar);
        etRoutineName = (EditText) findViewById(R.id.activityRoutine_etRoutineName);
        daysSpinner = (Spinner) findViewById(R.id.activityRoutine_spinnerDays);
        txtMuscleGroups = (TextView) findViewById(R.id.activityRoutine_txtMuscleGroupNames);
        checkMainRoutine = (CheckBox) findViewById(R.id.activityRoutine_checkMainRoutine);
        listOfExercises = (ListView) findViewById(R.id.activityRoutine_listOfExercises);
        btNewExercise = (Button) findViewById(R.id.activityRoutine_btNewExercise);
        btConfirm = (Button) findViewById(R.id.activityRoutine_btConfirm);
    }
}
