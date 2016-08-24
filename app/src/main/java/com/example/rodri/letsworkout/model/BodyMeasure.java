package com.example.rodri.letsworkout.model;

/**
 * Created by rodri on 8/12/2016.
 */
public class BodyMeasure {

    private long id;
    private double rightUpperArm;
    private double leftUpperArm;
    private double rightForearm;
    private double leftForearm;
    private double chest;
    private double rightThigh;
    private double leftThigh;
    private double rightCalf;
    private double leftCalf;
    private double waist;
    private double shoulder;
    private double weight;
    private double height;
    private int date;

    public BodyMeasure() {
        id = 0;
        rightUpperArm = 0.00;
        leftUpperArm = 0.00;
        rightForearm = 0.00;
        leftForearm = 0.00;
        chest = 0.00;
        rightThigh = 0.00;
        leftThigh = 0.00;
        rightCalf = 0.00;
        leftCalf = 0.00;
        waist = 0.00;
        shoulder = 0.00;
        weight = 0.00;
        height = 0.00;
        date = 0;
    }

    public BodyMeasure(long id, double rightUpperArm, double leftUpperArm, double rightForearm, double leftForearm,
                       double chest, double rightThigh, double leftThigh, double rightCalf, double leftCalf,
                       double waist, double shoulder, double weight, double height, int date) {
        this.id = id;
        this.rightUpperArm = rightUpperArm;
        this.leftUpperArm = leftUpperArm;
        this.rightForearm = rightForearm;
        this.leftForearm = leftForearm;
        this.chest = chest;
        this.rightThigh = rightThigh;
        this.leftThigh = leftThigh;
        this.rightCalf = rightCalf;
        this.leftCalf = leftCalf;
        this.waist = waist;
        this.shoulder = shoulder;
        this.weight = weight;
        this.height = height;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public double getRightUpperArm() {
        return rightUpperArm;
    }

    public double getLeftUpperArm() {
        return leftUpperArm;
    }

    public double getRightForearm() {
        return rightForearm;
    }

    public double getLeftForearm() {
        return leftForearm;
    }

    public double getRightThigh() {
        return rightThigh;
    }

    public double getChest() {
        return chest;
    }

    public double getLeftThigh() {
        return leftThigh;
    }

    public double getRightCalf() {
        return rightCalf;
    }

    public double getWaist() {
        return waist;
    }

    public double getLeftCalf() {
        return leftCalf;
    }

    public double getShoulder() {
        return shoulder;
    }

    public double getWeight() { return weight; }

    public double getHeight() { return height; }

    public int getDate() { return date; }

    public void setShoulder(double shoulder) {
        this.shoulder = shoulder;
    }

    public void setRightUpperArm(double rightUpperArm) {
        this.rightUpperArm = rightUpperArm;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLeftUpperArm(double leftUpperArm) {
        this.leftUpperArm = leftUpperArm;
    }

    public void setRightForearm(double rightForearm) {
        this.rightForearm = rightForearm;
    }

    public void setLeftForearm(double leftForearm) {
        this.leftForearm = leftForearm;
    }

    public void setChest(double chest) {
        this.chest = chest;
    }

    public void setRightThigh(double rightThigh) {
        this.rightThigh = rightThigh;
    }

    public void setLeftThigh(double leftThigh) {
        this.leftThigh = leftThigh;
    }

    public void setRightCalf(double rightCalf) {
        this.rightCalf = rightCalf;
    }

    public void setLeftCalf(double leftCalf) {
        this.leftCalf = leftCalf;
    }

    public void setWaist(double waist) {
        this.waist = waist;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
