package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 9/23/2016.
 */
public class RoutineMuscleGroup {

    private long id;
    private long routineId;
    private long muscleGroupId;

    public RoutineMuscleGroup() {}

    public RoutineMuscleGroup(long id, long routineId, long muscleGroupId) {
        this.id = id;
        this.routineId = routineId;
        this.muscleGroupId = muscleGroupId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoutineId() {
        return routineId;
    }

    public void setRoutineId(long routineId) {
        this.routineId = routineId;
    }

    public long getMuscleGroupId() {
        return muscleGroupId;
    }

    public void setMuscleGroupId(long muscleGroupId) {
        this.muscleGroupId = muscleGroupId;
    }
}
