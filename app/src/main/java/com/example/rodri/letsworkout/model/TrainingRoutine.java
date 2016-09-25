package com.example.rodri.letsworkout.model;

import android.app.Activity;

import com.example.rodri.letsworkout.database.MyDataSource;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rodri on 9/25/2016.
 */
public class TrainingRoutine {

    private long userId;
    private List<Routine> routines;
    private HashMap<Long, List<RoutineExercises>> routineExercises; // Long - routineId
    private HashMap<Long, List<RoutineMuscleGroup>> routineMuscleGroups; // Long - routineId
    private MyDataSource dataSource;
    private Activity activity;

    public TrainingRoutine() {}

    public TrainingRoutine(long userId, Activity activity) {
        this.userId = userId;
        this.activity = activity;
        this.dataSource = new MyDataSource(activity);
        getDataFromDataSource();
    }

    private void getDataFromDataSource() {
        dataSource.open();
        routines = dataSource.getAllRoutines(userId);
        for (Routine r: routines) {
            routineExercises.put(r.getId(), dataSource.getRoutineExercises(r.getId()));
            routineMuscleGroups.put(r.getId(), dataSource.getRoutineMuscleGroups(r.getId()));
        }
        dataSource.close();
    }

    public List<RoutineExercises> getRoutineExercises() {
        return (List) routineExercises.values();
    }

    public List<RoutineMuscleGroup> getRoutineMuscleGroups() {
        return (List) routineMuscleGroups.values();
    }

    public List<Routine> getRoutines() {
        return routines;
    }

}
