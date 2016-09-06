package com.example.rodri.letsworkout.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Exercise;
import com.example.rodri.letsworkout.model.ExerciseRepetition;

import java.util.List;

/**
 * Created by rodri on 9/6/2016.
 */
public class ExerciseRepetitionAdapter extends ArrayAdapter<ExerciseRepetition> {

    private Activity activity;
    private List<ExerciseRepetition> exercises;
    private LayoutInflater inflater = null;
    private MyDataSource dataSource;

    public ExerciseRepetitionAdapter(Activity activity, int textViewResourceId, List<ExerciseRepetition> exercises) {
        super(activity, textViewResourceId, exercises);
        try {
            this.activity = activity;
            this.exercises = exercises;
            this.dataSource = new MyDataSource(activity.getApplicationContext());

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    public class ViewHolder {
        public TextView displayExerciseName;
        public TextView displaySets;
        public TextView displayReps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.custom_exercise_item, null);

            holder.displayExerciseName = (TextView) v.findViewById(R.id.customExerciseItem_txtExerciseName);
            holder.displaySets = (TextView) v.findViewById(R.id.customExerciseItem_txtSets);
            holder.displayReps = (TextView) v.findViewById(R.id.customExerciseItem_txtReps);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.displaySets.setText(exercises.get(position).getSets());
        holder.displayReps.setText(exercises.get(position).getReps());

        long exerciseId = exercises.get(position).getExerciseId();
        dataSource.open();
        Exercise exercise = dataSource.getExercise(exerciseId);
        dataSource.close();

        holder.displayExerciseName.setText(exercise.getName());

        return v;

    }
}
