package com.example.rodri.letsworkout.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.github.mikephil.charting.charts.BarChart;

/**
 * Created by rodri on 10/15/2016.
 */

public class BodyMeasurementsChartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private BarChart barChart;
    private Button btCompare;

    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_measurements_chart);

        iniViews();
        dataSource = new MyDataSource(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataFromDatabase task = new GetDataFromDatabase();
                task.execute("");
            }
        });
    }

    private class GetDataFromDatabase extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(5000);
                dataSource.open();

                dataSource.close();
            } catch (Exception e) {
                dataSource.close();
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);

        }
    }

    public void iniViews() {
        toolbar = (Toolbar) findViewById(R.id.activityBodyMeasurementsChart_toolbar);
        progressBar = (ProgressBar) findViewById(R.id.activityBodyMeasurementsChart_progressBar);
        barChart = (BarChart) findViewById(R.id.activityBodyMeasurementsChart_barChart);
        btCompare = (Button) findViewById(R.id.activityBodyMeasurementsChart_btCompare);
    }
}
