package com.example.rodri.letsworkout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.BodyMeasure;
import com.example.rodri.letsworkout.util.Util;

/**
 * Created by rodri on 8/26/2016.
 */
public class UpdateCurrentMeasuresActivity extends AppCompatActivity {

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

    private long bodyMeasureId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_current_measures);

        initializeViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dataSource = new MyDataSource(getApplicationContext());
        dataSource.open();
        bodyMeasureId = dataSource.getLatestBodyMeasureId(Authentication.getInstance().getUserId());
        getDataFromDatabase();
        dataSource.close();

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
                dataSource.updateBodyMeasure(bodyMeasureId, bm.getRightUpperArm(), bm.getLeftUpperArm(), bm.getRightForearm(),
                        bm.getLeftForearm(), bm.getChest(), bm.getRightThigh(), bm.getLeftThigh(), bm.getRightCalf(),
                        bm.getLeftCalf(), bm.getWaist(), bm.getShoulder(), bm.getWeight(), bm.getHeight());
                dataSource.close();

                // Need to implement some things ir order to refresh the "MyBody" Fragment
                onBackPressed();
            }
        });
    }

    public void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarUpdateCurrentMeasures);
        etWeight = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etWeight);
        etHeight = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etHeight);
        etRightUpperArm = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etRightUpperArm);
        etLeftUpperArm = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etLeftUpperArm);
        etRightForearm = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etRightForearm);
        etLeftForearm = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etLeftForearm);
        etRightThigh = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etRightThigh);
        etLeftThigh = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etLeftThigh);
        etRightCalf = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etRightCalf);
        etLeftCalf = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etLeftCalf);
        etWaist = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etWaist);
        etShoulder = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etShoulder);
        etChest = (EditText) findViewById(R.id.activityUpdateCurrentMeasures_etChest);
        btConfirm = (Button) findViewById(R.id.activityUpdateCurrentMeasures__btConfirm);
    }

    public void getDataFromDatabase() {
        dataSource.open();
        BodyMeasure bm = dataSource.getBodyMeasure(bodyMeasureId);
        etWeight.setText(String.valueOf(bm.getWeight()));
        etHeight.setText(String.valueOf(bm.getHeight()));
        etRightUpperArm.setText(String.valueOf(bm.getRightUpperArm()));
        etLeftUpperArm.setText(String.valueOf(bm.getLeftUpperArm()));
        etRightForearm.setText(String.valueOf(bm.getRightForearm()));
        etLeftForearm.setText(String.valueOf(bm.getLeftForearm()));
        etRightThigh.setText(String.valueOf(bm.getRightThigh()));
        etLeftThigh.setText(String.valueOf(bm.getLeftThigh()));
        etRightCalf.setText(String.valueOf(bm.getRightCalf()));
        etLeftCalf.setText(String.valueOf(bm.getLeftCalf()));
        etWaist.setText(String.valueOf(bm.getWaist()));
        etChest.setText(String.valueOf(bm.getChest()));
        etShoulder.setText(String.valueOf(bm.getShoulder()));
        dataSource.close();
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

