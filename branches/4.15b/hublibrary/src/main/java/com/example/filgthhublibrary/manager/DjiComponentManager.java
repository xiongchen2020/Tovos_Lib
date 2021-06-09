package com.example.filgthhublibrary.manager;

import com.example.filgthhublibrary.network.bean.ReqFlightevents;

import java.util.List;

import dji.common.gimbal.Attitude;
import dji.common.gimbal.GimbalState;

public class DjiComponentManager {

    public void setGimbalState(List<ReqFlightevents.FlyReport.Gimbal> gimbalList,int index, GimbalState gimbalState) {

        Attitude attitude = gimbalState.getAttitudeInDegrees();
        ReqFlightevents.FlyReport.Gimbal gimbal = new ReqFlightevents.FlyReport.Gimbal();
        gimbal.setId(index);
        gimbal.setPitch(attitude.getPitch());
        gimbal.setRoll(attitude.getRoll());
        gimbal.setYaw(attitude.getYaw());

        if (gimbalList.size() > index) {
            gimbalList.set(index, gimbal);
        } else {
            gimbalList.add(gimbal);
        }


    }
}
