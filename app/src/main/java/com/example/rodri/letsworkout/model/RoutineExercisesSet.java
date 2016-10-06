package com.example.rodri.letsworkout.model;

import android.app.Activity;

import com.example.rodri.letsworkout.database.MyDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 10/5/2016.
 */
public class RoutineExercisesSet {

    private long routineId;
    private long dayId;
    private Routine routine;
    private MyDataSource dataSource;
    private List<Exercise> exercises;
    private List<RoutineExercises> routineExercises;
    private List<ExerciseRepetition> exerciseRepetitions;

    public RoutineExercisesSet(long routineId, long dayId, Activity activity) {
        this.routineId = routineId;
        this.dayId = dayId;
        this.dataSource = new MyDataSource(activity);
        getData();
    }

    public void getData() {
        dataSource.open();
        routine = dataSource.getRoutine(Authentication.getInstance().getUserId(), dayId);
        routineExercises = dataSource.getRoutineExercises(routineId);
        exerciseRepetitions = new ArrayList<>();
        exercises = new ArrayList<>();
        for (int i = 0; i < routineExercises.size(); i++) {
            long exerciseRepetitionId = routineExercises.get(i).getExerciseRepetitionId();
            exerciseRepetitions.add(dataSource.getExerciseRepetition(exerciseRepetitionId));
            long exerciseId = exerciseRepetitions.get(i).getExerciseId();
            exercises.add(dataSource.getExercise(exerciseId));
        }

        dataSource.close();
    }

    public Routine getRoutine() {
        return  routine;
    }

    public List<ExerciseRepetition> getExerciseRepetitions() {
        return exerciseRepetitions;
    }

    public List<RoutineExercises> getRoutineExercises() {
        return routineExercises;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

}
