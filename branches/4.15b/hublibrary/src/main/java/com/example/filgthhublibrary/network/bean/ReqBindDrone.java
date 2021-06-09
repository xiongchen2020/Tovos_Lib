package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;

public class ReqBindDrone implements Serializable {
    private int teamId;

    private String droneType;
    private  String sn;

    public String getDroneType() {
        return droneType;
    }

    public void setDroneType(String droneType) {
        this.droneType = droneType;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
//    private BindDronePara member;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

//    public BindDronePara getMember() {
//        return member;
//    }
//
//    public void setMember(BindDronePara member) {
//        this.member = member;
//    }


//    public static class BindDronePara implements Serializable {
//
//
//    }
}
