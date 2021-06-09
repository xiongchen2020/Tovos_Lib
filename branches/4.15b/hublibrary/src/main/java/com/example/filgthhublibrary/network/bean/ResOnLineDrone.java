package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;
import java.util.List;

public class ResOnLineDrone implements Serializable {
    private List<DroneOnlineModel> list;

    public List<DroneOnlineModel> getList() {
        return list;
    }

    public void setList(List<DroneOnlineModel> list) {
        this.list = list;
    }

    public static class DroneOnlineModel{
        private String sn;//sn码
        private String name;
        private String type;
        private double teamId;
        private double date;
        private double lat;
        private double lng;
        private double altitude;//经纬高度
        private double yaw;
        private double batteryLevel;
        private double homeLat;
        private double homeLng;
        private String account;
        private double liveStatus;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getTeamId() {
            return teamId;
        }

        public void setTeamId(double teamId) {
            this.teamId = teamId;
        }

        public double getDate() {
            return date;
        }

        public void setDate(double date) {
            this.date = date;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getAltitude() {
            return altitude;
        }

        public void setAltitude(double altitude) {
            this.altitude = altitude;
        }

        public double getYaw() {
            return yaw;
        }

        public void setYaw(double yaw) {
            this.yaw = yaw;
        }

        public double getBatteryLevel() {
            return batteryLevel;
        }

        public void setBatteryLevel(double batteryLevel) {
            this.batteryLevel = batteryLevel;
        }

        public double getHomeLat() {
            return homeLat;
        }

        public void setHomeLat(double homeLat) {
            this.homeLat = homeLat;
        }

        public double getHomeLng() {
            return homeLng;
        }

        public void setHomeLng(double homeLng) {
            this.homeLng = homeLng;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public double getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(double liveStatus) {
            this.liveStatus = liveStatus;
        }
    }
}
