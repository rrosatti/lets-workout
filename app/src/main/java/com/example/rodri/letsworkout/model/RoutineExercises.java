package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 9/5/2016.
 */
public class RoutineExercises {

    private long id;
    private long routineId;
    private long exerciseRepetitionId;

    public RoutineExercises() {}

    public RoutineExercises (long id, long routineId, long exerciseRepetitionId) {
        this.id = id;
        this.routineId = routineId;
        this.exerciseRepetitionId = exerciseRepetitionId;
    }

    public long getRoutineId() {
        return routineId;
    }

    public long getId() {
        return id;
    }

    public long getExerciseRepetitionId() {
        return exerciseRepetitionId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRoutineId(long routineId) {
        this.routineId = routineId;
    }

    public void setExerciseRepetitionId(long exerciseRepetitionId) {
        this.exerciseRepetitionId = exerciseRepetitionId;
    }
}
