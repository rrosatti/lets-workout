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
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.activity.RegisterNewBodyMeasureActivity;
import com.example.rodri.letsworkout.activity.UpdateCurrentMeasuresActivity;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.BodyMeasure;
import com.example.rodri.letsworkout.util.Util;

/**
 * Created by rodri on 8/23/2016.
 */
public class BodyFragment extends Fragment {

    TextView txtWeight;
    TextView txtHeight;
    TextView txtRightUpperArm;
    TextView txtLeftUpperArm;
    TextView txtRightForearm;
    TextView txtLeftForearm;
    TextView txtRightThigh;
    TextView txtLeftThigh;
    TextView txtRightCalf;
    TextView txtLeftCalf;
    TextView txtWaist;
    TextView txtShoulder;
    TextView txtChest;
    Button btUpdateCurrentMeasures;
    Button btRegisterNewMeasures;

    MyDataSource dataSource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_body, container, false);

        initializeViews(v);
        dataSource = new MyDataSource(getContext());
        dataSource.open();

        long userId = Authentication.getInstance().getUserId();
        long bodyMeasureId = dataSource.getLatestBodyMeasureId(userId);

        if (bodyMeasureId != -1) {
            getDataFromDatabase(bodyMeasureId);
        }

        btUpdateCurrentMeasures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), UpdateCurrentMeasuresActivity.class);
                startActivityForResult(i, 1);
            }
        });

        btRegisterNewMeasures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RegisterNewBodyMeasureActivity.class);
                startActivityForResult(i, 2);
            }
        });


        dataSource.close();

        return v;
    }

    public void initializeViews(View v) {
        txtWeight = (TextView) v.findViewById(R.id.fragmentBody_txtWeightValue);
        txtHeight = (TextView) v.findViewById(R.id.fragmentBody_txtHeightValue);
        txtRightUpperArm = (TextView) v.findViewById(R.id.fragmentBody_txtRightUpperArmValue);
        txtLeftUpperArm = (TextView) v.findViewById(R.id.fragmentBody_txtLeftUpperArmValue);
        txtRightForearm = (TextView) v.findViewById(R.id.fragmentBody_txtRightForearmValue);
        txtLeftForearm = (TextView) v.findViewById(R.id.fragmentBody_txtLeftForearmValue);
        txtRightThigh = (TextView) v.findViewById(R.id.fragmentBody_txtRightThighValue);
        txtLeftThigh = (TextView) v.findViewById(R.id.fragmentBody_txtLeftThighValue);
        txtRightCalf = (TextView) v.findViewById(R.id.fragmentBody_txtRightCalfValue);
        txtLeftCalf = (TextView) v.findViewById(R.id.fragmentBody_txtLeftCalfValue);
        txtWaist = (TextView) v.findViewById(R.id.fragmentBody_txtWaistValue);
        txtShoulder = (TextView) v.findViewById(R.id.fragmentBody_txtShoulderValue);
        txtChest = (TextView) v.findViewById(R.id.fragmentBody_txtChestValue);
        btUpdateCurrentMeasures = (Button) v.findViewById(R.id.fragmentBody_btUpdateCurrentMeasures);
        btRegisterNewMeasures = (Button) v.findViewById(R.id.fragmentBody_btRegisterNewMeasures);
    }

    public void getDataFromDatabase(long bodyMeasureId) {
        dataSource.open();
        BodyMeasure bm = dataSource.getBodyMeasure(bodyMeasureId);
        txtWeight.setText(String.valueOf(bm.getWeight()));
        txtHeight.setText(String.valueOf(bm.getHeight()));
        txtRightUpperArm.setText(String.valueOf(bm.getRightUpperArm()));
        txtLeftUpperArm.setText(String.valueOf(bm.getLeftUpperArm()));
        txtRightForearm.setText(String.valueOf(bm.getRightForearm()));
        txtLeftForearm.setText(String.valueOf(bm.getLeftForearm()));
        txtRightThigh.setText(String.valueOf(bm.getRightThigh()));
        txtLeftThigh.setText(String.valueOf(bm.getLeftThigh()));
        txtRightCalf.setText(String.valueOf(bm.getRightCalf()));
        txtLeftCalf.setText(String.valueOf(bm.getLeftCalf()));
        txtWaist.setText(String.valueOf(bm.getWaist()));
        txtShoulder.setText(String.valueOf(bm.getShoulder()));
        txtChest.setText(String.valueOf(bm.getChest()));
        dataSource.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         *  The result come from:
         *  1 - UpdateCurrentMeasuresActivity
         *  2 - RegisterNewBodyMeasure
         */
        switch (requestCode) {
            case 1: {
                if (resultCode == Activity.RESULT_OK) {
                    long id = data.getExtras().getLong("bodyMeasureId", 0);
                    getDataFromDatabase(id);
                }
                break;
            }
            case 2: {
                if (resultCode == Activity.RESULT_OK) {
                    long id = data.getExtras().getLong("bodyMeasureId", 0);
                    getDataFromDatabase(id);
                }
                break;
            }
            default: {
                break;
            }
        }

        if (requestCode == 1) {

        }
    }
}
