package com.example.rodri.letsworkout.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

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
                startActivity(i);
            }
        });

        dataSource = new MyDataSource(getActivity());
        dataSource.open();

        routines = dataSource.getRoutines(Authentication.getInstance().getUserId());
        adapter = new TrainingRoutineAdapter(getActivity(), 0, routines);

        listOfRoutines.setAdapter(adapter);
        return v;
    }
}
