package com.example.rodri.letsworkout.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.BodyMeasure;
import com.example.rodri.letsworkout.util.Util;

/**
 * Created by rodri on 8/28/2016.
 */
public class RegisterNewBodyMeasureActivity extends AppCompatActivity {

    private Toolbar toolbar;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_body_measure);

        initializeViews();
        dataSource = new MyDataSource(getApplicationContext());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BodyMeasure bm = new BodyMeasure();

                if (!isEditTextEmpty(etRightUpperArm)) {
                    double rightUpperArm = Double.parseDouble(etRightUpperArm.getText().toString());
                    bm.setRightUpperArm(rightUpperArm);
                }
                if (!isEditTextEmpty(etLeftUpperArm)) {
                    double leftUpperArm = Double.parseDouble(etLeftUpperArm.getText().toString());
                    bm.setLeftUpperArm(leftUpperArm);
                }
                if (!isEditTextEmpty(etRightForearm)) {
                    double rightForearm = Double.parseDouble(etRightForearm.getText().toString());
                    bm.setRightForearm(rightForearm);
                }
                if (!isEditTextEmpty(etLeftForearm)) {
                    double leftForearm = Double.parseDouble(etLeftForearm.getText().toString());
                    bm.setLeftForearm(leftForearm);
                }
                if (!isEditTextEmpty(etRightThigh)) {
                    double rightThigh = Double.parseDouble(etRightThigh.getText().toString());
                    bm.setRightThigh(rightThigh);
                }
                if (!isEditTextEmpty(etLeftThigh)) {
                    double leftThigh = Double.parseDouble(etLeftThigh.getText().toString());
                    bm.setLeftThigh(leftThigh);
                }
                if (!isEditTextEmpty(etRightCalf)) {
                    double rightCalf = Double.parseDouble(etRightCalf.getText().toString());
                    bm.setRightCalf(rightCalf);
                }
                if (!isEditTextEmpty(etLeftCalf)) {
                    double leftCalf = Double.parseDouble(etLeftCalf.getText().toString());
                    bm.setLeftCalf(leftCalf);
                }
                if (!isEditTextEmpty(etWaist)) {
                    double waist = Double.parseDouble(etWaist.getText().toString());
                    bm.setWaist(waist);
                }
                if (!isEditTextEmpty(etShoulder)) {
                    double shoulder = Double.parseDouble(etShoulder.getText().toString());
                    bm.setShoulder(shoulder);
                }
                if (!isEditTextEmpty(etChest)) {
                    double chest = Double.parseDouble(etChest.getText().toString());
                    bm.setChest(chest);
                }
                if (!isEditTextEmpty(etWeight)) {
                    double weight = Double.parseDouble(etWeight.getText().toString());
                    bm.setWeight(weight);
                }
                if (!isEditTextEmpty(etHeight)) {
                    double height = Double.parseDouble(etHeight.getText().toString());
                    bm.setHeight(height);
                }

                dataSource.open();
                BodyMeasure newBM = dataSource.createBodyMeasure(bm.getRightUpperArm(), bm.getLeftUpperArm(),
                        bm.getRightForearm(), bm.getLeftForearm(), bm.getChest(), bm.getRightThigh(), bm.getLeftThigh(),
                        bm.getRightCalf(), bm.getLeftCalf(), bm.getWaist(), bm.getShoulder(), bm.getWeight(), bm.getHeight(),
                        System.currentTimeMillis());
                dataSource.createUserBody(Authentication.getInstance().getUserId(), newBM.getId());
                dataSource.close();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("bodyMeasureId", newBM.getId());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    public void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarRegisterNewBodyMeasure);
        etWeight = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etWeight);
        etHeight = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etHeight);
        etRightUpperArm = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etRightUpperArm);
        etLeftUpperArm = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etLeftUpperArm);
        etRightForearm = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etRightForearm);
        etLeftForearm = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etLeftForearm);
        etRightThigh = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etRightThigh);
        etLeftThigh = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etLeftThigh);
        etRightCalf = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etRightCalf);
        etLeftCalf = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etLeftCalf);
        etChest = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etChest);
        etWaist = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etWaist);
        etShoulder = (EditText) findViewById(R.id.activityRegisterNewBodyMeasure_etShoulder);
        btConfirm = (Button) findViewById(R.id.activityRegisterNewBodyMeasure__btConfirm);
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
