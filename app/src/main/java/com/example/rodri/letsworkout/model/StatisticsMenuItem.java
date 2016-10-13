package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 10/13/2016.
 */

public class StatisticsMenuItem {

    private String title;
    private int iconId;

    public StatisticsMenuItem() { }

    public StatisticsMenuItem(String title, int iconId) {
        this.title = title;
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
