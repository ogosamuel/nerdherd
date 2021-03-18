package com.example.nerdherd;

public class MeasurementTrial extends Trial {

    private int totalMeasurementCount;

    public MeasurementTrial(int totalMeasurementCount) {
        this.totalMeasurementCount = totalMeasurementCount;
    }

    public int totalMeasureCount(){
        return totalMeasurementCount;
    }
}
