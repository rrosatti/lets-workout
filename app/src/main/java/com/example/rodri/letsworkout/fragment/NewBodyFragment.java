package com.example.rodri.letsworkout.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.BodyMeasure;

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
    EditText etRightThigh;
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
        dataSource = new MyDataSource(getContext());

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BodyMeasure bodyMeasure = new BodyMeasure();

                if (!isEditTextEmpty(etRightUpperArm)) {
                    double rightUpperArm = Double.parseDouble(etRightUpperArm.getText().toString());
                    bodyMeasure.setRightUpperArm(rightUpperArm);
                }
                if (!isEditTextEmpty(etLeftUpperArm)) {
                    double leftUpperArm = Double.parseDouble(etLeftUpperArm.getText().toString());
                    bodyMeasure.setLeftUpperArm(leftUpperArm);
                }
                if (!isEditTextEmpty(etRightForearm)) {
                    double rightForearm = Double.parseDouble(etRightForearm.getText().toString());
                    bodyMeasure.setRightForearm(rightForearm);
                }
                if (!isEditTextEmpty(etLeftForearm)) {
                    double leftForearm = Double.parseDouble(etLeftForearm.getText().toString());
                    bodyMeasure.setLeftForearm(leftForearm);
                }
                if (!isEditTextEmpty(etRightThigh)) {
                    double rightThigh = Double.parseDouble(etRightThigh.getText().toString());
                    bodyMeasure.setRightThigh(rightThigh);
                }
                if (!isEditTextEmpty(etLeftThigh)) {
                    double leftThigh = Double.parseDouble(etLeftThigh.getText().toString());
                    bodyMeasure.setLeftThigh(leftThigh);
                }
                if (!isEditTextEmpty(etRightCalf)) {
                    double rightCalf = Double.parseDouble(etRightCalf.getText().toString());
                    bodyMeasure.setRightCalf(rightCalf);
                }
                if (!isEditTextEmpty(etLeftCalf)) {
                    double leftCalf = Double.parseDouble(etLeftCalf.getText().toString());
                    bodyMeasure.setLeftCalf(leftCalf);
                }
                if (!isEditTextEmpty(etWaist)) {
                    double waist = Double.parseDouble(etWaist.getText().toString());
                    bodyMeasure.setWaist(waist);
                }
                if (!isEditTextEmpty(etShoulder)) {
                    double shoulder = Double.parseDouble(etShoulder.getText().toString());
                    bodyMeasure.setShoulder(shoulder);
                }
                if (!isEditTextEmpty(etChest)) {
                    double chest = Double.parseDouble(etChest.getText().toString());
                    bodyMeasure.setChest(chest);
                }
                if (!isEditTextEmpty(etWeight)) {
                    double weight = Double.parseDouble(etWeight.getText().toString());
                    bodyMeasure.setWeight(weight);
                }
                if (!isEditTextEmpty(etHeight)) {
                    double height = Double.parseDouble(etHeight.getText().toString());
                    bodyMeasure.setHeight(height);
                }

                dataSource.open();
                BodyMeasure newBodyMeasure = dataSource.createBodyMeasure(bodyMeasure.getRightUpperArm(), bodyMeasure.getLeftUpperArm(),
                        bodyMeasure.getRightForearm(), bodyMeasure.getLeftForearm(), bodyMeasure.getChest(), bodyMeasure.getRightThigh(),
                        bodyMeasure.getLeftThigh(), bodyMeasure.getRightCalf(), bodyMeasure.getLeftCalf(), bodyMeasure.getWaist(),
                        bodyMeasure.getShoulder(), bodyMeasure.getWeight(), bodyMeasure.getHeight(), System.currentTimeMillis());

                dataSource.createUserBody(Authentication.getInstance().getUserId(), newBodyMeasure.getId());
                dataSource.close();

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, new BodyFragment());
                ft.commit();

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
        etRightThigh = (EditText) v.findViewById(R.id.etRightThigh);
        etLeftThigh = (EditText) v.findViewById(R.id.etLeftThigh);
        etRightCalf = (EditText) v.findViewById(R.id.etRightCalf);
        etLeftCalf = (EditText) v.findViewById(R.id.etLeftCalf);
        etWaist = (EditText) v.findViewById(R.id.etWaist);
        etShoulder = (EditText) v.findViewById(R.id.etShoulder);
        etChest = (EditText) v.findViewById(R.id.etChest);
        btConfirm = (Button) v.findViewById(R.id.fragmentNewBody_btConfirm);
    }

    /**
     * check whether or not the given EditText is empty
     *
     * @param editText
     * @return
     */
    public boolean isEditTextEmpty(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
