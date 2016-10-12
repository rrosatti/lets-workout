package com.example.rodri.letsworkout.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.activity.NewRoutineActivity;
import com.example.rodri.letsworkout.adapter.TrainingRoutineAdapter;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.Routine;

import java.util.List;

/**
 * Created by rodri on 8/30/2016.
 */
public class TrainingRoutineFragment extends Fragment {

    private Button btNewRoutine;
    private ListView listOfRoutines;
    private MyDataSource dataSource;
    private TrainingRoutineAdapter adapter;
    private List<Routine> routines;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_training_routine, container, false);

        btNewRoutine = (Button) v.findViewById(R.id.fragmentTrainingRoutine_btNewRoutine);
        listOfRoutines = (ListView) v.findViewById(R.id.fragmentTrainingRoutine_listOfRoutines);

        btNewRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NewRoutineActivity.class);
                startActivityForResult(i, 3);
            }
        });

        dataSource = new MyDataSource(getActivity());

        getRoutines();


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2: {
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        Toast.makeText(getActivity(), "Yeah, it worked!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                break;
            }
            case 3: {
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        getRoutines();
                        break;
                    }
                }

                break;
            }

        }
    }

    public void getRoutines() {
        dataSource.open();
        routines = dataSource.getRoutines(Authentication.getInstance().getUserId());
        if (routines != null) {
            adapter = new TrainingRoutineAdapter(getActivity(), 0, routines, this);
            listOfRoutines.setAdapter(adapter);
        }
        dataSource.close();
    }
}
