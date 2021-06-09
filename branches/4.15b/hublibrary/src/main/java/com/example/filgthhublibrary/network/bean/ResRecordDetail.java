package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResRecordDetail implements Serializable {

    private ResSummary summary;
    private List<ResRecordPoint> recordPoints = new ArrayList<>();

    public ResSummary getSummary() {
        return summary;
    }

    public void setSummary(ResSummary summary) {
        this.summary = summary;
    }

    public List<ResRecordPoint> getRecordPoints() {
        return recordPoints;
    }

    public void setRecordPoints(List<ResRecordPoint> recordPoints) {
        this.recordPoints = recordPoints;
    }
}
