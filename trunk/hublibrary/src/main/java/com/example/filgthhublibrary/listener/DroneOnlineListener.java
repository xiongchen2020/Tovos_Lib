package com.example.filgthhublibrary.listener;

import com.example.filgthhublibrary.network.bean.ResOnLineDrone;

public interface DroneOnlineListener {

    void getOnLineDroneList(ResOnLineDrone resOnLineDrone);

    void getOnLineDroneListThrowable(Throwable throwable);

}
