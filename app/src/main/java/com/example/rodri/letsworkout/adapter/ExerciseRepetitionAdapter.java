package com.example.rodri.letsworkout.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodri.letsworkout.R;
import com.example.rodri.letsworkout.database.MyDataSource;
import com.example.rodri.letsworkout.model.Exercise;
import com.example.rodri.letsworkout.model.ExerciseRepetition;

import java.util.List;

/**
 * Created by rodri on 9/7/2016.
 */
public class ExerciseRepetitionAdapter extends RecyclerView.Adapter<ExerciseRepetitionAdapter.MyViewHolder> {

    private Activity activity;
    private List<ExerciseRepetition> exerciseRepetitions;
    private MyDataSource dataSource;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView displayExerciseName;
        public TextView displaySets;
        public TextView displayReps;
        public ImageView displayRemoveExercise;
        public ExerciseRepetition currentExerciseRepetition;

        public MyViewHolder(View v) {
            super(v);

            displayExerciseName = (TextView) v.findViewById(R.id.customExerciseItem_txtExerciseName);
            displaySets = (TextView) v.findViewById(R.id.customExerciseItem_txtSets);
            displayReps = (TextView) v.findViewById(R.id.customExerciseItem_txtReps);
            displayRemoveExercise = (ImageView) v.findViewById(R.id.customExerciseItem_imgRemoveExercise);
        }
    }

    public ExerciseRepetitionAdapter(Activity activity, List<ExerciseRepetition> exerciseRepetitions) {
        this.activity = activity;
        this.exerciseRepetitions = exerciseRepetitions;
        this.dataSource = new MyDataSource(activity.getApplicationContext());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_exercise_item, parent, false);

        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ExerciseRepetition exerciseRepetition = exerciseRepetitions.get(position);
        holder.currentExerciseRepetition = exerciseRepetition;

        holder.displaySets.setText(String.valueOf(exerciseRepetition.getSets()));
        holder.displayReps.setText(String.valueOf(exerciseRepetition.getReps()));

        long exerciseId = exerciseRepetition.getExerciseId();
        dataSource.open();
        final Exercise exercise = dataSource.getExercise(exerciseId);
        dataSource.close();

        holder.displayExerciseName.setText(exercise.getName());

        holder.displayRemoveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseRepetitions.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseRepetitions.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
