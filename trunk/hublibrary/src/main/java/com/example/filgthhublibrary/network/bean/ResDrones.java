package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;
import java.util.List;

public class ResDrones implements Serializable {
    List<DroneGetModel> droneGetModels;

    public List<DroneGetModel> getDroneGetModels() {
        return droneGetModels;
    }

    public void setDroneGetModels(List<DroneGetModel> droneGetModels) {
        this.droneGetModels = droneGetModels;
    }

    public static class DroneGetModel implements Serializable{
       private String sn;
        private String  name;
        private String droneType;
        private int teamId;

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

        public String getDroneType() {
            return droneType;
        }

        public void setDroneType(String droneType) {
            this.droneType = droneType;
        }

        public int getTeamId() {
            return teamId;
        }

        public void setTeamId(int teamId) {
            this.teamId = teamId;
        }
    }
}
