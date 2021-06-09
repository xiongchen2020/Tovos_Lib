package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;
import java.util.List;

public class ResStatisticsRecords  implements Serializable {
    private int totalDuration;
    private int avgDuration;
    private List<String> names;
    private List<Double> values;

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getAvgDuration() {
        return avgDuration;
    }

    public void setAvgDuration(int avgDuration) {
        this.avgDuration = avgDuration;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }
}
