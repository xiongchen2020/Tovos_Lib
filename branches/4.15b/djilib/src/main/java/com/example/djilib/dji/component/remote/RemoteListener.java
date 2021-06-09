package com.example.djilib.dji.component.remote;

import dji.common.error.DJIError;
import dji.common.remotecontroller.AircraftMappingStyle;
import dji.common.remotecontroller.GPSData;
import dji.common.remotecontroller.RCMode;

public interface RemoteListener {
    void getMappingStyle(AircraftMappingStyle aircraftMappingStyle);
    void setMappingStyle(DJIError djiError);
    void getMasterSlaveMode(RCMode rcMode);
    void setMasterSlaveMode(DJIError djiError);
    void startPairing();
    void stopParing();
    void startMultiDevicePairing(DJIError djiError);
    void stopMultiDevicePairing(DJIError djiError);
    void setRTKChannelEnabled(DJIError djiError);
    void setGPSData( GPSData gpsData);
}
