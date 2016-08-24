package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 8/18/2016.
 */
public class UserBody {

    private long id;
    private long userId;
    private long bodyMeasuresId;
    private BodyMeasure bodyMeasure;


    public UserBody() {}

    public UserBody(long id, long userId, long bodyMeasuresId, BodyMeasure bodyMeasure) {
        this.id = id;
        this.userId = userId;
        this.bodyMeasuresId = bodyMeasuresId;
        this.bodyMeasure  = bodyMeasure;
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

    public BodyMeasure getBodyMeasure() {
        return bodyMeasure;
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

    public void setBodyMeasure(BodyMeasure bodyMeasure) {
        this.bodyMeasure = bodyMeasure;
    }


}
