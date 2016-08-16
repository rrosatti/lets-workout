package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 8/16/2016.
 */
public class DrawerItem {

    private String title;
    private int icon;

    public DrawerItem() {}

    public DrawerItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
