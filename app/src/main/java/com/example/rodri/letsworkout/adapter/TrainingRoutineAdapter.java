package com.example.rodri.letsworkout.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.activity.RoutineActivity;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Authentication;
import com.example.rodri.letsworkout.model.Day;
import com.example.rodri.letsworkout.model.MuscleGroup;
import com.example.rodri.letsworkout.model.Routine;

import java.util.List;

/**
 * Created by rodri on 9/25/2016.
 */
public class TrainingRoutineAdapter extends ArrayAdapter<Routine> {

    private Activity activity;
    private List<Routine> routines;
    private LayoutInflater inflater = null;
    private MyDataSource dataSource;
    private Fragment fragment;

    public TrainingRoutineAdapter(Activity activity, int textViewResourceId, List<Routine> routines, Fragment fragment) {
        super(activity, textViewResourceId, routines);
        try {
            this.activity = activity;
            this.routines = routines;
            this.dataSource = new MyDataSource(activity);
            this.fragment = fragment;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return routines.size();
    }

    public class ViewHolder {
        public TextView displayDay;
        public TextView displayRoutineName;
        public TextView displayMuscleGroup;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.custom_routine_item, null);

            holder.displayDay = (TextView) v.findViewById(R.id.customRoutineItem_txtDay);
            holder.displayMuscleGroup = (TextView) v.findViewById(R.id.customRoutineItem_txtMuscleGroup);
            holder.displayRoutineName = (TextView) v.findViewById(R.id.customRoutineItem_txtRoutineName);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        // I need to display the DAY(1) and the MUSCLE GROUPS (2)
        //
        // 1 - In Routine we have the dayId
        //      1.1 - Get the correct day according to dayId (dataSource)
        // 2 - In Routine we have the id (routine ID)
        //      2.1 - Get the correct Muscle Groups according to id (dataSource)
        long dayId = routines.get(position).getDayId();
        final long routineId = routines.get(position).getId();
        dataSource.open();
        Day day = dataSource.getDay(dayId);
        List<MuscleGroup> muscleGroups = dataSource.getMuscleGroups(routineId);
        dataSource.close();

        StringBuffer muscleGroupNames = new StringBuffer();
        for (int i = 0; i < muscleGroups.size(); i++) {
          muscleGroupNames.append(muscleGroups.get(i).getName());
          if (i < muscleGroups.size() - 1) {
            muscleGroupNames.append(" / ");
          }
        }

        if (!routines.get(position).getName().equals(day.getName())) {
            holder.displayRoutineName.setText(routines.get(position).getName());
            holder.displayRoutineName.setWidth(0);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            p.weight = 1;
            holder.displayRoutineName.setLayoutParams(p);
        } else {

        }

        if (routines.get(position).getChosen() == 1) {
            v.setBackgroundColor(activity.getResources().getColor(R.color.background_chosen_routine));
            holder.displayDay.setTextColor(activity.getResources().getColor(R.color.background_project));
            holder.displayMuscleGroup.setTextColor(activity.getResources().getColor(R.color.background_project));
            holder.displayRoutineName.setTextColor(activity.getResources().getColor(R.color.background_project));
        }

        holder.displayDay.setText(day.getName());
        holder.displayMuscleGroup.setText(muscleGroupNames);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, RoutineActivity.class);
                i.putExtra("routineId", routineId);
                fragment.startActivityForResult(i, 2);
            }
        });

        return v;
    }
}
