package com.example.rodri.letsworkout.model;

import java.io.Serializable;

/**
 * Created by rodri on 8/12/2016.
 */
public class ExerciseRepetition implements Serializable {

    private long id;
    private long exerciseId;
    private int sets;
    private int reps;

    public ExerciseRepetition() {}

    public ExerciseRepetition(long id, long exerciseId, int sets, int reps) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;
    }

    public long getId() {
        return id;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

}
