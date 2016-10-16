package com.example.rodri.letsworkout.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.BodyMeasure;
import com.example.rodri.letsworkout.model.UserBody;
import com.example.rodri.letsworkout.util.Util;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rodri on 10/15/2016.
 */

public class BodyMeasurementsChartActivity extends AppCompatActivity {

    private static final String STATE_CHART_LABELS = "labels";
    private static final String STATE_CHART_DATA = "data";

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private BarChart barChart;
    private Button btCompare;
    private Spinner spinnerDates1;
    private Spinner spinnerDates2;

    private MyDataSource dataSource;
    private List<String> dates = new ArrayList<>();
    private List<UserBody> userBodies = new ArrayList<>();
    private long selectedDate1 = -1;
    private long selectedDate2 = -1;

    private List<BarEntry> entries1 = new ArrayList<>();
    private List<BarEntry> entries2 = new ArrayList<>();
    private ArrayList<String> labels = new ArrayList<>();
    private BarDataSet barDataSet1;
    private BarDataSet barDataSet2;
    private BarData barData;
    private ArrayList<IBarDataSet> dataSets = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_measurements_chart);

        iniViews();
        dataSource = new MyDataSource(this);

        if (savedInstanceState != null) {
            labels = (ArrayList<String>) savedInstanceState.getSerializable(STATE_CHART_LABELS);
            dataSets = (ArrayList<IBarDataSet>) savedInstanceState.getSerializable(STATE_CHART_DATA);

            barData = new BarData(labels, dataSets);
            barChart.setData(barData);
            barChart.setDescription("Body Measurement Comparison");
            barChart.invalidate();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dataSource.open();
        userBodies = dataSource.getAllUserBodies(Authentication.getInstance().getUserId());
        if (userBodies == null) {
            Toast.makeText(BodyMeasurementsChartActivity.this, R.string.toast_no_data_to_show, Toast.LENGTH_SHORT).show();
            finish();
        }

        for (UserBody ub: userBodies) {
            BodyMeasure bm = dataSource.getBodyMeasure(ub.getBodyMeasuresId());
            dates.add(Util.convertToDate(bm.getDate(), "dd/MM/yyyy"));
        }

        ArrayAdapter adapter = new ArrayAdapter(BodyMeasurementsChartActivity.this, android.R.layout.simple_list_item_1, dates);
        spinnerDates1.setAdapter(adapter);
        spinnerDates2.setAdapter(adapter);

        spinnerDates1.setSelection(0);
        spinnerDates2.setSelection(dates.size() - 1);

        spinnerDates1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDate1 = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDates2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDate2 = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                Thread.sleep(1000);
                dataSource.open();

                BodyMeasure bm1 = dataSource.getBodyMeasure(userBodies.get( (int) selectedDate1 - 1).getBodyMeasuresId());
                BodyMeasure bm2 = dataSource.getBodyMeasure(userBodies.get( (int) selectedDate2 - 1).getBodyMeasuresId());

                setEntriesAndLabels(bm1, bm2);

                barDataSet1 = new BarDataSet(entries1, dates.get( (int) (selectedDate1 - 1)));
                barDataSet1.setColor(Color.rgb(104, 241, 175));
                barDataSet2 = new BarDataSet(entries2, dates.get( (int) (selectedDate2 - 1)));
                barDataSet2.setColor(Color.rgb(164, 228, 251));

                dataSets.add(barDataSet1);
                dataSets.add(barDataSet2);

                barData = new BarData(labels, dataSets);

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
            barChart.setData(barData);
            barChart.setDescription("Body Measurement Comparison");
            barChart.invalidate(); // <--- Please, do not EVER forget this guy.
        }
    }

    public void iniViews() {
        toolbar = (Toolbar) findViewById(R.id.activityBodyMeasurementsChart_toolbar);
        progressBar = (ProgressBar) findViewById(R.id.activityBodyMeasurementsChart_progressBar);
        barChart = (BarChart) findViewById(R.id.activityBodyMeasurementsChart_barChart);
        btCompare = (Button) findViewById(R.id.activityBodyMeasurementsChart_btCompare);
        spinnerDates1 = (Spinner) findViewById(R.id.activityBodyMeasurementsChart_spinner1);
        spinnerDates2 = (Spinner) findViewById(R.id.activityBodyMeasurementsChart_spinner2);
    }

    public void setEntriesAndLabels(BodyMeasure bm1, BodyMeasure bm2) {
        int count = 0;
        entries1.add(new BarEntry( (float) bm1.getRightUpperArm(), count++));
        entries1.add(new BarEntry( (float) bm1.getLeftUpperArm(), count++));
        entries1.add(new BarEntry( (float) bm1.getRightForearm(), count++));
        entries1.add(new BarEntry( (float) bm1.getLeftForearm(), count++));
        entries1.add(new BarEntry( (float) bm1.getChest(), count++));
        entries1.add(new BarEntry( (float) bm1.getRightThigh(), count++));
        entries1.add(new BarEntry( (float) bm1.getLeftThigh(), count++));
        entries1.add(new BarEntry( (float) bm1.getRightCalf(), count++));
        entries1.add(new BarEntry( (float) bm1.getLeftCalf(), count++));
        entries1.add(new BarEntry( (float) bm1.getWaist(), count++));
        entries1.add(new BarEntry( (float) bm1.getShoulder(), count++));
        entries1.add(new BarEntry( (float) bm1.getHeight(), count++));

        count = 0;
        entries2.add(new BarEntry( (float) bm2.getRightUpperArm(), count++));
        entries2.add(new BarEntry( (float) bm2.getLeftUpperArm(), count++));
        entries2.add(new BarEntry( (float) bm2.getRightForearm(), count++));
        entries2.add(new BarEntry( (float) bm2.getLeftForearm(), count++));
        entries2.add(new BarEntry( (float) bm2.getChest(), count++));
        entries2.add(new BarEntry( (float) bm2.getRightThigh(), count++));
        entries2.add(new BarEntry( (float) bm2.getLeftThigh(), count++));
        entries2.add(new BarEntry( (float) bm2.getRightCalf(), count++));
        entries2.add(new BarEntry( (float) bm2.getLeftCalf(), count++));
        entries2.add(new BarEntry( (float) bm2.getWaist(), count++));
        entries2.add(new BarEntry( (float) bm2.getShoulder(), count++));
        entries2.add(new BarEntry( (float) bm2.getHeight(), count++));

        String[] bodyMeasures = getResources().getStringArray(R.array.body_measure_items);
        labels = new ArrayList<>(Arrays.asList(bodyMeasures));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(STATE_CHART_LABELS, (Serializable) labels);
        outState.putSerializable(STATE_CHART_DATA, (Serializable) dataSets);
    }
}
