package com.example.rodri.letsworkout.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Day;
import com.example.rodri.letsworkout.model.Routine;
import com.example.rodri.letsworkout.model.TrainingRoutine;

import java.util.List;

/**
 * Created by rodri on 9/30/2016.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private Activity activity;
    private List<Day> days;
    private MyDataSource dataSource;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView displayDay;
        public TextView displayRoutineName;
        public TextView displayMuscleGroups;

        public MyViewHolder(View v) {
            super(v);

            displayDay = (TextView) v.findViewById(R.id.customScheduleItem_txtDay);
            displayRoutineName = (TextView) v.findViewById(R.id.customScheduleItem_txtRoutineName);
            displayMuscleGroups = (TextView) v.findViewById(R.id.customScheduleItem_txtMuscleGroups);

            // set on click listener goes here (I think so)
        }

    }


    public ScheduleAdapter(Activity activity, List<Day> days) {
        this.activity = activity;
        this.days = days;
        this.dataSource = new MyDataSource(activity);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_schedule_item, parent, false);

        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Day day = days.get(position);
        holder.displayDay.setText(day.getName());

        // Routine - routineName
        // List<RoutineMuscleGroup> - muscle group name
        dataSource.open();
        Routine routine = dataSource.getRoutineByUserId()

    }
}
