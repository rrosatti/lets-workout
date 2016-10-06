package com.example.rodri.letsworkout.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.interfaces.DataTransferInterface;
import com.example.rodri.letsworkout.model.Exercise;
import com.example.rodri.letsworkout.model.ExerciseRepetition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 9/6/2016.
 */
public class ExerciseRepetitionAdapter extends ArrayAdapter<ExerciseRepetition> {

    private Activity activity;
    private List<ExerciseRepetition> exercises;
    private LayoutInflater inflater = null;
    private MyDataSource dataSource;
    DataTransferInterface dtTransferInterface;
    private List<ExerciseRepetition> removedExercises;

    public ExerciseRepetitionAdapter(Activity activity, int textViewResourceId, List<ExerciseRepetition> exercises) {
        super(activity, textViewResourceId, exercises);
        try {
            this.activity = activity;
            this.exercises = exercises;
            this.dataSource = new MyDataSource(activity.getApplicationContext());
            this.removedExercises = new ArrayList<>();

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ExerciseRepetitionAdapter(Activity activity, int textViewResourceId, List<ExerciseRepetition> exercises,
                                     DataTransferInterface dtTransferInterface) {
        this (activity, textViewResourceId, exercises);
        this.dtTransferInterface = dtTransferInterface;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ExerciseRepetition getItem(int position) {
        return exercises.get(position);
    }

    public class ViewHolder {
        public TextView displayExerciseName;
        public TextView displaySets;
        public TextView displayReps;
        public ImageView displayRemoveExercise;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.custom_exercise_item, null);

            holder.displayExerciseName = (TextView) v.findViewById(R.id.customExerciseItem_txtExerciseName);
            holder.displaySets = (TextView) v.findViewById(R.id.customExerciseItem_txtSets);
            holder.displayReps = (TextView) v.findViewById(R.id.customExerciseItem_txtReps);
            holder.displayRemoveExercise = (ImageView) v.findViewById(R.id.customExerciseItem_imgRemoveExercise);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.displaySets.setText(String.valueOf(exercises.get(position).getSets()));
        holder.displayReps.setText(String.valueOf(exercises.get(position).getReps()));

        long exerciseId = exercises.get(position).getExerciseId();
        dataSource.open();
        final Exercise exercise = dataSource.getExercise(exerciseId);
        dataSource.close();

        holder.displayExerciseName.setText(exercise.getName());

        holder.displayRemoveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removedExercises.add(exercises.get(position));
                exercises.remove(position);
                dtTransferInterface.setValues(removedExercises);
                notifyDataSetChanged();
            }
        });

        return v;

    }

    public void addExercise(ExerciseRepetition exerciseRepetition) {
        exercises.add(exerciseRepetition);
        notifyDataSetChanged();
    }
}
