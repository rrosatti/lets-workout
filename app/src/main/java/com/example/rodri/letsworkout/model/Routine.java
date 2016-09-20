package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 8/12/2016.
 */
public class Routine {

    private long id;
    private long dayId;
    private long userId;

    public Routine() {}

    public Routine(long id, long dayId, long userId) {
        this.id = id;
        this.dayId = dayId;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public long getDayId() {
        return dayId;
    }

    public long getUserId() { return userId; }

    public void setId(long id) {
        this.id = id;
    }

    public void setDayId(long dayId) {
        this.dayId = dayId;
    }

    public void setUserId(long userId) { this.userId = userId; }

}
