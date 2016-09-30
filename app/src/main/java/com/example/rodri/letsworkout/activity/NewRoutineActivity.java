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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.adapter.ExerciseRepetitionAdapter;
import com.example.rodri.letsworkout.adapter.MuscleGroupAdapter;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.Day;
import com.example.rodri.letsworkout.model.Exercise;
import com.example.rodri.letsworkout.model.ExerciseRepetition;
import com.example.rodri.letsworkout.model.MuscleGroup;
import com.example.rodri.letsworkout.model.Routine;
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
    //private RecyclerView listOfExercises;
    private ListView listOfExercises;
    private Button btConfirm;
    private EditText etRoutineName;

    private List<Day> days = new ArrayList<>();
    private List<MuscleGroup> muscleGroup = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapterDays;
    private ArrayAdapter<String> spinnerAdapterExercises;
    private MuscleGroupAdapter adapterMuscleGroup;

    private int selectedDay = -1;
    private int selectedExercise = -1;
    private List<MuscleGroup> selectedMuscleGroups = new ArrayList<>();
    private List<Exercise> allExercises = new ArrayList<>(); // contains all exercises in the exercises' spinner

    List<ExerciseRepetition> exercises = new ArrayList<>();
    ExerciseRepetitionAdapter adapterExercises;

    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_routine);

        initializeViews();
        dataSource = new MyDataSource(getApplicationContext());
        dataSource.open();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        adapterExercises = new ExerciseRepetitionAdapter(NewRoutineActivity.this, 0, exercises);
        listOfExercises.setAdapter(adapterExercises);

        days = dataSource.getDays();
        String[] daysArray = new String[days.size()];
        for (int i = 0; i < days.size(); i++) {
            daysArray[i] = days.get(i).getName();
        }
        spinnerAdapterDays = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, daysArray);
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

        muscleGroup = dataSource.getMuscleGroups();

        etMuscleGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(NewRoutineActivity.this);
                dialog.setContentView(R.layout.dialog_muscle_group);
                dialog.setTitle("Muscle Group");

                ListView listOfMuscleGroup = (ListView) dialog.findViewById(R.id.dialogMuscleGroup_listOfMuscleGroups);
                Button btConfirm = (Button) dialog.findViewById(R.id.dialogMuscleGroup_btConfirm);

                adapterMuscleGroup = new MuscleGroupAdapter(NewRoutineActivity.this, 0, muscleGroup);
                listOfMuscleGroup.setAdapter(adapterMuscleGroup);

                btConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedMuscleGroups.clear();
                        selectedMuscleGroups = adapterMuscleGroup.getSelectedItems();
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < selectedMuscleGroups.size(); i++) {
                            sb.append(selectedMuscleGroups.get(i).getName());
                            if (i < selectedMuscleGroups.size() - 1) {
                                sb.append(" and ");
                            }

                        }
                        etMuscleGroup.setHint(sb);
                        //Toast.makeText(NewRoutineActivity.this, "res: " + sb, Toast.LENGTH_SHORT).show();
                        setExercises();
                        dialog.cancel();
                    }
                });

                dialog.show();

            }
        });

        spinnerExercises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedExercise = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sets = etSets.getText().toString();
                String reps = etReps.getText().toString();

                if (sets.equals("")) {
                    Toast.makeText(NewRoutineActivity.this, R.string.toast_sets_field_empty, Toast.LENGTH_SHORT).show();
                    return;
                } else if (reps.equals("")) {
                    Toast.makeText(NewRoutineActivity.this, R.string.toast_reps_field_empty, Toast.LENGTH_SHORT).show();
                    return;
                } else if (selectedExercise == -1){
                    Toast.makeText(NewRoutineActivity.this, R.string.toast_any_exercise_selected, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    /**ExerciseRepetition exerciseRepetition =
                            dataSource.createExerciseRepetition(allExercises.get(selectedExercise).getId(), Integer.parseInt(sets),
                                    Integer.parseInt(reps));
                    */
                    ExerciseRepetition exerciseRepetition = new ExerciseRepetition();
                    exerciseRepetition.setExerciseId(allExercises.get(selectedExercise).getId());
                    exerciseRepetition.setSets(Integer.parseInt(sets));
                    exerciseRepetition.setReps(Integer.parseInt(reps));
                    exercises.add(exerciseRepetition);
                    adapterExercises.notifyDataSetChanged();

                }
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMuscleGroups.size() < 1) {
                    Toast.makeText(NewRoutineActivity.this, R.string.toast_muscle_group_empty, Toast.LENGTH_SHORT).show();
                } else if (exercises.size() < 1){
                    Toast.makeText(NewRoutineActivity.this, R.string.toast_any_exercise_selected, Toast.LENGTH_SHORT).show();
                } else {
                    //persist data
                    // 1 - ExerciseRepetition
                    // 2 - Routine (check if the user passed a value to routine name (EditText))
                    // 3 - RoutineExercises
                    // 4 - RoutineMuscleGroup

                    int temp = 0;
                    try {
                        // 1
                        List<ExerciseRepetition> persistedExercises = new ArrayList<>();
                        for (ExerciseRepetition e : exercises) {
                            ExerciseRepetition er = dataSource.createExerciseRepetition(e.getExerciseId(), e.getSets(), e.getReps());
                            persistedExercises.add(er);
                        }

                        // 2
                        String routineName;
                        if (etRoutineName.getText().toString().equals("")) {
                            routineName = days.get(selectedDay).getName();
                        } else {
                            routineName = etRoutineName.getText().toString();
                        }
                        Routine routine = dataSource.createRoutine(selectedDay + 1, Authentication.getInstance().getUserId(),
                                true, routineName);

                        // 3 this can be placed with step 1 (I guess)
                        for (ExerciseRepetition e: persistedExercises) {
                            dataSource.createRoutineExercises(routine.getId(), e.getId());
                        }

                        // 4
                        for (MuscleGroup mg: selectedMuscleGroups) {
                            dataSource.createRoutineMuscleGroup(routine.getId(), mg.getId());
                        }

                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                for (MuscleGroup mg: selectedMuscleGroups) {
                    System.out.println("Muscle Group: " + mg.getName());
                }
                for (ExerciseRepetition e: exercises) {
                    System.out.println("Exercise ID: " + e.getExerciseId() + " Sets: " + e.getSets() + " Reps: " + e.getReps());
                }
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
        //listOfExercises = (RecyclerView) findViewById(R.id.activityNewRoutine_listOfExercises);
        listOfExercises = (ListView) findViewById(R.id.activityNewRoutine_listOfExercises);
        btConfirm = (Button) findViewById(R.id.activityNewRoutine_btConfirm);
        etRoutineName = (EditText) findViewById(R.id.activityNewRoutine_etRoutineName);
    }

    public void setExercises() {
        List<Exercise> exercises;
        List<String> allExercisesByName = new ArrayList<>();
        for (int i = 0; i < selectedMuscleGroups.size(); i++) {
            exercises = dataSource.getExercises(selectedMuscleGroups.get(i).getId());
            allExercises.addAll(exercises);
            // *** GAMBIARRA ALERT !!!!! ***  This will change when I implement CustomSpinner =)
            for (Exercise e: exercises) {
                allExercisesByName.add(e.getName());
            }
        }
        spinnerAdapterExercises = new ArrayAdapter<>(NewRoutineActivity.this, android.R.layout.simple_spinner_item, allExercisesByName);
        spinnerExercises.setAdapter(spinnerAdapterExercises);
    }

    @Override
    protected void onDestroy() {
        dataSource.close();
        super.onDestroy();
    }
}
