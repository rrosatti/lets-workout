package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 8/18/2016.
 */
public class Body {

    private long id;
    private long userId;
    private long bodyMeasuresId;
    private double weight;
    private double height;
    private BodyMeasure bodyMeasure;


    public Body() {}

    public Body(long id, long userId, long bodyMeasuresId, double weight, double height, BodyMeasure bodyMeasure) {
        this.id = id;
        this.userId = userId;
        this.bodyMeasuresId = bodyMeasuresId;
        this.weight = weight;
        this.height = height;
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

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
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

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setBodyMeasure(BodyMeasure bodyMeasure) {
        this.bodyMeasure = bodyMeasure;
    }


}
