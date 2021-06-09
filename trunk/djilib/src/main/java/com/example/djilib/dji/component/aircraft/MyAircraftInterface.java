package com.example.djilib.dji.component.aircraft;

import dji.common.error.DJIError;
import dji.common.flightcontroller.ConnectionFailSafeBehavior;
import dji.common.flightcontroller.FlightControllerState;
import dji.common.model.LocationCoordinate2D;

public interface MyAircraftInterface {

    void receiver(FlightControllerState flightControllerState);

    void getSetGoHomeHeightResult(DJIError djiError);

    void getFlightMaxHeight(Integer integer);

    void getFlightMaxRadius(Integer integer);

    void getFlightGoHomeHieght(Integer integer);

    void getHomeLocation(LocationCoordinate2D locationCoordinate2D);

    void getIsAllowSwitchFlightModel(boolean allow);

    void setIsAllowSwitchFlightModel(DJIError djiError);

    void getIsAllowLimtFlightMaxRadius(boolean allow);

    void setIsAllowLimtFlightMaxRadius(DJIError djiError);

    void getFailSafeBehavior(ConnectionFailSafeBehavior failSafeBehavior);

    void setFailSafeBehavior(DJIError djiError);

    void getIMUList();

    void getCompassList();

    void getSerialNumber(String s);

    void setGoHomeLocation(DJIError djiError);


}
