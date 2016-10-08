package com.example.rodri.letsworkout.activity;

import android.app.Dialog;
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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.adapter.ExerciseRepetitionAdapter;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.interfaces.DataTransferInterface;
import com.example.rodri.letsworkout.model.Day;
import com.example.rodri.letsworkout.model.Exercise;
import com.example.rodri.letsworkout.model.ExerciseRepetition;
import com.example.rodri.letsworkout.model.MuscleGroup;
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

    // Exercises Dialog Objects
    private Spinner exercisesSpinner;
    private EditText etSets;
    private EditText etReps;
    private Button btAddNewExercise;

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
    private List<ExerciseRepetition> exerciseRepetitions = new ArrayList<>();
    private List<ExerciseRepetition> removedExercises = new ArrayList<>();
    private List<Exercise> allExercises = new ArrayList<>();
    private List<String> exercisesByName = new ArrayList<>();
    private List<MuscleGroup> muscleGroups = new ArrayList<>();
    private int selectedExercise = -1;

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

            exerciseRepetitions = exercisesSet.getExerciseRepetitions();
            exerciseRepetitionAdapter = new ExerciseRepetitionAdapter(RoutineActivity.this, 0,
                    exerciseRepetitions, this);
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

            btNewExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(RoutineActivity.this);
                    dialog.setContentView(R.layout.dialog_exercise_repetition);
                    dialog.setTitle("New Exercise");

                    exercisesSpinner = (Spinner) dialog.findViewById(R.id.dialogExerciseRepetition_spinnerExercises);
                    etSets = (EditText) dialog.findViewById(R.id.dialogExerciseRepetition_etSets);
                    etReps = (EditText) dialog.findViewById(R.id.dialogExerciseRepetition_etReps);
                    btAddNewExercise = (Button) dialog.findViewById(R.id.dialogExerciseRepetition_btConfirm);

                    dataSource.open();
                    muscleGroups = muscleGroupSet.getMuscleGroups();
                    List<Exercise> auxExercises = new ArrayList<>();
                    for (int i = 0; i < muscleGroups.size(); i++) {
                        auxExercises = dataSource.getExercises(muscleGroups.get(i).getId());
                        allExercises.addAll(auxExercises);

                        for (Exercise e: auxExercises) {
                            exercisesByName.add(e.getName());
                        }
                    }
                    dataSource.close();

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(RoutineActivity.this,
                            android.R.layout.simple_spinner_item, exercisesByName);
                    exercisesSpinner.setAdapter(adapter);

                    exercisesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedExercise = position;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    btAddNewExercise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String sets = etSets.getText().toString();
                            String reps = etReps.getText().toString();

                            if(sets.equals("")) {
                                Toast.makeText(RoutineActivity.this, R.string.toast_sets_field_empty, Toast.LENGTH_SHORT).show();
                            } else if (reps.equals("")) {
                                Toast.makeText(RoutineActivity.this, R.string.toast_reps_field_empty, Toast.LENGTH_SHORT).show();
                            } else {
                                ExerciseRepetition exerciseRepetition = new ExerciseRepetition();
                                exerciseRepetition.setExerciseId(allExercises.get(selectedExercise).getId());
                                exerciseRepetition.setSets(Integer.parseInt(sets));
                                exerciseRepetition.setReps(Integer.parseInt(reps));
                                exerciseRepetitions.add(exerciseRepetition);
                                exerciseRepetitionAdapter.notifyDataSetChanged();
                            }


                            dialog.cancel();
                        }
                    });

                    dialog.show();

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
        // *** RoutineExercisesSet ??? - list of allExercises (ListView)
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
