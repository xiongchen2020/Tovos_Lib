package com.example.djilib.dji.component.aircraft.database;

public class CustomIMUData {

    private int index;
    private float accelerometerValue;
    private float gyroscopeValue;
    private boolean isMultiSideCalibrationType = false;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public float getAccelerometerValue() {
        return accelerometerValue;
    }

    public void setAccelerometerValue(float accelerometerValue) {
        this.accelerometerValue = accelerometerValue;
    }

    public float getGyroscopeValue() {
        return gyroscopeValue;
    }

    public void setGyroscopeValue(float gyroscopeValue) {
        this.gyroscopeValue = gyroscopeValue;
    }

    public boolean isMultiSideCalibrationType() {
        return isMultiSideCalibrationType;
    }

    public void setMultiSideCalibrationType(boolean multiSideCalibrationType) {
        isMultiSideCalibrationType = multiSideCalibrationType;
    }
}
