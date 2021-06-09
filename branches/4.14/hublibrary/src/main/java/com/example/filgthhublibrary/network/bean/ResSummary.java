package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;

public class ResSummary implements Serializable {

    private String account;
    private String teamId;
    private String sn;
    private String droneType;
    private double duration;
    private double distance;
    private double maxHeight;
    private String takeoffLongitude;
    private String takeoffLatitude;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDroneType() {
        return droneType;
    }

    public void setDroneType(String droneType) {
        this.droneType = droneType;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public String getTakeoffLongitude() {
        return takeoffLongitude;
    }

    public void setTakeoffLongitude(String takeoffLongitude) {
        this.takeoffLongitude = takeoffLongitude;
    }

    public String getTakeoffLatitude() {
        return takeoffLatitude;
    }

    public void setTakeoffLatitude(String takeoffLatitude) {
        this.takeoffLatitude = takeoffLatitude;
    }
}
