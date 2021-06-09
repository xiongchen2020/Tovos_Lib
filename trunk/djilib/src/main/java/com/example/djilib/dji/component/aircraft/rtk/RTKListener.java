package com.example.djilib.dji.component.aircraft.rtk;


import dji.common.error.DJIError;
import dji.common.flightcontroller.rtk.RTKBaseStationInformation;
import dji.common.flightcontroller.rtk.RTKConnectionStateWithBaseStationReferenceSource;
import dji.common.flightcontroller.rtk.ReferenceStationSource;

public interface RTKListener {

    void openRtkResult(DJIError djiError);

    void getRtkStatues(boolean isopen);

    void setSignalResult(DJIError djiError);

    void getNowSignalResult(ReferenceStationSource referenceStationSource);

    void setHomeAltitude(float altitude);

    void getRTKBaseStationInformationList(RTKBaseStationInformation[] rtkBaseStationInformations);

    void getRTKConnectionStateWithBaseStationState(RTKConnectionStateWithBaseStationReferenceSource rtkConnectionStateWithBaseStationReferenceSource, RTKBaseStationInformation rtkBaseStationInformation);
}
