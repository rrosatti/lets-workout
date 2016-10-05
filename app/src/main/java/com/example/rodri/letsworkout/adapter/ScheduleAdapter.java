package com.example.rodri.letsworkout.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.activity.RoutineActivity;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.Day;
import com.example.rodri.letsworkout.model.Routine;
import com.example.rodri.letsworkout.model.RoutineMuscleGroupSet;

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
        public TextView dayId;

        public MyViewHolder(View v) {
            super(v);

            displayDay = (TextView) v.findViewById(R.id.customScheduleItem_txtDay);
            displayRoutineName = (TextView) v.findViewById(R.id.customScheduleItem_txtRoutineName);
            displayMuscleGroups = (TextView) v.findViewById(R.id.customScheduleItem_txtMuscleGroups);
            dayId = (TextView) v.findViewById(R.id.customScheduleItem_dayId);

            // set on click listener goes here (I think so)
            // create new Intent to ScheduleActivity
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dayId.getText().toString() != "") {
                        Intent i = new Intent(activity, RoutineActivity.class);
                        i.putExtra("userId", Authentication.getInstance().getUserId());
                        i.putExtra("dayId", v.getId());
                        System.out.println("V.getId() " + Long.valueOf(dayId.getText().toString()));
                        activity.startActivity(i);
                    }
                }
            });


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
        Routine routine = dataSource.getRoutine(Authentication.getInstance().getUserId(), day.getId());

        if (routine != null) {
            RoutineMuscleGroupSet muscleGroupSet = new RoutineMuscleGroupSet(routine.getId(), activity);
            StringBuffer muscleGroupNames = muscleGroupSet.getMuscleGroupNames();
            holder.displayMuscleGroups.setText(muscleGroupNames);
            holder.dayId.setText(String.valueOf(day.getId()));

            if (!routine.getName().equals(day.getName())) {
                holder.displayRoutineName.setText(routine.getName());
            } else {
                holder.displayRoutineName.setHeight(0);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                p.topMargin = 0;
                holder.displayRoutineName.setLayoutParams(p);
            }

        }

    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
