package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 8/12/2016.
 */
public class Routine {

    private long id;
    private long dayId;
    private long userId;
    private boolean chosen;
    private String name;

    public Routine() {}

    public Routine(long id, long dayId, long userId, boolean chosen, String name) {
        this.id = id;
        this.dayId = dayId;
        this.userId = userId;
        this.chosen = chosen;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getDayId() {
        return dayId;
    }

    public long getUserId() { return userId; }

    public boolean getChosen() { return chosen; }

    public String getName() { return name; }

    public void setId(long id) {
        this.id = id;
    }

    public void setDayId(long dayId) {
        this.dayId = dayId;
    }

    public void setUserId(long userId) { this.userId = userId; }

    public void setChosen(boolean chosen) { this.chosen = chosen; }

    public void setName(String name) { this.name = name; }

}
