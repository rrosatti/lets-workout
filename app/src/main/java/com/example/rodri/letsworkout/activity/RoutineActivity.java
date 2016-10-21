package com.example.rodri.letsworkout.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.example.rodri.letsworkout.model.Authentication;
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
    private Button btDeleteRoutine;

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
    private long routineId;
    private long newDayId = -1;
    private int chosen;
    private List<ExerciseRepetition> exerciseRepetitions = new ArrayList<>();
    private List<ExerciseRepetition> removedExercises = new ArrayList<>();
    private List<ExerciseRepetition> newExercises = new ArrayList<>();
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
            userId = extras.getLong("userId", -1);
            dayId = extras.getLong("dayId", -1);
            routineId = extras.getLong("routineId", -1);
            dataSource.open();
            if (routineId != -1) {
                routine = dataSource.getRoutine(routineId);
                exercisesSet = new RoutineExercisesSet(routineId, routine.getDayId(), RoutineActivity.this);
                dayId = routine.getDayId();
                userId = Authentication.getInstance().getUserId();
            } else {
                routine = dataSource.getRoutine(userId, dayId);
                exercisesSet = new RoutineExercisesSet(routine.getId(), dayId, RoutineActivity.this);
            }
            muscleGroupSet = new RoutineMuscleGroupSet(routine.getId(), RoutineActivity.this);
            days = dataSource.getDays();
            dataSource.close();
            chosen = routine.getChosen();

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
                    if(isChecked) {
                        chosen = 1;
                    } else {
                        chosen = 0;
                    }

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

                    btAddNewExercise.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_states_2));

                    dataSource.open();
                    muscleGroups = muscleGroupSet.getMuscleGroups();
                    List<Exercise> auxExercises;
                    for (int i = 0; i < muscleGroups.size(); i++) {
                        auxExercises = dataSource.getExercises(muscleGroups.get(i).getId());
                        System.out.println("Muscle group: " + muscleGroups.get(i).getName());
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
                            boolean result = addNewExercise();
                            if (result) {
                                dialog.cancel();
                            }
                        }
                    });

                    dialog.show();

                }
            });


            btConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RoutineActivity.this);

                    builder.setMessage(R.string.dialog_confirm_changes);
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveChanges();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            btDeleteRoutine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RoutineActivity.this);
                    builder.setMessage(R.string.dialog_delete_routine);
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteRoutine();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

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
        btDeleteRoutine = (Button) findViewById(R.id.activityRoutine_btDeleteRoutine);
    }

    @Override
    public void setValues(List<?> objectList) {
        removedExercises = (List<ExerciseRepetition>) objectList;
    }

    /**
     // 1 - check routine name
     // 2 - check day
     // 3 - check 'check box'
     // 4 - check if there is a new exercise or any of the previous ones was removed
     //
     // Possible changed tables:
     //
     // Routine (routine name, day id, chosen)
     // ExerciseRepetition (sets, reps, )
     // RoutineExercises (routineExerciseId, )

     */
    public void saveChanges() {

        dataSource.open();

        // 1 - Check all data related to the Routine table
        String routineName = etRoutineName.getText().toString();
        if (!routineName.equals(routine.getName())) {
            routine.setName(routineName);
        }
        if (routine.getDayId() != newDayId) {
            routine.setDayId(newDayId);
        }
        if (routine.getChosen() != chosen) {
            routine.setChosen(chosen);
            // check if there is already a routine set as 'main routine', given the same userId and dayId;
            if (chosen == 1) {
                Routine r = dataSource.getRoutine(userId, dayId);
                // if yes, then set the 'chosen' column as 0(it is not a main routine anymore)
                if (r != null) {
                    dataSource.updateRoutine(r.getId(), r.getDayId(), r.getUserId(), 0, r.getName());
                }
            }
        }

        // 2 - Check if there are new exercises
        // P.S. 1 - If the user add a new exercise and then later remove the same exercise, the newExercises
        //          list won't be notified. That's why we need to remove it manually.
        // P.S. 2 - I need the ID of the exercises in order to remove them. If happened the case above, then we
        //          don't have this ID. So, I also save the position of this exercise in the array (newExercisesRemoved)
        //          and also remove them manually.
        //
        // Possible solution for this problem: set a random value(-1) when user add a new exercise.
        boolean areThereNewExercises = false;
        if (newExercises.size() != 0) {
            int[] newExercisesRemoved = new int[newExercises.size()];
            int count = 0;

            for (int i = 0; i < removedExercises.size(); i++) {
                if (newExercises.contains(removedExercises.get(i))) {
                    newExercisesRemoved[count++] = i;
                    newExercises.remove(removedExercises.get(i));
                }
            }

            if (newExercises.size() != 0) areThereNewExercises = true;

            for (int i = 0; i < count; i++) {
                removedExercises.remove(newExercisesRemoved[i]);
            }
        }

        // 3 - Check if any of the exercises was removed
        boolean areThereRemovedExercises = false;
        if (removedExercises.size() != 0) {
            areThereRemovedExercises = true;
        }

        dataSource.beginTransaction();
        try {
            dataSource.updateRoutine(routine.getId(), routine.getDayId(), routine.getUserId(),
                    routine.getChosen(), routine.getName());

            if (areThereNewExercises) {
                for (ExerciseRepetition er: newExercises) {
                    ExerciseRepetition newExerciseRepetition =
                            dataSource.createExerciseRepetition(er.getExerciseId(), er.getSets(), er.getReps());
                    System.out.println("id: " + er.getExerciseId() + " sets: " + er.getSets()
                            + " reps: " + er.getReps());
                    dataSource.createRoutineExercises(routine.getId(), newExerciseRepetition.getId());
                }
            }

            if (areThereRemovedExercises) {
                for (ExerciseRepetition er: removedExercises) {
                    dataSource.deleteExerciseRepetition(er.getId());
                    dataSource.deleteRoutineExercise(routine.getId(), er.getId());
                }
            }

            dataSource.setTransactionSuccessful();
            setResult(Activity.RESULT_OK);
            finish();
        } catch (Exception e) {

        } finally {
            dataSource.endTransaction();
        }

        //Toast.makeText(RoutineActivity.this, "YEAH!!", Toast.LENGTH_LONG).show();
        dataSource.close();

    }

    public boolean addNewExercise() {

        String sets = etSets.getText().toString();
        String reps = etReps.getText().toString();

        if(sets.equals("")) {
            Toast.makeText(RoutineActivity.this, R.string.toast_sets_field_empty, Toast.LENGTH_SHORT).show();
        } else if (reps.equals("")) {
            Toast.makeText(RoutineActivity.this, R.string.toast_reps_field_empty, Toast.LENGTH_SHORT).show();
        } else {
            ExerciseRepetition exerciseRepetition = new ExerciseRepetition();
            exerciseRepetition.setExerciseId(allExercises.get(selectedExercise).getId());
            //System.out.println("Selected exercise: " + allExercises.get(selectedExercise).getName()
            //        + " id: " + allExercises.get(selectedExercise).getId());
            exerciseRepetition.setSets(Integer.parseInt(sets));
            exerciseRepetition.setReps(Integer.parseInt(reps));
            exerciseRepetitions.add(exerciseRepetition);
            newExercises.add(exerciseRepetition);
            exerciseRepetitionAdapter.notifyDataSetChanged();

            return true;
        }

        return false;

    }

    /**
     *
     *
     *
     */
    public void deleteRoutine() {
        dataSource.open();
        boolean result = dataSource.deleteRoutine(routine.getId());
        dataSource.close();

        if (result) {
            finish();
        } else {
            Toast.makeText(RoutineActivity.this, R.string.toast_delete_routine_failed, Toast.LENGTH_SHORT).show();
        }
    }

}
