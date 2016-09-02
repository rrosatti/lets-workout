package com.example.rodri.letsworkout.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.adapter.MuscleGroupAdapter;
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
    private ArrayAdapter<String> spinnerAdapterExercises;
    private MuscleGroupAdapter adapterMuscleGroup;

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
                        List<String> selectedItems = new ArrayList<>();
                        selectedItems = adapterMuscleGroup.getSelectedItems();
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < selectedItems.size(); i++) {
                            sb.append(selectedItems.get(i));
                            if (i < selectedItems.size() - 1) {
                                sb.append(" and ");
                            }

                        }
                        etMuscleGroup.setHint(sb);
                        Toast.makeText(NewRoutineActivity.this, "res: " + sb, Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                dialog.show();


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
