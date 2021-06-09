package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;
import java.util.List;

public class ReqFlightevents {
    private List<FlyReport> recordList;

    public List<FlyReport> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<FlyReport> recordList) {
        this.recordList = recordList;
    }

    public static class FlyReport {

        private long date;//毫秒
        private String sn;//sn码
        private float latitude;
        private float longitude;
        private float altitude;//经纬高度
        private float yaw;
        private float speed;
        private float speedH;
        private float speedV;
        private int flightMode;
        private int waypointMode;
        private boolean isAllowLive;
        private int batteryLevel;
        private int rcSignal;
        private float homeLatitude;
        private float homeLongitudel;
        private List<Gimbal> gimbal;
        private List<Camera> camera;
        private String accessoryModel;
        private String recordId;
        private String taskId;
        private String missionId;
        private int liveStatus;
        private boolean isMotorUp;
        private boolean isFlying;
        private String orderId;
        private long flightTime;
        private String dataOrigin;
        private String droneType;
        private int teamId;

        public String getDroneType() {
            return droneType;
        }

        public void setDroneType(String droneType) {
            this.droneType = droneType;
        }

        public String getDataOrigin() {
            return dataOrigin;
        }

        public void setDataOrigin(String dataOrigin) {
            this.dataOrigin = dataOrigin;
        }

        public int getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(int liveStatus) {
            this.liveStatus = liveStatus;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }

        public float getAltitude() {
            return altitude;
        }

        public void setAltitude(float altitude) {
            this.altitude = altitude;
        }

        public float getYaw() {
            return yaw;
        }

        public void setYaw(float yaw) {
            this.yaw = yaw;
        }

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public float getSpeedH() {
            return speedH;
        }

        public void setSpeedH(float speedH) {
            this.speedH = speedH;
        }

        public float getSpeedV() {
            return speedV;
        }

        public void setSpeedV(float speedV) {
            this.speedV = speedV;
        }

        public int getFlightMode() {
            return flightMode;
        }

        public void setFlightMode(int flightMode) {
            this.flightMode = flightMode;
        }

        public int getWaypointMode() {
            return waypointMode;
        }

        public void setWaypointMode(int waypointMode) {
            this.waypointMode = waypointMode;
        }

        public boolean isAllowLive() {
            return isAllowLive;
        }

        public void setAllowLive(boolean allowLive) {
            isAllowLive = allowLive;
        }

        public int getBatteryLevel() {
            return batteryLevel;
        }

        public void setBatteryLevel(int batteryLevel) {
            this.batteryLevel = batteryLevel;
        }

        public int getRcSignal() {
            return rcSignal;
        }

        public void setRcSignal(int rcSignal) {
            this.rcSignal = rcSignal;
        }

        public float getHomeLatitude() {
            return homeLatitude;
        }

        public void setHomeLatitude(float homeLatitude) {
            this.homeLatitude = homeLatitude;
        }

        public float getHomeLongitudel() {
            return homeLongitudel;
        }

        public void setHomeLongitudel(float homeLongitudel) {
            this.homeLongitudel = homeLongitudel;
        }

        public List<Gimbal> getGimbal() {
            return gimbal;
        }

        public void setGimbal(List<Gimbal> gimbal) {
            this.gimbal = gimbal;
        }

        public List<Camera> getCamera() {
            return camera;
        }

        public void setCamera(List<Camera> camera) {
            this.camera = camera;
        }

        public String getAccessoryModel() {
            return accessoryModel;
        }

        public void setAccessoryModel(String accessoryModel) {
            this.accessoryModel = accessoryModel;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getMissionId() {
            return missionId;
        }

        public void setMissionId(String missionId) {
            this.missionId = missionId;
        }

        public boolean isMotorUp() {
            return isMotorUp;
        }

        public void setMoteorUp(boolean motorUp) {
            isMotorUp = motorUp;
        }

        public boolean isFlying() {
            return isFlying;
        }

        public void setFlying(boolean flying) {
            isFlying = flying;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public long getFlightTime() {
            return flightTime;
        }

        public void setFlightTime(long flightTime) {
            this.flightTime = flightTime;
        }

        public static class Camera implements Serializable {
            private int id;
            private int status;
            private String model;


            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }


        }
        public static class Gimbal{
            public int id;
            public float yaw;
            public float pitch;
            public float roll;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public float getYaw() {
                return yaw;
            }

            public void setYaw(float yaw) {
                this.yaw = yaw;
            }

            public float getPitch() {
                return pitch;
            }

            public void setPitch(float pitch) {
                this.pitch = pitch;
            }

            public float getRoll() {
                return roll;
            }

            public void setRoll(float roll) {
                this.roll = roll;
            }
        }

        public int getTeamId() {
            return teamId;
        }

        public void setTeamId(int teamId) {
            this.teamId = teamId;
        }

        @Override
        public String toString() {
            return "FlyReport{" +
                    "date=" + date +
                    ", sn='" + sn + '\'' +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", altitude=" + altitude +
                    ", yaw=" + yaw +
                    ", speed=" + speed +
                    ", speedH=" + speedH +
                    ", speedV=" + speedV +
                    ", flightMode=" + flightMode +
                    ", waypointMode=" + waypointMode +
                    ", isAllowLive=" + isAllowLive +
                    ", batteryLevel=" + batteryLevel +
                    ", rcSignal=" + rcSignal +
                    ", homeLatitude=" + homeLatitude +
                    ", homeLongitudel=" + homeLongitudel +
                    ", gimbal=" + gimbal +
                    ", camera=" + camera +
                    ", accessoryModel='" + accessoryModel + '\'' +
                    ", recordId='" + recordId + '\'' +
                    ", taskId='" + taskId + '\'' +
                    ", missionId='" + missionId + '\'' +
                    ", liveStatus=" + liveStatus +
                    ", isMotorUp=" + isMotorUp +
                    ", isFlying=" + isFlying +
                    ", orderId='" + orderId + '\'' +
                    ", flightTime=" + flightTime +
                    ", dataOrigin='" + dataOrigin + '\'' +
                    '}';
        }
    }


//
//    @NonNull
//    @Override
//    public String toString() {
//        return ;
//    }
}
