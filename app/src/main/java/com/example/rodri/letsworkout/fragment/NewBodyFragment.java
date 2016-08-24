package com.example.rodri.letsworkout.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;

/**
 * Created by rodri on 8/23/2016.
 */
public class NewBodyFragment extends Fragment {

    EditText etWeight;
    EditText etHeight;
    EditText etRightUpperArm;
    EditText etLeftUpperArm;
    EditText etRightForearm;
    EditText etLeftForearm;
    EditText etRighThigh;
    EditText etLeftThigh;
    EditText etRightCalf;
    EditText etLeftCalf;
    EditText etWaist;
    EditText etShoulder;
    EditText etChest;
    Button btConfirm;

    MyDataSource dataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_body, container, false);

        initializeViews(v);

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "HEY!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    public void initializeViews(View v) {
        etWeight = (EditText) v.findViewById(R.id.etWeight);
        etHeight = (EditText) v.findViewById(R.id.etHeight);
        etRightUpperArm = (EditText) v.findViewById(R.id.etRightUpperArm);
        etLeftUpperArm = (EditText) v.findViewById(R.id.etLeftUpperArm);
        etRightForearm = (EditText) v.findViewById(R.id.etRightForearm);
        etLeftForearm = (EditText) v.findViewById(R.id.etLeftForearm);
        etRighThigh = (EditText) v.findViewById(R.id.etRightThigh);
        etLeftThigh = (EditText) v.findViewById(R.id.etLeftThigh);
        etRightCalf = (EditText) v.findViewById(R.id.etRightCalf);
        etLeftCalf = (EditText) v.findViewById(R.id.etLeftCalf);
        etWaist = (EditText) v.findViewById(R.id.etWaist);
        etShoulder = (EditText) v.findViewById(R.id.etShoulder);
        etChest = (EditText) v.findViewById(R.id.etChest);
        btConfirm = (Button) v.findViewById(R.id.fragmentNewBody_btConfirm);
    }
}
