package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 8/12/2016.
 */
public class Routine {

    private long id;
    private long dayId;
    private long exerciseRepetitionId;

    public Routine() {}

    public Routine(long id, long dayId, long exerciseRepetitionId) {
        this.id = id;
        this.dayId = dayId;
        this.exerciseRepetitionId = exerciseRepetitionId;
    }

    public long getId() {
        return id;
    }

    public long getDayId() {
        return dayId;
    }

    public long getExerciseRepetitionId() {
        return exerciseRepetitionId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDayId(long dayId) {
        this.dayId = dayId;
    }

    public void setExerciseRepetitionId(long exerciseRepetitionId) {
        this.exerciseRepetitionId = exerciseRepetitionId;
    }
}
