package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 8/12/2016.
 */
public class Exercise {

    private long id;
    private long muscleGroupId;
    private String name;

    public Exercise() {}

    public Exercise(long id, long muscleGroupId, String name) {
        this.id = id;
        this.muscleGroupId = muscleGroupId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getMuscleGroupId() {
        return muscleGroupId;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMuscleGroupId(long muscleGroupId) {
        this.muscleGroupId = muscleGroupId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
