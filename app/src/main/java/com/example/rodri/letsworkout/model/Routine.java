package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 8/12/2016.
 */
public class Routine {

    private long id;
    private long dayId;

    public Routine() {}

    public Routine(long id, long dayId) {
        this.id = id;
        this.dayId = dayId;
    }

    public long getId() {
        return id;
    }

    public long getDayId() {
        return dayId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDayId(long dayId) {
        this.dayId = dayId;
    }

}
