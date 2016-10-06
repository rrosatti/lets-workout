package com.example.rodri.letsworkout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.adapter.ExerciseRepetitionAdapter;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.interfaces.DataTransferInterface;
import com.example.rodri.letsworkout.model.Day;
import com.example.rodri.letsworkout.model.ExerciseRepetition;
import com.example.rodri.letsworkout.model.Routine;
import com.example.rodri.letsworkout.model.RoutineExercisesSet;
import com.example.rodri.letsworkout.model.RoutineMuscleGroupSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 10/4/2016.
 */
public class RoutineActivity extends AppCompatActivity implements DataTransferInterface{

    // UI Objects
    private Toolbar toolbar;
    private EditText etRoutineName;
    private Spinner daysSpinner;
    private TextView txtMuscleGroups;
    private CheckBox checkMainRoutine;
    private ListView listOfExercises;
    private Button btNewExercise;
    private Button btConfirm;

    // Other variables
    private MyDataSource dataSource;
    private Routine routine;
    private RoutineExercisesSet exercisesSet;
    private RoutineMuscleGroupSet muscleGroupSet;
    private List<Day> days;
    private ExerciseRepetitionAdapter exerciseRepetitionAdapter;
    private long userId;
    private long dayId;
    private long newDayId = -1;
    private boolean checked = true;
    private List<ExerciseRepetition> removedExercises = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        dataSource = new MyDataSource(this);
        iniViews();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("userId");
            dayId = extras.getLong("dayId");
            dataSource.open();
            routine = dataSource.getRoutine(userId, dayId);
            exercisesSet = new RoutineExercisesSet(routine.getId(), dayId, RoutineActivity.this);
            muscleGroupSet = new RoutineMuscleGroupSet(routine.getId(), RoutineActivity.this);
            days = dataSource.getDays();
            dataSource.close();

            String[] daysArray = new String[days.size()];
            for (int i = 0; i < days.size(); i++) {
                daysArray[i] = days.get(i).getName();
            }

            etRoutineName.setText(routine.getName());

            ArrayAdapter daysAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, daysArray);
            daysSpinner.setAdapter(daysAdapter);
            daysSpinner.setSelection((int) dayId - 1);

            txtMuscleGroups.setText(muscleGroupSet.getMuscleGroupNames());

            if (routine.getChosen() == 1) {
                checkMainRoutine.setChecked(true);
            }

            exerciseRepetitionAdapter = new ExerciseRepetitionAdapter(RoutineActivity.this, 0,
                    exercisesSet.getExerciseRepetitions(), this);
            listOfExercises.setAdapter(exerciseRepetitionAdapter);

            btConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RoutineActivity.this, "YEAH!!", Toast.LENGTH_LONG).show();
                }
            });



            // --- GET CHANGED DATA

            daysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    newDayId = position + 1;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            checkMainRoutine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checked = isChecked;
                    Toast.makeText(RoutineActivity.this, "Check is " + checked, Toast.LENGTH_SHORT).show();
                }
            });

        }

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

    @Override
    public void setValues(List<?> objectList) {
        removedExercises = (List<ExerciseRepetition>) objectList;
    }
}
