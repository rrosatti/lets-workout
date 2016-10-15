package com.example.rodri.letsworkout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.BodyMeasure;
import com.example.rodri.letsworkout.model.UserBody;
import com.example.rodri.letsworkout.util.Util;
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

    private MyDataSource dataSource;

    private LineData lineData;
    private LineDataSet lineDataSet;
    private List<Entry> entries = new ArrayList<>();
    private List<String> labels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_chart);

        iniViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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

            List<UserBody> userBodies = dataSource.getAllUserBodies(Authentication.getInstance().getUserId());

            for (int i = 0; i < userBodies.size(); i++) {
                UserBody ub = userBodies.get(i);
                System.out.println("User body id: " + ub.getBodyMeasuresId());
                BodyMeasure bm = dataSource.getBodyMeasure(ub.getBodyMeasuresId());
                labels.add(Util.convertToDate(bm.getDate(), "dd/MM/yy"));
                entries.add(new Entry( (float) bm.getWeight(), i));
            }

            for (int i = 0; i < labels.size(); i++) {
                System.out.println("label: " + labels.get(i) + " entry: " + entries.get(i));
            }

            lineDataSet = new LineDataSet(entries, "# Weight");
            lineData = new LineData(labels, lineDataSet);
            lineChart.setData(lineData);
            lineChart.setDescription("Weight through time");
            //lineChart.animateY(1200);

            dataSource.close();

        } catch (Exception e) {
            dataSource.close();
            e.printStackTrace();
        }
    }

    public void iniViews() {
        toolbar = (Toolbar) findViewById(R.id.activityWeightChart_toolbar);
        lineChart = (LineChart) findViewById(R.id.activityWeighChart_lineChart);
    }
}
