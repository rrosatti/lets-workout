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

    public BodyMeasure() {}

    public BodyMeasure(long id, double rightUpperArm, double leftUpperArm, double rightForearm, double leftForearm,
                       double chest, double rightThigh, double leftThigh, double rightCalf, double leftCalf,
                       double waist, double shoulder) {
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
}
