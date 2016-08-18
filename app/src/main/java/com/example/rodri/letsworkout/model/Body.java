package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 8/18/2016.
 */
public class Body {

    private long id;
    private long user_id;
    private long body_measures_id;
    private double weight;
    private double height;
    private BodyMeasure bodyMeasure;


    public Body() {}

    public Body(long id, long user_id, long body_measures_id, double weight, double height, BodyMeasure bodyMeasure) {
        this.id = id;
        this.user_id = user_id;
        this.body_measures_id = body_measures_id;
        this.weight = weight;
        this.height = height;
        this.bodyMeasure  = bodyMeasure;
    }

    public long getId() {
        return id;
    }

    public long getUser_id() {
        return user_id;
    }

    public long getBody_measures_id() {
        return body_measures_id;
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

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setBody_measures_id(long body_measures_id) {
        this.body_measures_id = body_measures_id;
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
