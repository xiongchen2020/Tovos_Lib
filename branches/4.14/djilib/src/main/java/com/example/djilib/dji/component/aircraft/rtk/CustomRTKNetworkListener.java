package com.example.djilib.dji.component.aircraft.rtk;


import dji.common.error.DJIError;
import dji.common.flightcontroller.rtk.CoordinateSystem;
import dji.common.flightcontroller.rtk.NetworkServicePlansState;
import dji.common.flightcontroller.rtk.NetworkServiceState;
import dji.common.flightcontroller.rtk.RTKBaseStationInformation;

public interface CustomRTKNetworkListener {

    void openNetWorkResult(DJIError djiError);

    void getRtkLinkStatues(NetworkServiceState networkServiceState);

    void getNetworkServiceOrderPlans(NetworkServicePlansState networkServicePlansState);

    void getNetworkServiceCoordinateSystem(CoordinateSystem coordinateSystem);


}
