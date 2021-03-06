package com.example.rodri.letsworkout.model;

import android.app.Activity;

import com.example.rodri.letsworkout.database.MyDataSource;

import java.util.List;

/**
 * Created by rodri on 10/2/2016.
 */
public class RoutineMuscleGroupSet {

    private long routineId;
    private long userId;
    private long dayId;
    private MyDataSource dataSource;
    private List<MuscleGroup> muscleGroups;
    private StringBuffer muscleGroupNames;

    public RoutineMuscleGroupSet(long routineId, Activity activity) {
        this.routineId = routineId;
        this.dataSource = new MyDataSource(activity);
        this.muscleGroupNames = new StringBuffer();
        getDataFromRoutineId();
    }

    public RoutineMuscleGroupSet(long userId, long dayId, Activity activity) {
        this.userId = userId;
        this.dayId = dayId;
        this.dataSource = new MyDataSource(activity);
        this.muscleGroupNames = new StringBuffer();
        getDataFromUserAndDayId();
    }

    public void getDataFromRoutineId() {
        dataSource.open();
        muscleGroups = dataSource.getMuscleGroups(routineId);
        for (int i = 0; i < muscleGroups.size(); i++) {
            muscleGroupNames.append(muscleGroups.get(i).getName());
            if (i < muscleGroups.size() - 1) {
                muscleGroupNames.append(" / ");
            }
        }
        dataSource.close();
    }

    public void getDataFromUserAndDayId() {
        dataSource.open();
        Routine routine = dataSource.getRoutine(userId, dayId);
        muscleGroups = dataSource.getMuscleGroups(routine.getId());
        for (int i = 0; i < muscleGroups.size(); i++) {
            muscleGroupNames.append(muscleGroups.get(i).getName());
            if (i < muscleGroups.size() - 1) {
                muscleGroupNames.append(" / ");
            }
        }
        dataSource.close();
    }

    public StringBuffer getMuscleGroupNames() {
        return muscleGroupNames;
    }

    public List<MuscleGroup> getMuscleGroups() { return muscleGroups; }
}
