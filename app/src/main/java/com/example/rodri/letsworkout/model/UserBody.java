package com.example.rodri.letsworkout.model;

import com.example.rodri.letsworkout.database.MyDataSource;

/**
 * Created by rodri on 8/18/2016.
 */
public class UserBody {

    private long id;
    private long userId;
    private long bodyMeasuresId;

    public UserBody() {}

    public UserBody(long id, long userId, long bodyMeasuresId) {
        this.id = id;
        this.userId = userId;
        this.bodyMeasuresId = bodyMeasuresId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getBodyMeasuresId() {
        return bodyMeasuresId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setBodyMeasuresId(long bodyMeasuresId) {
        this.bodyMeasuresId = bodyMeasuresId;
    }


}
