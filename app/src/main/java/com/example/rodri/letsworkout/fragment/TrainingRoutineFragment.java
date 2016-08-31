package com.example.rodri.letsworkout.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.activity.NewRoutineActivity;

/**
 * Created by rodri on 8/30/2016.
 */
public class TrainingRoutineFragment extends Fragment {

    Button btNewRoutine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_training_routine, container, false);

        btNewRoutine = (Button) v.findViewById(R.id.fragmentTrainingRoutine_btNewRoutine);

        btNewRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NewRoutineActivity.class);
                startActivity(i);
            }
        });

        return v;
    }
}
