package com.example.rodri.letsworkout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.UserBody;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 10/14/2016.
 */

public class WeightChartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LineChart lineChart;

    private List<Double> weights;
    private MyDataSource dataSource;

    private LineData lineData;
    private LineDataSet lineDataSet;
    private List<Entry> entries = new ArrayList<>();
    private List<String> labels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_chart);

        toolbar = (Toolbar) findViewById(R.id.activityWeightChart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dataSource = new MyDataSource(this);

        try {
            dataSource.open();
            if (!dataSource.isThereAnyBodyRegistered(Authentication.getInstance().getUserId())) {
                Toast.makeText(WeightChartActivity.this, R.string.toast_no_body_registered, Toast.LENGTH_SHORT).show();
                return;
            }



        } catch (Exception e) {
            dataSource.close();
            e.printStackTrace();
        }
    }
}
