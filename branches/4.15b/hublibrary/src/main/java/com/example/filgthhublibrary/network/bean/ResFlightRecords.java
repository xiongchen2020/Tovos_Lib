package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;
import java.util.List;
public class ResFlightRecords implements Serializable {
    private List<FlightGetModel> flightGetModelList;
    private int pages;
    private int total;

    public List<FlightGetModel> getFlightGetModelList() {
        return flightGetModelList;
    }

    public void setFlightGetModelList(List<FlightGetModel> flightGetModelList) {
        this.flightGetModelList = flightGetModelList;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class FlightGetModel implements Serializable {

        private String account;
        private int teamId;
        private String droneSN;
        private String droneType;
        private double duration;
        private double distance;
        private double maxHeight;
        private String id;
        private double takeoffLongitude;
        private double takeoffLatitude;


        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getTeamId() {
            return teamId;
        }

        public void setTeamId(int teamId) {
            this.teamId = teamId;
        }

        public String getDroneSN() {
            return droneSN;
        }

        public void setDroneSN(String droneSN) {
            this.droneSN = droneSN;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getTakeoffLongitude() {
            return takeoffLongitude;
        }

        public void setTakeoffLongitude(double takeoffLongitude) {
            this.takeoffLongitude = takeoffLongitude;
        }

        public double getTakeoffLatitude() {
            return takeoffLatitude;
        }

        public void setTakeoffLatitude(double takeoffLatitude) {
            this.takeoffLatitude = takeoffLatitude;
        }
    }
        }